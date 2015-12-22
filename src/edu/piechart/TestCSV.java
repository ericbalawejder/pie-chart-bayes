package edu.piechart;
import java.io.File;
import norsys.netica.*;

public class TestCSV {
	public static void main(String[] args) {
		try {
			Environ env = new Environ(null);

			// Use if case file is comma-delimited.
			env.setCaseFileDelimChar (','); 
			
			// Read in the trained net.
			Net net = new Net (new Streamer ("piechart_bayes_data/NetFiles/LearnedPieEvidence.dne"));
			NodeList nodes = net.getNodes();
			
			// Read in the case file
			Streamer caseFile = new Streamer ("piechart_bayes_data/DataFiles/pieEvidencePredict.cas");
			//net.reviseCPTsByCaseFile (caseFile, nodes, 1.0);
			
			Node intendedMessage = net.getNode("IntendedMessage");
			Node numberOfSlices = net.getNode("NumberOfSlices");
			Node prominence = net.getNode("Prominence");
			Node similarColors = net.getNode("SimilarColors");
			Node multipleSlices = net.getNode("MultipleSlices");
			
			// Builds the junction tree of cliques (maximal complete subgraph).
			net.compile();
			
			double belief = intendedMessage.getBelief ("singleSlice"); 
			System.out.println(belief);
			
			numberOfSlices.finding().enterState ("fiveOrMore");
			System.out.println(belief);
			
			prominence.finding().enterState ("yes");
			System.out.println(belief);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
