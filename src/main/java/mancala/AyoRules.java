package mancala;

public class AyoRules extends GameRules {
    private static final long serialVersionUID = 4651301351614241909L;

    /**
     * Move stones from the initial pit.
     *
     * @param startPit The inital pit that stones will be moved from.
     * @param playerNum The player number (1 or 2).
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException When a player attempts to move from a pit that isn't theirs.
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if (!isValidMove(startPit, playerNum)) {
            throw new InvalidMoveException();
        }
        setPlayer(playerNum);
        final MancalaDataStructure dataStructure = getDataStructure();
        final int preMoveStoreCount = getStoreCount(playerNum, dataStructure);
        int toReturn;

        distributeStones(startPit);
        final int postCount = getStoreCount(playerNum, dataStructure);
        toReturn = postCount - preMoveStoreCount;

        return toReturn;
    }

    /**
     * Capture the stones from the pit and it's complement.
     *
     * @param stoppingPoint The last pit that a stone was distributed to.
     * @return The number of stones captured.
     */
    @Override
    public int captureStones(final int stoppingPoint)  {
        final int otherPit = 13-stoppingPoint;
        int stonesCaptured;

        // get store
        if (getNumStones(otherPit) == 0) {
            stonesCaptured = 0;
        } else {
            final MancalaDataStructure dataStructure = getDataStructure();
            stonesCaptured = removeStones(otherPit, dataStructure);
            if (stoppingPoint <= PITS_PER_SIDE) {
                // add to player 1's store
                addToStore(1,stonesCaptured, dataStructure);
            } else {
                // add to player 2's store
                addToStore(2,stonesCaptured, dataStructure);
            }
        }
        // return number of stones captured
        return stonesCaptured;
    }

    /**
     * Distributes stones to the proper pits.
     *
     * @param startingPoint The inital pit that stones will be moved from.
     * @return Number of stones distributed or captured.
     */
    @Override
    public int distributeStones(final int startingPoint) {

        int initialStones = 0;
        int stonesToMove;
        Countable currentPit;
        final int playerNum = getPlayerNum(startingPoint);
        final MancalaDataStructure dataStructure = getDataStructure();

        setIterator(dataStructure, startingPoint, playerNum, true);
        int capturedStones;
        boolean repeat;

        do {
            stonesToMove = removeStones(getCurrentPit(dataStructure), dataStructure);
            initialStones += stonesToMove;
            while (stonesToMove > 0) {
                currentPit = dataStructure.next();
                currentPit.addStone();
                stonesToMove--;
            }

            if (getCurrentPit(dataStructure) == -1) {
                repeat = false;
            } else {
                if (getNumStones(getCurrentPit(dataStructure)) > NUM_CAPTURE) {
                    repeat = true;
                } else {
                    repeat = false;
                }
            }
        } while (repeat);

        if (getCurrentPit(dataStructure) == -1) {
            capturedStones = 0;
        } else {
            final int currentPitCount = getNumStones(getCurrentPit(dataStructure));
            capturedStones = doCapture(playerNum, dataStructure, currentPitCount);
        }

        return initialStones+capturedStones;
    }
    
}

