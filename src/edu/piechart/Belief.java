package edu.piechart;

import norsys.netica.*;

public class Belief {

    public String messageCategory;
    public float belief;

    //https://www.norsys.com/netica-j/docs/javadocs/index.html
    //getBeliefs() method?
    Belief(Node node) throws NeticaException {
        messageCategory = node.getName();
        belief = node.getBelief(messageCategory);
    }

}
