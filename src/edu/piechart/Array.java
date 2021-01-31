package edu.piechart;

public class Array {

    public static void multipleLinePrint(float[] array, int numberPerLine) {
        for (int index = 0; index < array.length; index++) {
            if (index != 0 && index % numberPerLine == 0) {
                System.out.println();
            }
            System.out.print(array[index] + "   ");
        }
        System.out.println();
    }

    public static void multipleLinePrint(String[] array, int numberPerLine) {
        for (int index = 0; index < array.length; index++) {
            if (index != 0 && index % numberPerLine == 0) {
                System.out.println();
            }
            System.out.print(array[index] + "   ");
        }
        System.out.println();
    }

    public static float max(float[] array) {
        float max = 0;

        for (float v : array) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    public static int findIndex(float[] array) {
        float largest = array[0];
        int largestIndex = 0;

        for (int index = 0; index < array.length; index++) {
            if (array[index] > largest) {
                largest = array[index];
                largestIndex = index;
            }
        }
        return largestIndex;
    }

}
