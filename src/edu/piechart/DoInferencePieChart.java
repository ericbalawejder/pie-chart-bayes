package edu.piechart;

import norsys.netica.*;

public class DoInferencePieChart {

    public static void main(String[] args) {
        try {
            Environ env = new Environ(null);

            // Must read in the learned net created by LearnPieChartNet.java.
            Net net = new Net(new Streamer("piechart_bayes_data/NetFiles/LearnedPieEvidence.dne"));

            // get the nodes from the net for local use
            Node intendedMessage = net.getNode("IntendedMessage");
            Node numberOfSlices = net.getNode("NumberOfSlices");
            Node prominence = net.getNode("Prominence");
            Node similarColors = net.getNode("SimilarColors");
            Node multipleSlices = net.getNode("MultipleSlices");

            // Builds the junction tree of cliques (maximal complete subgraph).
            net.compile();

            // create ReadFile object that reads in a text file. Project file path
            ReadFile file = new ReadFile("piechart_bayes_data/DataFiles/pieEvidencePredictP2.cas");

            // place the file contents into an array.
            String[] testInstance = file.openFile();

            // create an Instance object from the testInstance file
            Instance instance = new Instance(testInstance);

            // enter evidence into the trained net. Must trim extra newline for enterState() input
            numberOfSlices.finding().enterState(instance.getNumberOfSlices());
            prominence.finding().enterState(instance.getProminence());
            similarColors.finding().enterState(instance.getSimilarColors());
            multipleSlices.finding().enterState(instance.getMultipleSlices().trim());

            // getBeliefs() returns the probability of each state in parent node intendedMessage
            float[] beliefArray = intendedMessage.getBeliefs();

            System.out.println("Slices = " + instance.getNumberOfSlices()
                    + ", prominence = " + instance.getProminence()
                    + ", similar colors = " + instance.getSimilarColors()
                    + ", multiple slices = " + instance.getMultipleSlices()
                    + "The highest probability is: " + Array.max(beliefArray)
                    + " with message category = "
                    + intendedMessage.state(Array.findIndex(beliefArray)));

            // turns auto-updating on or off
            net.setAutoUpdate(1);

            // return vector of state probabilities
            Streamer stream = new Streamer("piechart_bayes_data/NetFiles/singleSliceTestEvidence.dne");
            net.write(stream);

            System.out.println("\nexecution complete");

            // garbage collector. Not strictly necessary, but a good habit.
            net.finalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}