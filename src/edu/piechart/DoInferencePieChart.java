package edu.piechart;
import norsys.netica.*;
     
public class DoInferencePieChart {

  public static void main (String[] args){
    
	try {
	Environ env = new Environ (null);

	// Read in the net created by the PieChartNet.java example program. No training data.
	//Net net = new Net (new Streamer ("PieChart_bayes_data/pieEvidence.dne"));
	
	// Read in the learned net created by LearnPieChartNet.java.
	Net net = new Net (new Streamer ("piechart_bayes_data/NetFiles/LearnedPieEvidence.dne"));
	
	// Read in the learned net created by LearnPieChartNet.java.
	//Streamer caseFile = new Streamer ("piechart_bayes_data/DataFiles/pieEvidenceCSV.cas");
	//net.reviseCPTsByCaseFile (caseFile, nodes, 1.0);
	
	Node intendedMessage = net.getNode("IntendedMessage");
	Node numberOfSlices = net.getNode("NumberOfSlices");
	Node prominence = net.getNode("Prominence");
	Node similarColors = net.getNode("SimilarColors");
	Node multipleSlices = net.getNode("MultipleSlices");
	
	// Builds the junction tree of cliques (maximal complete subgraph).
	net.compile();
	
	// Place evidence data here.
	String message = "singleSlice";
	String numOfSlices = "fiveOrMore";
	String prom = "yes";
	String simColors = "no";
	String multSlices = "yes";
	
	// Read in the case file pieEvidence.cas and learn new CPTables.
	//Streamer caseFile = new Streamer ("PieChart_bayes_data/pieEvidenceCSV.cas");
	//net.reviseCPTsByCaseFile (caseFile, nodes, 1.0);
	
	// Determine the probability that a message is single slice.
	double belief = intendedMessage.getBelief (message); 
	System.out.println ("\nThe probability of " + message + " message is " + belief);

	numberOfSlices.finding().enterState (numOfSlices);
	belief = intendedMessage.getBelief (message);          
	System.out.println ("\nGiven a pie chart with " + numOfSlices + " slices,\n"+
			    "the probability of a " + message + " message is " + belief);

	prominence.finding().enterState (prom);
	belief = intendedMessage.getBelief (message);          
	System.out.println ("\nGiven a pie chart with " + numOfSlices + " slices and prominence,\n" +
			    "the probability of a " + message + " message is " + belief);

	similarColors.finding().enterState (simColors);
	belief = intendedMessage.getBelief (message);          
	System.out.println ("\nGiven a pie chart with " + numOfSlices + " slices, prominence\n" +
			    "and " + simColors + " similar colors, the probability of a " + message + " message is " + belief);
	
	multipleSlices.finding().enterState (multSlices);
	belief = intendedMessage.getBelief (message);          
	System.out.println ("\nGiven a pie chart with " + numOfSlices + " slices, prominence,\n" +
			    simColors + " similar colors and " + multSlices + " multiple slices, the probability of " + 
			    "a " + message + " message is " + belief + "\n");
	
	// Not strictly necessary, but a good habit.
	net.finalize();   
    }
    catch (Exception e) {
    	e.printStackTrace();
    }
  }
}
