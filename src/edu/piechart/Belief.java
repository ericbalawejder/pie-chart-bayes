package edu.piechart;

import norsys.netica.*;

public class Belief 
{
	public String messageCategory;
	public double belief;
	
	public static void main(String[] args) 
	{
		
	}
	
	Belief (Node node) throws NeticaException
	{
		messageCategory = node.getName();
		belief = node.getBelief(messageCategory);
	}
}
