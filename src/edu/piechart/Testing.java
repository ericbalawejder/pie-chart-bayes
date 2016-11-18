package edu.piechart;

import java.io.IOException;
import norsys.netica.*;

public class Testing 
{
	public static void main(String[] args) throws NeticaException 
	{
		try 
		{
			// Environ = null for unregister Netica usage
			Environ env = new Environ(null);

			// Use if case file is comma-delimited.
			env.setCaseFileDelimChar(',');

			// read in annotations.txt and place into an array for local use
			ReadData data = new ReadData("piechart_bayes_data/DataFiles/annotations.txt");
			String[] annotations = data.openFile();
			
			// predictions array stores testing intendedMessage values
			String[] predictions = new String[annotations.length];

			// 31 times
			for (int index = 0; index < annotations.length; index++) 
			{
				// ----------train the net--------------------

				// Use the empty net
				String filePath = "piechart_bayes_data/NetFiles/pieEvidence.dne";

				// Read in the net created by PieChartNet.java
				Net trainingNet = new Net(new Streamer(filePath));

				// remove conditional probability tables
				LearnPieChartNet.removeCPTables(filePath);

				// Read in the case file and learn new CPTables.
				Streamer caseFile = new Streamer("piechart_bayes_data/DataFiles/trainingTemp.cas");
				trainingNet.reviseCPTsByCaseFile(caseFile, trainingNet.getNodes(), 1.0);

				// Builds the junction tree of cliques (maximal complete subgraph).
				trainingNet.compile();

				// Write the learned network to a dne file.
				Streamer trainingStreamOut = new Streamer("piechart_bayes_data/NetFiles/trainingTemp.dne");
				trainingNet.write(trainingStreamOut);

				// ------------test the net-------------------

				// Must read in the learned net created by LearnPieChartNet.java.
				Net testingNet = new Net(new Streamer("piechart_bayes_data/NetFiles/trainingTemp.dne"));

				// get the nodes from the net for local use
				Node intendedMessage = testingNet.getNode("IntendedMessage");
				Node numberOfSlices = testingNet.getNode("NumberOfSlices");
				Node prominence = testingNet.getNode("Prominence");
				Node similarColors = testingNet.getNode("SimilarColors");
				Node multipleSlices = testingNet.getNode("MultipleSlices");

				// Builds the junction tree of cliques (maximal complete subgraph).
				testingNet.compile();

				// create ReadFile object that reads in a text file. Project file path
				ReadFile file = new ReadFile("piechart_bayes_data/DataFiles/testingTemp.cas");

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

				// convert type state to type string with .toString()
				predictions[index] = intendedMessage.state(Array.findIndex(beliefArray)).toString();

				// turns auto-updating on or off
				testingNet.setAutoUpdate(1);

				// return vector of state probabilities
				Streamer testStreamOut = new Streamer("piechart_bayes_data/NetFiles/testingTemp.dne");
				testingNet.write(testStreamOut);

				// garbage collector. Not strictly necessary, but a good habit.
				trainingNet.finalize();
				testingNet.finalize();
			}
			//Array.multipleLinePrint(annotations, 6);
			
			System.out.println(measureAccuracy(predictions, annotations));
			
			System.out.println("execution complete");
		}

		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}

	// compare predictions[] against annotations[]
	public static double measureAccuracy(String[] prediction, String[] annotations) 
	{
		double counter = 0;
		for (int index = 0; index < prediction.length; index++) 
		{
			// if prediction matches annotation, increment counter
			if (prediction[index].contains(annotations[index])) 
			{
				counter++;
			}
		}
		
		// returns percent value (*100)
		double accuracy = (counter / prediction.length) * 100;
		return accuracy;
	}
	/*
	 * public static Net testNet (Net trained, String evidence) throws NeticaException 
	 * { 
	 * 		String[] splitEvidence = evidence.split(",");
	 * 		trained.compile(); 
	 * 		return trained;
	 * }
	 */
}
