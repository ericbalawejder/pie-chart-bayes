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

			// Place evidence data here.
			String numOfSlices = "fiveOrMore";
			String prom = "yes";
			String simColors = "no";
			String multSlices = "yes";

			// enter evidence into the trained net
			numberOfSlices.finding().enterState(numOfSlices);
			prominence.finding().enterState(prom);
			similarColors.finding().enterState(simColors);
			multipleSlices.finding().enterState(multSlices);

			// create beliefArray to store the probabilities of the message categories
			double[] beliefArray = new double[11];

			// get the probability of each intended message and insert it into the beliefArray
			double singleSliceProbability = intendedMessage.getBelief("singleSlice");
			beliefArray[0] = singleSliceProbability;

			double fractionProbability = intendedMessage.getBelief("fraction");
			beliefArray[1] = fractionProbability;

			double versusProbability = intendedMessage.getBelief("versus");
			beliefArray[2] = versusProbability;

			double biggestSliceProbability = intendedMessage.getBelief("biggestSlice");
			beliefArray[3] = biggestSliceProbability;

			double majoritySliceProbability = intendedMessage.getBelief("majoritySlice");
			beliefArray[4] = majoritySliceProbability;

			double addSlicesProbability = intendedMessage.getBelief("addSlices");
			beliefArray[5] = addSlicesProbability;

			double twoTiedForBiggestProbability = intendedMessage.getBelief("twoTiedForBiggest");
			beliefArray[6] = twoTiedForBiggestProbability;

			double noMajorityProbability = intendedMessage.getBelief("noMajority");
			beliefArray[7] = noMajorityProbability;

			double smallestSliceProbability = intendedMessage.getBelief("smallestSlice");
			beliefArray[8] = smallestSliceProbability;

			double closeToHalfProbability = intendedMessage.getBelief("closeToHalf");
			beliefArray[9] = closeToHalfProbability;

			double numOfPartsProbability = intendedMessage.getBelief("numOfParts");
			beliefArray[10] = numOfPartsProbability;

			System.out.println("\nGiven a pie chart with " + numOfSlices
					+ " slices, prominence,\n" + simColors
					+ " similar colors and " + multSlices
					+ " multiple slices, the probability of " + "a BLANK"
					+ " message is " + max(beliefArray) + "\n");

			multipleLinePrint(beliefArray, 5);

			System.out.println("The highest probability is: " + max(beliefArray) 
					+ " at index BLANK" );
						
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

	// array print method
	public static void print(double[] array) 
	{
		for (int index = 0; index < array.length; index++) 
		{
			System.out.print(array[index] + "   ");
		}
		System.out.println();
	}

	// array print method with added number per line parameter
	public static void multipleLinePrint(double[] array, int numberPerLine) 
	{
		for (int index = 0; index < array.length; index++) 
		{
			if (index != 0 && index % numberPerLine == 0) 
			{
				System.out.println();
			}
			System.out.print(array[index] + "   ");
		}
		System.out.println();
	}
	
	// method to find the max value in an array of doubles
	public static double max(double[] array)
	{
		double max = 0;
		
		for (int index = 0; index < array.length; index++)
		{
			if (array[index] > max)
			{
				max = array[index];
			}
		}
		return max;
	}
}