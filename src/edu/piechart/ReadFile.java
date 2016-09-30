// Java documentation for reading in a file type
// https://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html
// https://docs.oracle.com/javase/7/docs/api/java/io/FileReader.html

package edu.piechart;

//needed for BufferedReader and FileReader
import java.io.*;

public class ReadFile 
{
	public static void main(String[] args) throws IOException 
	{
		try
		{
		// project file path (No such file or directory)
		//String fileName = "/piechart_bayes_data/DataFiles/pieEvidencePredict.cas";
		
		// system file path
		String fileName = "/Users/ericbalawejder/Workspaces/Eclipse 4.2 Java" +
				"/PieChart/piechart_bayes_data/DataFiles/pieEvidencePredict.cas";
		
		// create ReadFile object that reads in a text file
		ReadFile file = new ReadFile(fileName);
		
		//System.out.println(fileName);
		//System.out.println(file);
		// look in net class
		
		// store the whole file into memory
		String testInstance = file.openFile();
					
		// print file
		System.out.println(testInstance);
		
		}
	
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	// define path where file is
	private String path; 

	// ReadFile constructor 
	public ReadFile(String filePath) 
	{
		path = filePath;		
	}

	// open whole file and copy into a string 
	public String openFile() throws IOException 
	{
		// create ReadFile object
		FileReader fileReader = new FileReader(path);
		// create BufferedReader object for FileReader object
		BufferedReader bufferReader = new BufferedReader(fileReader);
		// create StringBuilder object to build the file into a string
		StringBuilder fileToString = new StringBuilder();

		String characterData;

		while ((characterData = bufferReader.readLine()) != null) 
		{
			// appened file contents to fileToString
			fileToString.append(characterData);
			fileToString.append("\n");
		}
		// close resources
		bufferReader.close();
		// return the string
		return fileToString.toString();
	}
}
