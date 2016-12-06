// Java documentation for reading in a file type
// https://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html
// https://docs.oracle.com/javase/7/docs/api/java/io/FileReader.html

/****************************************************************************
 * Class returns file to String array[]
 ****************************************************************************
 */

package edu.piechart;

//needed for BufferedReader and FileReader
import java.io.*;

public class ReadFile 
{
	// define path where file is
	private String path; 

	// ReadFile constructor 
	public ReadFile(String filePath) 
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
		// split string contents with , and place in array
		String[] evidence = fileToString.toString().split(",");
		// close resources
		bufferReader.close();
		// return the array
		return evidence;
	}
}
