package edu.piechart;

public class Instance 
{
	private String numberOfSlices;
	private String prominence;
	private String similarColors;
	private String multipleSlices;
	
	public Instance(String[] evidence)
	{
		//use array from ReadFile to place evidence, no protection
		numberOfSlices = evidence[7];
		prominence = evidence[8];
		similarColors = evidence[9];
		multipleSlices = evidence[10];
	}
	
	public String getNumberOfSlices()
	{
		return numberOfSlices;
	}
	
	public String getProminence()
	{
		return prominence;
	}
	
	public String getSimilarColors()
	{
		return similarColors;
	}
	
	public String getMultipleSlices()
	{
		return multipleSlices;
	}
}
