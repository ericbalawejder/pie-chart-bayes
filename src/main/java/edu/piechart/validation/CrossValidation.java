package main.java.edu.piechart.validation;

import main.java.edu.piechart.io.ReadData;
import main.java.edu.piechart.io.ReadFile;
import main.java.edu.piechart.net.Instance;
import main.java.edu.piechart.net.LearnPieChartNet;
import main.java.edu.piechart.util.Array;
import norsys.netica.Environ;
import norsys.netica.Net;
import norsys.netica.NeticaException;
import norsys.netica.Node;
import norsys.netica.NodeList;
import norsys.netica.Streamer;
import org.apache.commons.lang.ArrayUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CrossValidation {

    public static void main(String[] args) throws NeticaException {
        try {
            // Environ = null for unregister Netica usage
            Environ env = new Environ(null);

            // Use if case file is comma-delimited.
            env.setCaseFileDelimChar(',');

            // Read in annotations.txt and place into an array for local use
            ReadData annotationData = new ReadData("piechart_bayes_data/DataFiles/annotations.txt");
            String[] annotations = annotationData.openFile();

            // Create ReadData object that reads in a text file. Project file path
            ReadData pieEvidenceData = new ReadData("piechart_bayes_data/DataFiles/pieEvidenceCSV.cas");

            // Place the file contents into an array. Each array index is an instance of training data.
            String[] trainingData = pieEvidenceData.openFile();

            // Predictions array stores testing intendedMessage values
            String[] predictions = new String[trainingData.length];

            // Array with correct "Yes" or "No" predictions
            String[] correctPredictions = new String[trainingData.length];

            // Header for testing instance file
            String header = "pieID,IntendedMessage,NumberOfSlices,Prominence,SimilarColors,MultipleSlices";

            // Begin cross validation
            for (int index = 1; index < trainingData.length; index++) {
                // ----------train the net--------------------

                // Use the empty net
                String filePath = "piechart_bayes_data/NetFiles/pieEvidence.dne";

                // Read in the net created by PieChartNet.java
                Net trainingNet = new Net(new Streamer(filePath));

                // Remove conditional probability tables
                LearnPieChartNet.removeCPTables(filePath);

                // copyOfRange method does not support String[] type, must cast
                String[] trainingTemp1 = Arrays.copyOfRange(trainingData, 0, index);
                String[] trainingTemp2 = Arrays.copyOfRange(trainingData, index + 1, trainingData.length);

                // Combine trainingTemp arrays into one array for training instance
                String[] trainingInstance = (String[]) ArrayUtils.addAll(trainingTemp1, trainingTemp2);

                // Testing instance
                String[] testingInstance = {header, trainingData[index]};

                // Create temporary files from training and testing arrays
                File trainingDataTemp = File.createTempFile("trainingDataTemp", ".cas", new File("piechart_bayes_data/DataFiles/"));
                File testingDataTemp = File.createTempFile("testingDataTemp", ".cas", new File("piechart_bayes_data/DataFiles/"));

                // Uses absolute path, no library method for relative path
                writeToFile(trainingDataTemp.getAbsolutePath(), trainingInstance);
                writeToFile(testingDataTemp.getAbsolutePath(), testingInstance);

                // Read in the temporary case file and learn new CPTables.
                Streamer caseFile = new Streamer(trainingDataTemp.getAbsolutePath());
                trainingNet.reviseCPTsByCaseFile(caseFile, trainingNet.getNodes(), 1.0);

                // Builds the junction tree of cliques (maximal complete subgraph).
                trainingNet.compile();

                // Write the learned network to a dne file.
                Streamer trainingStreamOut = new Streamer("piechart_bayes_data/NetFiles/trainingTemp.dne");
                trainingNet.write(trainingStreamOut);

                // ------------test the net-------------------

                // Must read in the learned net created by LearnPieChartNet.java.
                Net testingNet = new Net(new Streamer("piechart_bayes_data/NetFiles/trainingTemp.dne"));

                // Get the nodes from the net for local use
                Node intendedMessage = testingNet.getNode("IntendedMessage");
                Node numberOfSlices = testingNet.getNode("NumberOfSlices");
                Node prominence = testingNet.getNode("Prominence");
                Node similarColors = testingNet.getNode("SimilarColors");
                Node multipleSlices = testingNet.getNode("MultipleSlices");

                // Builds the junction tree of cliques (maximal complete subgraph).
                testingNet.compile();

                // Create ReadFile object that reads in a text file. Project file path
                ReadFile testFile = new ReadFile(testingDataTemp.getAbsolutePath());

                // Place the file contents into an array.
                String[] testInstance = testFile.openFile();

                // Create an Instance object from the testInstance file
                Instance instance = new Instance(testInstance);

                // Enter evidence into the trained net. Must trim extra newline for enterState() input
                numberOfSlices.finding().enterState(instance.getNumberOfSlices());
                prominence.finding().enterState(instance.getProminence());
                similarColors.finding().enterState(instance.getSimilarColors());
                multipleSlices.finding().enterState(instance.getMultipleSlices().trim());

                // getBeliefs() returns the probability of each state in parent node intendedMessage
                float[] beliefArray = intendedMessage.getBeliefs();

                // Convert type state to type string with .toString()
                predictions[index] = intendedMessage.state(Array.findIndex(beliefArray)).toString();

                // Compare predictions array to annotations array
                if (predictions[index].contains(annotations[index - 1])) {
                    correctPredictions[index] = "Yes";
                } else {
                    correctPredictions[index] = "No";
                }

                // Turns auto-updating on or off
                testingNet.setAutoUpdate(1);

                // Return vector of state probabilities
                //Streamer testStreamOut = new Streamer("piechart_bayes_data/NetFiles/testingTemp.dne");
                // Creates 31 testingTemp.dne files for inspection
                Streamer testStreamOut = new Streamer("piechart_bayes_data/NetFiles/testingTemp" + index + ".dne");
                testingNet.write(testStreamOut);

                // Garbage collector. Not strictly necessary, but a good habit.
                trainingNet.finalize();
                testingNet.finalize();

                // Delete temporary files
                trainingDataTemp.deleteOnExit();
                testingDataTemp.deleteOnExit();
            }

            // Print results
            Array.multipleLinePrint(predictions, 6);
            System.out.println();
            Array.multipleLinePrint(annotations, 6);
            System.out.println(measureAccuracy(predictions, annotations));
            Array.multipleLinePrint(correctPredictions, 6);
            System.out.println("\nexecution complete");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // compare predictions[] against annotations[]
    public static double measureAccuracy(String[] prediction, String[] annotations) {
        double counter = 0;
        for (int index = 0; index < annotations.length; index++) {
            // if prediction matches annotation, increment counter
            if (prediction[index + 1].contains(annotations[index])) {
                counter++;
            }
        }

        // returns percent value (*100)
        return (counter / prediction.length) * 100;
    }

    public static void removeId(File f0, File f1, File f2, int lineNumber) throws IOException {
        ReadFile contents = new ReadFile(f0.getParent());
        String[] data = contents.openFile();

        FileWriter f1FileOut = new FileWriter(f1);
        FileWriter f2FileOut = new FileWriter(f2);

        String[] f1NewTrainingData = new String[data.length - 1];
        String[] f2NewTestingsData = new String[2];

        for (int index = 0; index < data.length; index++) {
            if (index != lineNumber) {
                f1NewTrainingData[index] = data[index];
            }

            if (index == 0 || index == lineNumber) {
                f2NewTestingsData[index] = data[index];
            }
        }

        f1FileOut.write(Arrays.toString(f1NewTrainingData));
        f2FileOut.write(Arrays.toString(f2NewTestingsData));

        // close FileWriter resources
        f1FileOut.close();
        f2FileOut.close();
    }

    public static Net testNet(Net trained, String evidence) throws NeticaException {
        String[] splitEvidence = evidence.split(",");
        NodeList nodeList = trained.getNodes();

        for (int index = 0; index < trained.sizeCompiled(); index++) {
            nodeList.set(index, splitEvidence[index]);
        }
        Net newNet = new Net();
        newNet.copyNodes(nodeList);
        newNet.compile();
        return newNet;
    }

    // method to write the contents of a String array to a File
    public static void writeToFile(String filename, String[] array) throws IOException {
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(filename));
        for (String s : array) {
            outputWriter.write(s + "");
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

}
