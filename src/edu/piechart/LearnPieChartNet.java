package edu.piechart;

import norsys.netica.*;

public class LearnPieChartNet 
{
	public static void main(String[] args) 
	{
		try 
		{
			Environ env = new Environ(null);

			// Use if case file is comma-delimited.
			env.setCaseFileDelimChar(',');

			// Read in the net created by PieChartNet.java
			Net net = new Net(new Streamer("piechart_bayes_data/NetFiles/pieEvidence.dne"));
			NodeList nodes = net.getNodes();
			int numNodes = nodes.size();

			// Remove CPTables of nodes in net, so new ones can be learned.
			for (int n = 0; n < numNodes; n++) 
			{
				Node node = (Node) nodes.get(n);
				node.deleteTables();
			}

			// System.out.println(nodes);

			// Read in the case file pieEvidence.cas and learn new CPTables.
			Streamer caseFile = new Streamer("piechart_bayes_data/DataFiles/pieEvidenceCSV.cas");
			net.reviseCPTsByCaseFile(caseFile, nodes, 1.0);

			// Builds the junction tree of cliques (maximal complete subgraph).
			net.compile();

			// Write the learned network to a dne file.
			net.write(new Streamer("piechart_bayes_data/NetFiles/LearnedPieEvidence.dne"));

			// Show warnings of REPORT_ERR, NOTICE_ERR, or WARNING_ERR.
			System.out.println(NeticaError.getWarnings(NeticaError.WARNING_ERR,env));

			// Frees Netica object's native resources. Not strictly necessary, but a good habit.
			net.finalize();
		}

		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

/*
 * ============================================================== This alternate
 * way can replace the net.reviseCPTsByCaseFile line above, if you need to
 * filter or adjust individual cases.
 * 
 * long[] casePosn = new long[1]; casePosn[0] = Net.FIRST_CASE; while (true) {
 * net.retractFindings(); net.readCase (casePosn, caseFile, nodes, null, null);
 * if (casePosn[0] == Net.NO_MORE_CASES) break;
 * 
 * net.reviseCPTsByFindings (nodes, 1.0); casePosn[0] = Net.NEXT_CASE; }
 * ==============================================================
 */

