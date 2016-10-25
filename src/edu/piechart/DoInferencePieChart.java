package edu.piechart;

import norsys.netica.*;

public class DoInferencePieChart 
{
	public static void main(String[] args) 
	{
		try 
		{
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
			
			// project file path
			String fileName = "piechart_bayes_data/DataFiles/pieEvidencePredict2.cas";
			
			// create ReadFile object that reads in a text file
			ReadFile file = new ReadFile(fileName);
			
			// place the file contents into an array.
			String[] testInstance = file.openFile();
							
			// create an Instance object from the testInstance file
			Instance instance = new Instance(testInstance);
						
			// enter evidence into the trained net. Must trim extra newline for enterState() input
			numberOfSlices.finding().enterState(instance.numberOfSlices);
			prominence.finding().enterState(instance.prominence);
			similarColors.finding().enterState(instance.similarColors);
			multipleSlices.finding().enterState(instance.multipleSlices.trim());

			// create beliefArray to store the probabilities of the message categories
			float[] beliefArray = new float[11];
			
			// create String array with IntendedMessage categories
			String[] messageCategory = {"singleSlice", "fraction", "versus", "biggestSlice",
					"majoritySlice", "addSlices","twoTiedForBiggest", "noMajority", "smallestSlice",
					"closeToHalf", "numOfParts"};
			
			// calculate the probability of each message category and place in beliefArray
			for (int index = 0; index < messageCategory.length; index++)
			{
				beliefArray[index] = intendedMessage.getBelief(messageCategory[index]);
			}

			System.out.println("\nGiven a pie chart with " + instance.numberOfSlices
					+ " slices, " + instance.prominence + " prominence, " 
					+ instance.similarColors
					+ " similar colors and " + instance.multipleSlices
					+ "multiple slices, the probability of " + "a " 
					+ messageCategory[Arrays.findIndex(beliefArray)]
					+ " message is " + Arrays.max(beliefArray) + "\n");

			System.out.println("The highest probability is: " + Arrays.max(beliefArray) 
					+ " with message category = " + messageCategory[Arrays.findIndex(beliefArray)]);
						
			// turns auto-updating on or off
			net.setAutoUpdate(1);

			// return vector of state probabilities
			net.write(new Streamer("piechart_bayes_data/NetFiles/singleSliceTestEvidence.dne"));

			// garbage collector. Not strictly necessary, but a good habit.
			net.finalize();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}