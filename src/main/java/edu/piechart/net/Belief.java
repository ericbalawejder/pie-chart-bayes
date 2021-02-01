package main.java.edu.piechart.net;

import norsys.netica.NeticaException;
import norsys.netica.Node;

import java.util.Objects;

public class Belief {

    private final String messageCategory;
    private final float belief;

    public Belief(Node node) throws NeticaException {
        this.messageCategory = node.getName();
        this.belief = node.getBelief(this.messageCategory);
    }

    public String getMessageCategory() {
        return this.messageCategory;
    }

    public float getBelief() {
        return this.belief;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Belief belief1 = (Belief) o;
        return Float.compare(belief1.belief, belief) == 0
                && Objects.equals(messageCategory, belief1.messageCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageCategory, belief);
    }

    @Override
    public String toString() {
        return "Belief{" +
                "messageCategory='" + messageCategory + '\'' +
                ", belief=" + belief +
                '}';
    }

}
