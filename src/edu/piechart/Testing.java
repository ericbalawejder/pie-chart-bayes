package edu.piechart;

import java.io.IOException;

public class Testing 
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
			Arrays.multipleLinePrint(trainingData, 1);
			
			// show length of trainingData array
			System.out.println(trainingData.length);
			
			// omit trainingData[0] header line
			// loop through removing one trainingData[index] at a time
			// using cross validation to train and test
			//
			// how to grab evidence? trainingData[String.split(",")]
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
