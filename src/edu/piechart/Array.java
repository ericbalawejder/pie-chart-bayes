package edu.piechart;

public class Array
{
	// array print method
	public static void print(float[] array) 
	{
		for (int index = 0; index < array.length; index++) 
		{
			System.out.print(array[index] + "   ");
		}
		System.out.println();
	}

	// array print method overloaded for type String[]
	public static void print(String[] array) 
	{
		for (int index = 0; index < array.length; index++) 
		{
			System.out.print(array[index] + "   ");
		}
		System.out.println();
	}

	// array print method with added number per line parameter
	public static void multipleLinePrint(float[] array, int numberPerLine) 
	{
		for (int index = 0; index < array.length; index++) 
		{
			if (index != 0 && index % numberPerLine == 0) 
			{
				System.out.println();
			}
			System.out.print(array[index] + "   ");
		}
		System.out.println();
	}

	// array print method overloaded for type String[]
	public static void multipleLinePrint(String[] array, int numberPerLine) 
	{
		for (int index = 0; index < array.length; index++) 
		{
			if (index != 0 && index % numberPerLine == 0) 
			{
				System.out.println();
			}
			System.out.print(array[index] + "   ");
		}
		System.out.println();
	}

	// method to find the max value in an array of floats
	public static float max(float[] array) 
	{
		float max = 0;

		for (int index = 0; index < array.length; index++) 
		{
			if (array[index] > max) 
			{
				max = array[index];
			}
		}
		return max;
	}

	// method to return the array index of the max value
	public static int findIndex(float array[]) 
	{
		float largest = array[0];
		int largestIndex = 0;

		for (int index = 0; index < array.length; index++) 
		{
			if (array[index] > largest) 
			{
				largest = array[index];
				largestIndex = index;
			}
		}
		return largestIndex;
	}
}
