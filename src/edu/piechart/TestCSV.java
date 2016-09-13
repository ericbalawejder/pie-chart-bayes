package edu.piechart;
import java.io.File;
import norsys.netica.*;

public class TestCSV 
{
	public static void main(String[] args) 
	{
		try {
			Environ env = new Environ(null);

			// Use if case file is comma-delimited.
			//env.setCaseFileDelimChar (','); 
			
			// Read in the trained net.
			Net net = new Net (new Streamer ("piechart_bayes_data/NetFiles/LearnedPieEvidence.dne"));
			NodeList nodes = net.getNodes();
			System.out.println(nodes);
			
//			int numNodes = nodes.size();
//			
//			// Remove CPTables of nodes in net, so new ones can be learned.
//			for (int n = 0;  n < numNodes;  n++) 
//			{
//			    Node node = (Node) nodes.get (n);
//			    node.deleteTables();
//			}
			
			// Read in the case file
			Streamer caseFile = new Streamer ("piechart_bayes_data/DataFiles/singleSliceTest.cas");
			//Streamer caseFile = new Streamer ("piechart_bayes_data/DataFiles/pieEvidencePredict.cas");
			net.reviseCPTsByCaseFile (caseFile, nodes, 1.0);
			
			Node intendedMessage = net.getNode("IntendedMessage");
			Node numberOfSlices = net.getNode("NumberOfSlices");
			Node prominence = net.getNode("Prominence");
			Node similarColors = net.getNode("SimilarColors");
			Node multipleSlices = net.getNode("MultipleSlices");
			
			// Builds the junction tree of cliques (maximal complete subgraph).
			net.compile();
			
			//double belief = intendedMessage.getBelief ("singleSlice"); 
			//System.out.println(belief);
			
			//double belief2 = numberOfSlices.getBelief ("fiveOrMore");
			//double belief2 = numberOfSlices.finding().enterState ("fiveOrMore");
			//System.out.println(belief2);
			
			//double belief3 = prominence.getBelief ("yes");
			//double belief3 = prominence.finding().enterState ("yes");
			//System.out.println(belief3);
			
			//double belief4 = similarColors.getBelief("no");
			//System.out.println(belief4);
			
			//double belief5 = multipleSlices.getBelief("yes");
			//System.out.println(belief5);
			
			// Write the learned network to a dne file.
			net.write (new Streamer ("piechart_bayes_data/NetFiles/testEvidence.dne"));
			
			//System.out.println(belief);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
