package main.java.edu.piechart.net;

import norsys.netica.*;

public class LearnPieChartNet {

    public static void main(String[] args) {
        try {
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

            // turns auto-updating on or off
            net.setAutoUpdate(1);

            // Write the learned network to a dne file.
            Streamer stream = new Streamer("piechart_bayes_data/NetFiles/LearnedPieEvidence.dne");
            net.write(stream);

            System.out.println("execution complete");

            // Frees Netica object's native resources. Not strictly necessary, but a good habit.
            net.finalize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // function to remove the Conditional Probability Tables from a net
    public static void removeCPTables(String filename) throws NeticaException {
        Net net = new Net(new Streamer(filename));
        NodeList nodes = net.getNodes();
        int numberOfNodes = nodes.size();

        // Remove CPTables of nodes in net, so new ones can be learned.
        for (Object o : nodes) {
            Node node = (Node) o;
            node.deleteTables();
        }
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
