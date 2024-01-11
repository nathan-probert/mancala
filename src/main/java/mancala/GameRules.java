package mancala;

import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {
    private static final long serialVersionUID = 4565554943701906406L;
    final private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)
    protected static final int PITS_PER_SIDE = 6;
    protected static final int DOUBLE_DIGITS = 10;
    protected static final int NUM_CAPTURE = 1;

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
        this.gameBoard.setUpPits();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    protected MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    protected boolean isSideEmpty(final int pitNum) {
        // This method can be implemented in the abstract class.
        int stones=0;
        if (pitNum > 0 && pitNum < 7) {
            for (int i=1; i<7; i++) {
                stones+=getNumStones(i);
            }
        } else if (pitNum > 6 && pitNum < 13) {
            for (int i=7; i<13; i++) {
                stones+=getNumStones(i);
            }
        }

        return stones == 0;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    // // i make this
    // public int getPlayer() {
    //     return currentPlayer;
    // }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    protected abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    protected abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // this method can be implemented in the abstract class.


        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/

        final Store store1 = new Store();
        store1.setOwner(one);
        one.setStore(store1);
        gameBoard.setStore(store1, 1);

        final Store store2 = new Store();
        store2.setOwner(two);
        two.setStore(store2);
        gameBoard.setStore(store2, 2);

        currentPlayer = 1;
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        // empty pits
        final MancalaDataStructure dataStructure = getDataStructure();
        for (int i=1; i<7; i++) {
            dataStructure.removeStones(i);
            dataStructure.removeStones(i+6);
        }
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    // helper method
    protected boolean isValidMove(final int startPit, final int playerNum) {
        boolean move;
        if (playerNum == 1 && (startPit > 6 || startPit < 1)) {
            move = false;
        } else if (playerNum == 2 && (startPit > 12 || startPit < 7)) {
            move = false;
        } else if (getNumStones(startPit) == 0) {
            move = false;
        } else {
            if (currentPlayer == 0) {
                move = true;
            } else {
                move = true;
            }
        }

        return move;
    }

    // helper method galore
    protected int getStoreCount(final int playerNum, final MancalaDataStructure dataStructure) {
        return dataStructure.getStoreCount(playerNum);
    }

    protected int removeStones(final int pitNum, final MancalaDataStructure dataStructure) {
        return dataStructure.removeStones(pitNum);
    }

    protected int addToStore(final int playerNum, final int stonesToAdd, final MancalaDataStructure dataStructure) {
        return dataStructure.addToStore(playerNum, stonesToAdd);
    }

    // helper method
    protected int getPlayerNum(final int startingPit) {
        int playerNum;
        if (startingPit <= PITS_PER_SIDE) {
            playerNum = 1;
        } else {
            playerNum = 2;
        }
        return playerNum;
    }

    protected int getCurrentPit(final MancalaDataStructure dataStructure) {
        return dataStructure.getIteratorPos();
    }

    protected int doCapture(final int playerNum, final MancalaDataStructure dataStructure, final int currentPitCount) {
        int capturedStones;

        if (playerNum == 2 && getCurrentPit(dataStructure) > 6 && currentPitCount == 1){
            capturedStones = captureStones(getCurrentPit(dataStructure));
        } else if (playerNum == 1 && getCurrentPit(dataStructure) < 7 && currentPitCount == 1) {
            capturedStones = captureStones(getCurrentPit(dataStructure));
        } else {
            capturedStones = 0;
        }

        return capturedStones;
    }

    protected void setIterator(final MancalaDataStructure dataStructure, final int startingPoint, final int playerNum, final boolean skipFirst) {
        dataStructure.setIterator(startingPoint, playerNum, skipFirst);
    }
    
    // helper function to move pit stones into stores at the end of a game
    protected void movePitsToStores() {      
        int totalStonesP1 = 0;
        int totalStonesP2 = 0;
        final MancalaDataStructure dataStructure = getDataStructure();

        for (int i=1; i<7; i++) {
            totalStonesP1 += dataStructure.removeStones(i);
            totalStonesP2 += dataStructure.removeStones(i+6);
        }

        addToStore(1, totalStonesP1, dataStructure);
        addToStore(2, totalStonesP2, dataStructure);
    }

    /**
     * Get text representation of the board's state.
     *
     * @return Text representation of the board's current state.
     */
    @Override
    public String toString() {
        final MancalaDataStructure dataStructure = getDataStructure();

        String line = "";
        for (int i=0; i<39; i++) {
            line+="-";
        }

        String mline = " "+getStoreCount(2,dataStructure);
        mline += " -------------------------------- ";
        mline += getStoreCount(1, dataStructure)+" ";
        String bpit = "    |"; // bottom pit
        String tpit = "    |"; // top pit
        for (int i=1; i<=PITS_PER_SIDE; i++) {
            final int stones = dataStructure.getNumStones(i);
            if (stones < DOUBLE_DIGITS) {
                bpit += " "+stones+"  |";
            } else {
                bpit += " "+stones+" |";
            }
        }
        for (int i=12; i>PITS_PER_SIDE; i--) {
            final int stones = dataStructure.getNumStones(i);
            if (stones < DOUBLE_DIGITS) {
                tpit += " "+stones+"  |";
            } else {
                tpit += " "+stones+" |";
            }
        }
        return line+"\n"+tpit+"\n"+mline+"\n"+bpit+"\n"+line;
    }

}
