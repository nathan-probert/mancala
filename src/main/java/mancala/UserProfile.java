package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = -5148800620959735237L;

    private String name;
    private int kalahGames;
    private int ayoGames;
    private int kalahWins;
    private int ayoWins;

    /**
     * Constructor for the proifle, initialize stats to 0.
     */
    public UserProfile() {
        name = "Player";
        kalahGames = 0;
        ayoGames = 0;
        kalahWins = 0;
        ayoWins = 0;
    }

    /**
     * Set the name of the player and the user profile.
     *
     * @param givenName The name of the player.
     */
    public void setName(final String givenName) {
        name = givenName;
    }

    /**
     * Get the player's name.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Add one to kalah games played.
     */
    public void finishedKalahGame() {
        kalahGames++;
    }

    /**
     * Get the number of kalah games played.
     *
     * @return Number of kalah games played.
     */
    public int kalahGamesPlayed() {
        return kalahGames;
    }

    /**
     * Add one to ayo games played.
     */
    public void finishedAyoGame() {
        ayoGames++;
    }

    /**
     * Get the number of ayo games played.
     *
     * @return Number of ayo games played.
     */
    public int ayoGamesPlayed() {
        return ayoGames;
    }

    /**
     * Add one to kalah games won.
     */
    public void wonKalahGame() {
        kalahWins++;
    }

    /**
     * Get the number of kalah games won.
     *
     * @return Number of kalah games won.
     */
    public int kalahGamesWon() {
        return kalahWins;
    }

    /**
     * Add one to ayo games won.
     */
    public void wonAyoGame() {
        ayoWins++;
    }

    /**
     * Get the number of ayo games won.
     *
     * @return Number of ayo games won.
     */
    public int ayoGamesWon() {
        return ayoWins;
    }
}
