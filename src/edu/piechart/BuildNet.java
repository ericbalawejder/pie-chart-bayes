package edu.piechart;
/* 
 *  BuildNet.java
 *
 *  Example use of Netica-J to construct a Bayes net and save it to file.
 *
 * Copyright (C) 1992-2007 by Norsys Software Corp.
 * The software in this file may be copied, modified, and/or included in 
 * derivative works without charge or obligation.
 * This file contains example software only, and Norsys makes no warranty that 
 * it is suitable for any particular purpose, or without defects.
 */
import norsys.netica.*;
import norsys.neticaEx.aliases.Node;
     
public class BuildNet {

  public static void main (String[] args){
    try {
	Node.setConstructorClass ("norsys.neticaEx.aliases.Node");
	Environ env = new Environ (null);

	Net net = new Net();
	net.setName("ChestClinic");

	Node visitAsia    = new Node ("VisitAsia",   "visit,no_visit",  net);
	Node tuberculosis = new Node ("Tuberculosis","present,absent",  net);
	Node smoking      = new Node ("Smoking",     "smoker,nonsmoker",net);
	Node cancer       = new Node ("Cancer",      "present,absent",  net);
	Node tbOrCa       = new Node ("TbOrCa",      "true,false",      net);
	Node xRay         = new Node ("XRay",        "abnormal,normal", net);
	Node dyspnea      = new Node ("Dyspnea",     "present,absent",  net);
	Node bronchitis   = new Node ("Bronchitis",  "present,absent",  net);

	visitAsia.setTitle ("Visit to Asia");
	cancer.setTitle ("Lung Cancer");
	tbOrCa.setTitle ("Tuberculosis or Cancer");

	visitAsia.state("visit").setTitle ("Visited Asia within the last 3 years");
	
	tuberculosis.addLink (visitAsia); // link from visitAsia to tuberculosis
	cancer.addLink (smoking);
	tbOrCa.addLink (tuberculosis);
	tbOrCa.addLink (cancer);
	xRay.addLink (tbOrCa);
	dyspnea.addLink (tbOrCa);
	bronchitis.addLink (smoking);
	dyspnea.addLink (bronchitis);

	visitAsia.setCPTable (0.01, 0.99);

	                       // VisitAsia   present  absent
	tuberculosis.setCPTable ("visit",     0.05,    0.95);
	tuberculosis.setCPTable ("no_visit",  0.01,    0.99);

	smoking.setCPTable (0.5, 0.5);

	                 // Smoking      present  absent
	cancer.setCPTable ("smoker",     0.1,     0.9);
	cancer.setCPTable ("nonsmoker",  0.01,    0.99);

	                     // Smoking      present  absent
	bronchitis.setCPTable ("smoker",     0.6,     0.4);
	bronchitis.setCPTable ("nonsmoker",  0.3,     0.7);
	
	tbOrCa.setEquation ("TbOrCa (Tuberculosis, Cancer) = Tuberculosis || Cancer");
	tbOrCa.equationToTable (1, false, false);

	//the above is a convenient way of doing:
	//tbOrCa.setCPTable ("present", "present", 1.0, 0.0);
	//tbOrCa.setCPTable ("present", "absent",  1.0, 0.0);
	//tbOrCa.setCPTable ("absent",  "present", 1.0, 0.0);
	//tbOrCa.setCPTable ("absent",  "absent",  0.0, 1.0);

	               // TbOrCa    abnormal normal
	xRay.setCPTable ("true",    0.98,    0.02);
	xRay.setCPTable ("false",   0.05,    0.95);

	                 // TbOrCa   Bronchitis 
	dyspnea.setCPTable ("true",  "present", 0.9, 0.1);
	dyspnea.setCPTable ("true",  "absent",  0.7, 0.3);
	dyspnea.setCPTable ("false", "present", 0.8, 0.2);
	dyspnea.setCPTable ("false", "absent",  0.1, 0.9);

	Streamer stream = new Streamer ("Data Files/ChestClinic.dne");
	net.write (stream);

	net.finalize();  // free resources immediately and safely; not strictly necessary, but a good habit
    }
    catch (Exception e) {
	e.printStackTrace();
    }
  }
}
