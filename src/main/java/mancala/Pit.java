package mancala;

import java.io.Serializable;

// should be done
public class Pit implements Countable, Serializable {
    private static final long serialVersionUID = -5900112831982566551L;
    private int stoneCount;

    /**
     * Constructor to create a pit and set number of stones it contains to 0.
     */
    public Pit() {
        stoneCount = 0;
    }

    /**
     * Adds the given number of stones to the pit.
     *
     * @param amount The number of stones to add to the pit.
     */
    @Override
    public void addStones(final int amount) {
        stoneCount += amount;
    }

    /**
     * Adds one stone to the pit.
     */
    @Override
    public void addStone() {
        stoneCount++;
    }

    /**
     * Get the number of stones that are within the pit.
     *
     * @return The number of stones that are in the pit.
     */
    @Override
    public int getStoneCount() {
        return stoneCount;
    }

    /**
     * Empties the pit and returns the number of stones that were in the pit.
     *
     * @return The number of stones in the pit.
     */
    @Override
    public int removeStones() {
        final int temp = stoneCount;
        stoneCount = 0;
        return temp;
    }


    /**
     * Get text representation of the pit's state.
     *
     * @return Text representation of the pit's current state.
     */
    @Override
    public String toString() {
        return "There are "+getStoneCount()+" stones in this pit.";
    }
}

