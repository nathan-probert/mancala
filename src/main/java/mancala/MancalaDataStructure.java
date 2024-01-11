package mancala;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


/**
 * Represents a Mancala data structure for the Mancala game.
 * Do not change the signature of any of the methods provided.
 * You may add methods if you need them.
 * Do not add game logic to this class
 */
public class MancalaDataStructure implements Serializable {
    private final int PLAYER_ONE = 6;
    private final int PLAYER_TWO = 13;
    private  int START_STONES = 4;  //not final because we might want a different size board in the future

    final private List<Countable> data = new ArrayList<>();
    private int iteratorPos = 0;
    private int playerSkip = PLAYER_TWO;
    private int pitSkip = -1; // will never match the iteratorPos unless set specifically

    
    /**
     * Constructor to initialize the MancalaDataStructure.
     * 
     * @param startStones The number of stones to place in pits at the start of the game. Default values is 4.
     */
    public MancalaDataStructure(final int startStones){
        START_STONES = startStones;
        for (int i = 0; i < PLAYER_ONE; i++) {
            data.add(new Pit());
        }
        data.add(new Store());
        for (int i = 7; i < PLAYER_TWO; i++) {
            data.add(new Pit());
        }
        data.add(new Store());
    }


    /**
     * Constructor to initialize the MancalaDataStructure.
     */
    public MancalaDataStructure() {
        this(4);
    }

    /**
     * Adds stones to a pit.
     *
     * @param pitNum   The number of the pit.
     * @param numToAdd The number of stones to add.
     * @return The current number of stones in the pit.
     */
    public int addStones(final int pitNum, final int numToAdd) {
        final Countable pit = data.get(pitPos(pitNum));
        pit.addStones(numToAdd);
        return pit.getStoneCount();
    }

    /**
     * Removes stones from a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones removed.
     */
    public int removeStones(final int pitNum) {
        final Countable pit = data.get(pitPos(pitNum));
        return pit.removeStones();
    }

    /**
     * Adds stones to a player's store.
     *
     * @param playerNum The player number (1 or 2).
     * @param numToAdd  The number of stones to add to the store.
     * @return The current number of stones in the store.
     */
    public int addToStore(final int playerNum, final int numToAdd) {
        final Countable store = data.get(storePos(playerNum));
        store.addStones(numToAdd);
        return store.getStoneCount();
    }

    /**
     * Gets the stone count in a player's store.
     *
     * @param playerNum The player number (1 or 2).
     * @return The stone count in the player's store.
     */
    public int getStoreCount(final int playerNum) {
        final Countable store = data.get(storePos(playerNum));
        return store.getStoneCount();
    }

    /**
     * Gets the stone count in a given  pit.
     *
     * @param pitNum The number of the pit.
     * @return The stone count in the pit.
     */
    public int getNumStones(final int pitNum) {
        final Countable pit = data.get(pitPos(pitNum));
        return pit.getStoneCount();
    }    

    /*helper method to convert 1 based pit numbers into array positions*/
    private int pitPos(final int pitNum) {
        /*Runtime execeptions don't need to be declared and are
        automatically passed up the chain until caught. This can
        replace the PitNotFoundException*/
        if(pitNum<1 || pitNum > 12){
            throw new RuntimeException("Pit Number Out of Range");
        }
        int pos = pitNum;
        if (pos <= PLAYER_ONE) {
            pos--;
        }
        return pos;
    }

    /*helper method to convert player number to an array position*/
    private int storePos(final int playerNum) {
        if(playerNum <1 || playerNum > 2){
            throw new RuntimeException("Invalid Player Position");
        }

        int pos;
        if (playerNum == 2) {
            pos = PLAYER_TWO;
        } else {
            pos = PLAYER_ONE;
        }
        return pos;
    }

    /**
     * Empties both players' stores.
     */
    public void emptyStores() {
        data.set(storePos(1), new Store());
        data.set(storePos(2), new Store());
    }

    /**
     * Sets up pits with a specified number of starting stones.
     *
     * @param startingStonesNum The number of starting stones for each pit.
     */
    public void setUpPits() {
        for (int i = 0; i < PLAYER_ONE; i++) {
            data.get(i).addStones(START_STONES);
        }

        for (int i = 7; i < PLAYER_TWO; i++) {
            data.get(i).addStones(START_STONES);
        }
    }

    /**
     * Adds a store that is already connected to a Player.
     *
     * @param store     The store to set.
     * @param playerNum The player number (1 or 2).
     */
    public void setStore(final Countable store, final int playerNum) {
        data.set(storePos(playerNum), store);
    }
    /*helper method for wrapping the iterator around to the beginning again*/
    private void loopIterator() {
        if (iteratorPos == PLAYER_TWO + 1) {
            iteratorPos = 0;
        }
    }

    private void skipPosition() {
        while (iteratorPos == playerSkip || iteratorPos == pitSkip) {
            iteratorPos++;
            loopIterator();
        }
    }

    private void setSkipPlayer(final int playerNum) {
        //sets the skip store to be the opposite player
        playerSkip = PLAYER_TWO;
        if (playerNum == 2) {
            playerSkip = PLAYER_ONE;
        }
    }

    private void setSkipPit(final int pitNum) {
        pitSkip = pitPos(pitNum);
    }

    /**
     * Sets the iterator position and positions to skip when iterating.
     *
     * @param startPos       The starting position for the iterator.
     * @param playerNum      The player number (1 or 2).
     * @param skipStartPit   Whether to skip the starting pit.
     */
    public void setIterator(final int startPos, final int playerNum, final boolean skipStartPit) {
        iteratorPos = pitPos(startPos);
        setSkipPlayer(playerNum);
        if (skipStartPit) {
            setSkipPit(startPos);
        }
    }

    // give me pit number
    public int getIteratorPos() {
        int toReturn = iteratorPos;
        if (iteratorPos == PLAYER_ONE) {
            toReturn = -1;
        } else if (iteratorPos == PLAYER_TWO) {
            toReturn = -1;
        } else if (iteratorPos < PLAYER_ONE) {
            toReturn = iteratorPos+1;
        }
        return toReturn;
    }

    /**
     * Moves the iterator to the next position.
     *
     * @return The countable object at the next position.
     */
    public Countable next() {
        iteratorPos++;
        loopIterator(); // in case we've run off the end
        skipPosition(); // skip store and start position if necessary
        return data.get(iteratorPos);
    }
}
