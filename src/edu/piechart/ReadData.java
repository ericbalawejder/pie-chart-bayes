// Java documentation for reading in a file type
// https://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html
// https://docs.oracle.com/javase/7/docs/api/java/io/FileReader.html

/****************************************************************************
 * Class returns file to String array[] where each line of the file is
 * an index of the array[]
 ****************************************************************************
 */

package edu.piechart;

//needed for BufferedReader and FileReader
import java.io.*;

public class ReadData
{
	// define path where file is
	private String path; 

	// ReadFile constructor 
	public ReadData(String filePath) 
	{
		path = filePath;
	}

	// open whole file and copy into a string array 
	public String[] openFile() throws IOException 
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
		// fileToString is StringBuilder object, .toString makes String object
		// split string contents with newline (\n) and place in array
		String[] data = fileToString.toString().split("\n");
		// close resources
		bufferReader.close();
		// return the array
		return data;
	}
}
