package edu.piechart;
/* 
 *  LearnCPTs.java
 *
 *  Example use of Netica-J for learning the CPTs of a Bayes net
 *  from a file of cases.
 *
 * Copyright (C) 1992-2007 by Norsys Software Corp.
 * The software in this file may be copied, modified, and/or included in 
 * derivative works without charge or obligation.
 * This file contains example software only, and Norsys makes no warranty that 
 * it is suitable for any particular purpose, or without defects.
 */

import java.io.File;
import norsys.netica.*;

public class LearnCPTs {

  public static void main(String[] args) {
    System.out.println( "Running Netica-J LearnCPTs example..." );

    try {
	Environ env = new Environ (null);
	
	//use if pieEvidence.cas is comma-delimited
	//env.setCaseFileDelimChar (','); 

	// Read in the net created by the BuildNet.java example program
	Net      net      = new Net (new Streamer ("Data Files/ChestClinic.dne"));
	NodeList nodes    = net.getNodes();
	int      numNodes = nodes.size();
	
	// Remove CPTables of nodes in net, so new ones can be learned.
	for (int n = 0;  n < numNodes;  n++) {
	    Node node = (Node) nodes.get (n);
	    node.deleteTables();
	}
	

	// Read in the case file created by the the SimulateCases.java
	// example program, and learn new CPTables.
	Streamer caseFile = new Streamer ("Data Files/ChestClinic.cas");
	net.reviseCPTsByCaseFile (caseFile, nodes, 1.0);

	net.write (new Streamer ("Data Files/Learned_ChestClinic.dne"));
	
	net.finalize();   // not strictly necessary, but a good habit
    }
    catch (Exception e) {
	e.printStackTrace();
    }
  }
}

	/* ==============================================================
	 * This alternate way can replace the net.reviseCPTsByCaseFile
	 * line above, if you need to filter or adjust individual cases.
	 
	long[] casePosn = new long[1];
	casePosn[0] = Net.FIRST_CASE;
	while (true) {
	    net.retractFindings();
	    net.readCase (casePosn, caseFile, nodes, null, null);
	    if (casePosn[0] == Net.NO_MORE_CASES) break;
	    
	    net.reviseCPTsByFindings (nodes, 1.0);
	    casePosn[0] = Net.NEXT_CASE;
	}
	  ============================================================== */
	
