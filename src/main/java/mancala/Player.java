package mancala;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = -2323510519191226220L;
    private Store store;
    private UserProfile user;

    /**
     * Constructor to create a player and link a user profile.
     */
    public Player() {
        this(new UserProfile());
    }

    /**
     * Constructor to create a player and link a user profile.
     *
     * @param profile The user profile to link to the player.
     */
    public Player(final UserProfile profile) {
        user = profile;
    }

    /**
     * Get the player's profile.
     *
     * @return The user profile linked to the player.
     */
    public UserProfile getUser() {
        return user;
    }

    /**
     * Get the player's name.
     *
     * @return The player's name.
     */
    public String getName() {
        return user.getName();
    }

    /**
     * Get the player's store.
     *
     * @return The player's store.
     */
    public Store getStore() {
        return store;
    }

    /**
     * Get the number of stones in a player's store.
     *
     * @return The number of stones in the player's store.
     */
    public int getStoreCount() {
        final Store store = getStore();
        return getStoreCountHelper(store);
    }

    // helper
    private int getStoreCountHelper(final Store store) {
        return store.getStoneCount();
    }

    /**
     * Set the name of the player and the user profile.
     *
     * @param givenName The name of the player.
     */
    public void setName(final String givenName) {
        user.setName(givenName);
    }

    /**
     * Set the store of the player.
     *
     * @param givenStore The store of the player.
     */    
    public void setStore(final Store givenStore) {
        store = givenStore;
    }

    /**
     * Get text representation of the player's state.
     *
     * @return Text representation of the player's current state.
     */
    @Override
    public String toString() {
        return getName()+" has "+getStoreCount()+" stones in their store.";
    }
}
