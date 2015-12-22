package edu.piechart;
/* 
 *  DoInference.java
 *
 *  Example use of Netica-J for doing probabilistic inference.
 *
 * Copyright (C) 1992-2007 by Norsys Software Corp.
 * The software in this file may be copied, modified, and/or included in 
 * derivative works without charge or obligation.
 * This file contains example software only, and Norsys makes no warranty that 
 * it is suitable for any particular purpose, or without defects.
 */
import norsys.netica.*;

public class DoInference {

	public static void main(String[] args) {
		try {
			Environ env = new Environ(null);

			// Read in the net created by the BuildNet.java example program
			Net net = new Net(new Streamer("Data Files/ChestClinic.dne"));

			Node visitAsia = net.getNode("VisitAsia");
			Node tuberculosis = net.getNode("Tuberculosis");
			Node xRay = net.getNode("XRay");

			net.compile();

			double belief = tuberculosis.getBelief("present");
			System.out
					.println("\nThe probability of tuberculosis is " + belief);

			xRay.finding().enterState("abnormal");
			belief = tuberculosis.getBelief("present");
			System.out.println("\nGiven an abnormal X-ray,\n"
					+ "the probability of tuberculosis is " + belief);

			visitAsia.finding().enterState("visit");
			belief = tuberculosis.getBelief("present");
			System.out
					.println("\nGiven an abnormal X-ray and a visit to Asia,\n"
							+ "the probability of tuberculosis is " + belief
							+ "\n");

			net.finalize(); // not strictly necessary, but a good habit
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
