package edu.piechart;
import java.io.File;
import norsys.netica.*;

public class TestPieChartNet 
{
	public static void main(String[] args) 
	{
		System.out.println("Running Netica-J TestNet example...");

		try {
			Environ env = new Environ(null);

			Net net = new Net(new Streamer("piechart_bayes_data/NetFiles/LearnedPieEvidence.dne"));
			NodeList testNodes = new NodeList(net);
			NodeList unobsvNodes = new NodeList(net);

			Node intendedMessage = net.getNode("IntendedMessage");
			Node numberOfSlices = net.getNode("NumberOfSlices");
			Node prominence = net.getNode("Prominence");
			Node similarColors = net.getNode("SimilarColors");
			Node multipleSlices = net.getNode("MultipleSlices");

			// The observed nodes are typically the factors known during
			// diagnosis
			//testNodes.add(numberOfSlices);
			testNodes.add(prominence);
			//testNodes.add(similarColors);
			//testNodes.add(multipleSlices);

			// The unobserved nodes are typically the factors not known during
			// diagnosis:
			unobsvNodes.add(similarColors);
			unobsvNodes.add(multipleSlices);

			// IMPORTANT: Otherwise any findings will be part of tests !!
			net.retractFindings();
			net.compile();

			NetTester tester = new NetTester(testNodes, unobsvNodes, -1);

			Streamer inStream = new Streamer(
					"piechart_bayes_data/DataFiles/pieEvidenceCSV.cas");
			Caseset testCases = new Caseset();
			testCases.addCases(inStream, 1.0, null);
			tester.testWithCaseset(testCases);

			printConfusionMatrix(tester, prominence);
			System.out.println("      Error rate for "
					+ prominence.getName() + " = "
					+ tester.getErrorRate(prominence));
			System.out.println("Logarithmic loss for "
					+ prominence.getName() + " = "
					+ tester.getLogLoss(prominence));

			// The following are not strictly necessary, but a good habit
			testCases.finalize();
			tester.finalize();
			net.finalize();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Print a confusion matrix table. This method can be found in
	 * examples/TestNet.java that comes with this distribution.
	 */
	public static void printConfusionMatrix(NetTester nt, Node node) throws NeticaException 
	{
		int numStates = node.getNumStates();
		System.out.println("\nConfusion matrix for " + node.getName() + ":");

		for (int i = 0; i < numStates; ++i) 
		{
			System.out.print("\t" + node.state(i).getName());
		}
		System.out.println("\tActual");

		for (int a = 0; a < numStates; ++a) 
		{
			for (int p = 0; p < numStates; ++p) 
			{
				System.out.print("\t" + (int) (nt.getConfusion(node, p, a)));
			}
			System.out.println("\t" + node.state(a).getName());
		}
	}
}
