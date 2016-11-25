package edu.piechart;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import norsys.netica.*;

public class LearnPieChartNet 
{
	public static void main(String[] args) 
	{
		try 
		{
			// Environ = null for unregister Netica usage
			Environ env = new Environ(null);

			// Use if case file is comma-delimited.
			env.setCaseFileDelimChar(',');
			
			// file path of Netica .dne net file 
			String filePath = "piechart_bayes_data/NetFiles/pieEvidence.dne";
			
			// Read in the net created by PieChartNet.java
			Net net = new Net(new Streamer(filePath));
			
			// function to remove CPTables of nodes in net, so new ones can be learned.
			removeCPTables(filePath);
			
			// Read in the case file pieEvidence.cas and learn new CPTables.
			Streamer caseFile = new Streamer("piechart_bayes_data/DataFiles/pieEvidenceCSV.cas");
			net.reviseCPTsByCaseFile(caseFile, net.getNodes(), 1.0);

			// Builds the junction tree of cliques (maximal complete subgraph).
			net.compile();

			// Write the learned network to a dne file.
			Streamer stream = new Streamer("piechart_bayes_data/NetFiles/LearnedPieEvidence.dne");
			net.write (stream);
			
			System.out.println("execution complete");

			// Frees Netica object's native resources. Not strictly necessary, but a good habit.
			net.finalize();
		}

		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	// function to remove the Conditional Probability Tables from a net
	public static void removeCPTables(String filename) throws NeticaException
	{
		Net net = new Net(new Streamer(filename));
		NodeList nodes = net.getNodes();
		int numberOfNodes = nodes.size();

		// Remove CPTables of nodes in net, so new ones can be learned.
		for (int n = 0; n < numberOfNodes; n++) 
		{
			Node node = (Node) nodes.get(n);
			node.deleteTables();
		}
	}
	
	public static void removeId(File f0, File f1, File f2, String id) throws IOException{
		ReadFile Contents = new ReadFile(f0.getParent());
		FileWriter f1FileOut = new FileWriter(f1);
		FileWriter f2FileOut = new FileWriter(f2);
		String[] data = Contents.openFile();
		
		String[] f1NewData = new String[data.length-1];
		String[] f2NewData = new String[2];
		
		 Pattern pattern = Pattern.compile("\\d+");
		 Matcher match;
		
		for(int i = 0; i< data.length; i++){
			match = pattern.matcher(id);
			String tempId = match.toString();
			if(i != Integer.parseInt(tempId)){
				f1NewData[i] = data[i];
			}
			if(i == 0 || i == Integer.parseInt(id) ){
				f2NewData[i] = data[i];
			}
		}
		
		f1FileOut.write(f1NewData.toString());
		f2FileOut.write(f2NewData.toString());

	}
}

/*
 * ============================================================== 
 * This alternate way can replace the net.reviseCPTsByCaseFile line above, 
 * if you need to filter or adjust individual cases.
 * 
 * long[] casePosition = new long[1]; 
 * casePosition[0] = Net.FIRST_CASE; 
 * while (true) 
 * {
 * 		net.retractFindings(); 
 * 		net.readCase (casePosition, caseFile, nodes, null, null);
 * 		if (casePosition[0] == Net.NO_MORE_CASES) 
 * 		{
 * 			break;
 * 		}
 * net.reviseCPTsByFindings (nodes, 1.0); casePosition[0] = Net.NEXT_CASE; 
 * }
 * ==============================================================
 */

