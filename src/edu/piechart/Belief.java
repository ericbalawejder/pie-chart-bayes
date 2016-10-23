package edu.piechart;

import norsys.netica.*;

public class Belief 
{
	public String messageCategory;
	// getBelief method returns a float
	public float belief;
	
	//public String[] messageCategoryNode = {""};
	
	Belief (Node node) throws NeticaException
	{
		messageCategory = node.getName();
		belief = node.getBelief(messageCategory);
	}
}
