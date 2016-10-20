package edu.piechart;

import java.io.IOException;

public class Instance 
{
	//public String messageCategory;
	public static String numberOfSlices;
	public static String prominence;
	public static String similarColors;
	public static String multipleSlices;
	
	// made static for testing purposes
	//static ArrayList<Instance> instances = new ArrayList<Instance>();
	
	public static void main(String[] args)
	{
		// Structure for importing a csv file.
		// This must be completed in a main method within a try catch block.
		// Exact class to be determined.
		try
		{
		String fileName = "/Users/ericbalawejder/Workspaces/Eclipse 4.2 Java" +
				"/PieChart/piechart_bayes_data/DataFiles/pieEvidencePredict.cas";
		
		// create ReadFile object that reads in a text file
		ReadFile file = new ReadFile(fileName);
		
		// place the file contents into an array.
		String[] testInstance = file.openFile();
						
		setEvidence(testInstance);
		
		// print file
		DoInferencePieChart.print(testInstance);
		
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	// static constructor not allowed??
	public Instance()
	{
		//instances = new ArrayList<Instance>();
	}
	
	public static void setEvidence(String[] evidence)
	{
		//use array from ReadFile to place evidence
		numberOfSlices = evidence[7];
		prominence = evidence[8];
		similarColors = evidence[9];
		multipleSlices = evidence[10];
	}
}
