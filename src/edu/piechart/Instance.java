package edu.piechart;

public class Instance 
{
	public String numberOfSlices;
	public String prominence;
	public String similarColors;
	public String multipleSlices;
	
	public Instance(String[] evidence)
	{
		//use array from ReadFile to place evidence
		numberOfSlices = evidence[7];
		prominence = evidence[8];
		similarColors = evidence[9];
		multipleSlices = evidence[10];
	}
}
