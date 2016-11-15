package edu.piechart;

import java.util.Arrays;
import java.io.IOException;
import norsys.netica.*;

public class CrossValidation 
{
	public static void main(String[] args) throws NeticaException 
	{
		try
		{
			// Environ = null for unregister Netica usage
			Environ env = new Environ(null);
			
			// Use if case file is comma-delimited.
			env.setCaseFileDelimChar(',');
			
			// Use the empty net
			String filePath = "piechart_bayes_data/NetFiles/pieEvidence.dne";
			
			// Read in the net created by PieChartNet.java
			//Net net = new Net(new Streamer(filePath));
			
			// create ReadData object that reads in a text file. Project file path
			ReadData data = new ReadData("piechart_bayes_data/DataFiles/pieEvidenceCSV.cas");
			
			// place the file contents into an array.
			// Each array index is an instance of training data.
			String[] trainingData = data.openFile();
			
			// PLEASE DOUBLE CHECK THIS
			String[] annotations = {"singleSlice", "fraction", "majoritySlice", "versus", "fraction",
					"addSlices", "majoritySlice", "majoritySlice", "twoTiedForBiggest", "majoritySlice",
					"biggestSlice", "addSlices", "fraction", "noMajority", "versus", "biggestSlice", 
					"versus", "biggestSlice", "majoritySlice", "majoritySlice", "majoritySlice", "majoritySlice",
					"twoTiedForBiggest", "majoritySlice", "majoritySlice", "twoTiedForBiggest", "numOfParts",
					"majoritySlice", "singleSlice", "singleSlice", "versus"};
						
			// start at index 1 to omit header in file
			for (int index = 1; index < trainingData.length; index++)
			{
				// remove conditional probability tables			
				LearnPieChartNet.removeCPTables(filePath);
				
				// copyOfRange method does not support String[] type, must cast
				String[] temp1 = (String[]) Arrays.copyOfRange(trainingData, 1, index);
				
				// testing instance
				String testingInstance = trainingData[index];
				
				String[] temp2 = (String[]) Arrays.copyOfRange(trainingData, index + 1, trainingData.length);
				
				Array.multipleLinePrint(temp1, 4);
				System.out.println("temp1 length = " + temp1.length);
			
				System.out.println("\n" + "index = " + index + "\n" + "testing = " + testingInstance + "\n");
			
				Array.multipleLinePrint(temp2, 4);
				System.out.println("temp2 length = " + temp2.length);
				
				System.out.println("----------------------------------");
				
				// stop when testingInstance is last line of trainingData[]
				if (temp2.length == 0)
				{
					break;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
