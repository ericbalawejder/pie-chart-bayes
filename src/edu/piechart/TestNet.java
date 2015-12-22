package edu.piechart;
/* 
 *  TestNet.java
 *
 *  Example use of Netica-J for testing the performance of 
 *  a learned net with the net tester tool.
 *
 * Copyright (C) 1992-2007 by Norsys Software Corp.
 * The software in this file may be copied, modified, and/or included in 
 * derivative works without charge or obligation.
 * This file contains example software only, and Norsys makes no warranty that 
 * it is suitable for any particular purpose, or without defects.
 */

import java.io.File;
import norsys.netica.*;

public class TestNet {

	public static void main(String[] args) {
		System.out.println("Running Netica-J TestNet example...");

		try {
			Environ env = new Environ(null);

			Net net = new Net(new Streamer("Data Files/ChestClinic.dne"));
			NodeList testNodes = new NodeList(net);
			NodeList unobsvNodes = new NodeList(net);

			Node visitAsia = net.getNode("VisitAsia");
			Node tuberculosis = net.getNode("Tuberculosis");
			Node cancer = net.getNode("Cancer");
			Node smoking = net.getNode("Smoking");
			Node tbOrCa = net.getNode("TbOrCa");
			Node xRay = net.getNode("XRay");
			Node dyspnea = net.getNode("Dyspnea");
			Node bronchitis = net.getNode("Bronchitis");

			// The observed nodes are typically the factors known during
			// diagnosis
			testNodes.add(cancer);

			// The unobserved nodes are typically the factors not known during
			// diagnosis:
			unobsvNodes.add(bronchitis);
			unobsvNodes.add(tuberculosis);
			unobsvNodes.add(tbOrCa);

			net.retractFindings(); // IMPORTANT: Otherwise any findings will be
									// part of tests !!
			net.compile();

			NetTester tester = new NetTester(testNodes, unobsvNodes, -1);

			Streamer inStream = new Streamer("Data Files/ChestClinic.cas");
			Caseset testCases = new Caseset();
			testCases.addCases(inStream, 1.0, null);
			tester.testWithCaseset(testCases);

			printConfusionMatrix(tester, cancer);
			System.out.println("      Error rate for " + cancer.getName()
					+ " = " + tester.getErrorRate(cancer));
			System.out.println("Logarithmic loss for " + cancer.getName()
					+ " = " + tester.getLogLoss(cancer));
			// the following are not strictly necessary, but a good habit
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
	public static void printConfusionMatrix(NetTester nt, Node node)
			throws NeticaException {
		int numStates = node.getNumStates();
		System.out.println("\nConfusion matrix for " + node.getName() + ":");

		for (int i = 0; i < numStates; ++i) {
			System.out.print("\t" + node.state(i).getName());
		}
		System.out.println("\tActual");

		for (int a = 0; a < numStates; ++a) {
			for (int p = 0; p < numStates; ++p) {
				System.out.print("\t" + (int) (nt.getConfusion(node, p, a)));
			}
			System.out.println("\t" + node.state(a).getName());
		}
	}
}
