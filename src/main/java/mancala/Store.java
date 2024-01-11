package mancala;

import java.io.Serializable;

// should be done
public class Store implements Countable, Serializable {
    private static final long serialVersionUID = 5793768056168366121L;
    private Player owner;
    private int totalStones;

    /**
     * Set the owner of the store to link it to a player.
     *
     * @param player The player that is the owner of the store.
     */
    public void setOwner(final Player player) {
        owner = player;
    }

    /**
     * Get the owner of the store.
     *
     * @return The owner of the store.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Add stones to the store.
     *
     * @param amount The number of stones to add to the store.
     */
    @Override
    public void addStones(final int amount) {
        totalStones += amount;
    }

    /**
     * Add one pit to the store.
     */
    @Override
    public void addStone() {
        totalStones++;
    }

    /**
     * Get the total number of stones in the store.
     *
     * @return The number of stones in the store.
     */
    @Override
    public int getStoneCount() {
        return totalStones;
    }

    /**
     * Removes and returns all stones in the store.
     *
     * @return The number of stones in the player's store.
     */
    @Override
    public int removeStones() {
        final int temp = totalStones;
        totalStones = 0;
        return temp;
    }

    /**
     * Get text representation of the store.
     *
     * @return The text representation of the store.
     */
    @Override
    public String toString() {
        return getOwner()+" has "+getStoneCount()+" stones in their store.";
    }
}
