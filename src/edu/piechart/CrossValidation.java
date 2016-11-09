package edu.piechart;

import java.util.Arrays;
import java.io.IOException;

public class CrossValidation 
{
	public static void main(String[] args) 
	{
		try
		{
			// create ReadData object that reads in a text file. Project file path
			ReadData data = new ReadData("piechart_bayes_data/DataFiles/pieEvidenceCSV.cas");
			
			// place the file contents into an array.
			// Each array index is an instance of training data.
			String[] trainingData = data.openFile();
						
			// print out each line of the of array
			Array.multipleLinePrint(trainingData, 1);
						
			// show length of trainingData array
			System.out.println(trainingData.length);
			
			// Use the empty net?
			String filePath = "piechart_bayes_data/NetFiles/pieEvidence.dne";
			
			// PLEASE DOUBLE CHECK THIS
			String[] annotations = {"singleSlice", "fraction", "majoritySlice", "versus", "fraction",
					"addSlices", "majoritySlice", "majoritySlice", "twoTiedForBiggest", "majoritySlice",
					"biggestSlice", "addSlices", "fraction", "noMajority", "versus", "biggestSlice", 
					"versus", "biggestSlice", "majoritySlice", "majoritySlice", "majoritySlice", "majoritySlice",
					"twoTiedForBiggest", "majoritySlice", "majoritySlice", "twoTiedForBiggest", "numOfParts",
					"majoritySlice", "singleSlice", "singleSlice", "versus"};
			
			
			// copyOfRange method does not support String[] type, must cast
			String[] copy = (String[]) Arrays.copyOfRange(annotations, 5, 30);
			Array.multipleLinePrint(copy, 6);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
