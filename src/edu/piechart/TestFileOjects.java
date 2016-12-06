package edu.piechart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import norsys.netica.Net;
import norsys.netica.NeticaException;
import norsys.netica.NodeList;

public class TestFileOjects 
{
	public static void main(String[] args) 
	{
		try 
		{	
			// read in annotations.txt and place into an array for local use
			ReadData annotationData = new ReadData("piechart_bayes_data/DataFiles/annotations.txt");
			String[] annotations = annotationData.openFile();
			
			// Creates a new empty file in the specified directory, 
			// using the given prefix and suffix strings to generate its name.
			// createTempFile(String prefix, String suffix, File directory)
			
			//File file0 = File.createTempFile("TempF0", ".cas", new File("piechart_bayes_data/DataFiles/"));
			File file0 = new File("piechart_bayes_data/DataFiles/fileTest.cas");
			File file1 = File.createTempFile("TempF1", ".cas", new File("piechart_bayes_data/DataFiles/"));
			File file2 = File.createTempFile("TempF2", ".cas", new File("piechart_bayes_data/DataFiles/"));
			
			ReadFileString fileContents = new ReadFileString("piechart_bayes_data/DataFiles/fileTest.cas");
			String stringFileContents = fileContents.openFile();
			System.out.println(stringFileContents);
			
			System.out.println(file0);
			// prints absolute path
	        System.out.println(file0.getAbsolutePath());
	         
	        //writeToFile(file0.getAbsolutePath(), array);
	        
	        for (int index = 1; index < annotations.length; index++)
	        {
	        	removeId(file0, file1, file2, index);
	        }
	        
	        // deletes file when the virtual machine terminate
	        //file0.deleteOnExit();
	        file1.deleteOnExit();
	        file2.deleteOnExit();
	         
	        //URL url = getClass().getResource("annotations.txt");
	        //File someFile = new File(url.getPath());
	        //System.out.println(someFile);
	        /* 
	        File testingFile = new File("./piechart_bayes_data/DataFiles/randomFile.txt");
	        System.out.println(testingFile);
	        System.out.println(testingFile.getAbsolutePath());
	        
	        File directory = new File("./");
	        System.out.println(directory.getAbsolutePath());
	        
	        File resourceFile = new File("../myFile.txt");
	        System.out.println(resourceFile.getAbsolutePath());
	        
	        Properties properties = new Properties();
	        properties.load(getClass().getResourceAsStream("annotations.txt"));
	        */
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(String filename, String[] array) throws IOException
	{
		BufferedWriter outputWriter = null;
		outputWriter = new BufferedWriter(new FileWriter(filename));
		for (int index = 0; index < array.length; index++) 
		{
			// Maybe:
			outputWriter.write(array[index] + "");
			outputWriter.newLine();
		}
		outputWriter.flush();  
		outputWriter.close();  
	}
	
	public static void removeId(File f0, File f1, File f2, int lineNumber) throws IOException
	{
		ReadFile contents = new ReadFile(f0.getParent());
		String[] data = contents.openFile();
		
		FileWriter f1FileOut = new FileWriter(f1);
		FileWriter f2FileOut = new FileWriter(f2);
		
		String[] f1NewTrainingData = new String[data.length - 1];
		String[] f2NewTestingsData = new String[2];
		
		for (int index = 0; index < data.length; index++)
		{
			if(index != lineNumber)
			{
				f1NewTrainingData[index] = data[index];
			}
			
			if (index == 0 || index == lineNumber)
			{
				f2NewTestingsData[index] = data[index];
			}
		}
		
		f1FileOut.write(f1NewTrainingData.toString());
		f2FileOut.write(f2NewTestingsData.toString());
		
		// close FileWriter resources
		f1FileOut.close();
		f2FileOut.close();
	}
	
	public static Net testNet(Net trained, String evidence) throws NeticaException
	{
		String[] splitEvidence = evidence.split(",");
		NodeList nodeList = trained.getNodes();
		
		for (int index = 0; index < trained.sizeCompiled(); index++)
		{
			nodeList.set(index, splitEvidence[index]);
		}
		Net newNet = new Net();
		newNet.copyNodes(nodeList);
		newNet.compile();
		return newNet;
	}
}
