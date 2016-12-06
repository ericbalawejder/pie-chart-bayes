//Java documentation for reading in a file type
//https://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html
//https://docs.oracle.com/javase/7/docs/api/java/io/FileReader.html

/****************************************************************************
 * Class returns file to String
 ****************************************************************************
 */

package edu.piechart;

//needed for BufferedReader and FileReader
import java.io.*;

public class ReadFileString 
{
	// define path where file is
	private String path;

	// ReadFile constructor
	public ReadFileString(String filePath) 
	{
		path = filePath;
	}

	// open file and copy specified number of lines into a string
	public String openFile(int numberOfLines) throws IOException 
	{
		// create ReadFile object
		FileReader fileReader = new FileReader(path);
		// create BufferedReader object for FileReader object
		BufferedReader bufferReader = new BufferedReader(fileReader);
		// create StringBuilder object to build the file into a string
		StringBuilder fileToString = new StringBuilder();

		String characterData;

		// index for number of lines in a file
		int fileLine = 0;

		while ((characterData = bufferReader.readLine()) != null && fileLine <= numberOfLines) 
		{
			// appened file contents to fileToString
			fileToString.append(characterData);
			fileToString.append("\n");
			fileLine++;
		}
		// close resources
		bufferReader.close();
		// return the string
		return fileToString.toString();
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