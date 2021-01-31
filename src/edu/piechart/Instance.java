package edu.piechart;

public class Instance {
    private final String numberOfSlices;
    private final String prominence;
    private final String similarColors;
    private final String multipleSlices;

    public Instance(String[] evidence) {
        //use array from ReadFile to place evidence, no protection
        numberOfSlices = evidence[7];
        prominence = evidence[8];
        similarColors = evidence[9];
        multipleSlices = evidence[10];
    }

    public String getNumberOfSlices() {
        return this.numberOfSlices;
    }

    public String getProminence() {
        return this.prominence;
    }

    public String getSimilarColors() {
        return this.similarColors;
    }

    public String getMultipleSlices() {
        return this.multipleSlices;
    }

}
