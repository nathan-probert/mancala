package mancala;

import java.util.ArrayList;
import java.io.Serializable;


public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 1813787854797966562L;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private GameRules rules;
    private String gameName;

    private static final int MAX_PLAYER_PIT1 = 6;
    private static final int MAX_PLAYER_PIT2 = MAX_PLAYER_PIT1 * 2;
    private static final int ONE = 1;

    /**
     * Constructor to create new game instance.
     */
    public MancalaGame() {
        players = new ArrayList<>();
    }

    /**
     * Set the rule name of the ruleset.
     *
     * @param name The name of the ruleset.
     */
    public void setRuleName(final String name) {
        gameName = name;
    }

    /**
     * Get the name of the ruleset.
     *
     * @return The name of the ruleset.
     */
    public String getRuleName() {
        return gameName;
    }

    /**
     * Get the instance of the game rules.
     *
     * @return The instance of the game rules
     */
    public GameRules getBoard() {
        return rules;
    }

    /**
     * Set the bonus for Kalah games.
     *
     * @param givenBonus State of bonus turn.
     */
    public void setBonus(final boolean givenBonus) {
        final KalahRules kRules = (KalahRules) rules;
        setBonusRules(givenBonus, kRules);
    }

    // heler for the helper
    private void setBonusRules(final boolean givenBonus, final KalahRules kRules) {
        kRules.setBonus(givenBonus);
    }

    /**
     * Check if a bonus turn is applicable for a Kalah game.
     *
     * @return is bonus turn applicable (true or false).
     */ 
    public boolean isBonus() {
        final KalahRules kRules = (KalahRules) rules;
        return isBonusRules(kRules);
    }

    // heler for the helper
    private boolean isBonusRules(final KalahRules kRules) {
        return kRules.isBonus();
    }

    /**
     * Get the current player.
     *
     * @return An instance of the current player.
     */ 
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) throws PitNotFoundException {
        return rules.getNumStones(pitNum);
    }

    /**
     * Get an arraylist of the playes.
     *
     * @return An arraylist of the current players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // helper methods
    private Player getPlayer(final ArrayList<Player> players, final int playerNum) {
        return players.get(playerNum-1);
    }

    /**
     * Get the number of stones in a player's store.
     *
     * @param player The player to get the store count of.
     * @return The number of stones in the desired store.
     */
    public int getStoreCount(final Player player) throws NoSuchPlayerException {
        if (player.getStore() == null) {
            throw new NoSuchPlayerException();
        }
        int stoneCount;
        final MancalaDataStructure dataStructure = rules.getDataStructure();
        if (player.equals(players.get(0))) {
            stoneCount = getStoreCountDs(1, dataStructure);
        } else {
            stoneCount = getStoreCountDs(2, dataStructure);
        }
        return stoneCount;
    }

    // helper method
    private int getStoreCountDs(final int playerNum, final MancalaDataStructure dataStructure) {
        return dataStructure.getStoreCount(playerNum);
    }

    /**
     * Get the winner of the game
     *
     * @return The winner of the game.
     */
    public Player getWinner() throws GameNotOverException {
        // check if game is over
        if (!isGameOver()) {
            throw new GameNotOverException();
        }
        Player winner;

        rules.movePitsToStores();

        // determine who has the higher store count
        final MancalaDataStructure dataStructure = rules.getDataStructure();
        if (getStoreCountDs(1, dataStructure) > getStoreCountDs(2, dataStructure)) {
           winner = getPlayer(getPlayers(), 1);
        } else if (getStoreCountDs(1, dataStructure) < getStoreCountDs(2, dataStructure)) {
            winner = getPlayer(getPlayers(), 2);
        } else {
            winner = null;
        }

        return winner;
    }

    /**
     * Check if the game is over.
     *
     * @return Boolean of if the game is over (true or false).
     */
    public boolean isGameOver() {
        return rules.isSideEmpty(1) || rules.isSideEmpty(12);
    }

    // helper
    private boolean isCurrent(final Player curPlayer, final Player one) {
        return curPlayer.equals(one);
    }

    // helper
    private boolean isValidMove(final int startPit) {
        boolean validMove;
        final Player curPlayer = getCurrentPlayer();

        if (isCurrent(curPlayer, getPlayer(getPlayers(), 1))) {
            if (startPit < 1 || startPit > MAX_PLAYER_PIT1) {
                validMove = false;
            } else {
                validMove = true;
            }
        } else if (isCurrent(curPlayer, getPlayer(getPlayers(), 2))) {
            if (startPit <= MAX_PLAYER_PIT1 || startPit > MAX_PLAYER_PIT2) {
                validMove = false;
            } else {
                validMove = true;
            }
        } else {
            throw new IllegalStateException();
        }

        return validMove;
    }

    private int getPlayerNum(final Player curPlayer) {
        int playerNum;
        if (isCurrent(curPlayer, getPlayer(getPlayers(), 1))) {
            playerNum = 1;
        } else {
            playerNum = 2;
        }
        return playerNum;
    }

    /**
     * Perform a move and return the number of stones remaining in the player's pits.
     *
     * @param startPit The starting pit for the move.
     * @return The number of stones remaining in the player's pits.
     * @throws InvalidMoveException If the move is invalid.
     * @throws IllegalStateException If an invalid player makes a move.
     */
    public int move(final int startPit) throws InvalidMoveException, IllegalStateException {
        // this throws illegal state exception
        if (!isValidMove(startPit)) {
            throw new InvalidMoveException();
        }
        
        final Player curPlayer = getCurrentPlayer();
        final int playerNum = getPlayerNum(curPlayer);

        // this throws invalidmoveexception
        rules.moveStones(startPit, playerNum);

        int totalStones = 0;
        try {
            if (playerNum == ONE) {
                for (int i=1; i<=MAX_PLAYER_PIT1; i++) {
                    totalStones+=getNumStones(i);
                }
            } else {
                for (int i=MAX_PLAYER_PIT1+1; i<=MAX_PLAYER_PIT2; i++) {
                    totalStones+=getNumStones(i);
                }
            }
        } catch (PitNotFoundException e) {
            // wont happen
            throw new InvalidMoveException();
        }

        // return num stones in players pits
        return totalStones;
    }

    /**
     * Set the game rules for the mancala game.
     *
     * @param theBoard Game rules instance for the game to adhere to.
     */
    public void setBoard(final GameRules theBoard) {
        rules = theBoard;
    }

    /**
     * Set the current player.
     *
     * @param player The player that is now the current player.
     */
    public void setCurrentPlayer(final Player player) {
        currentPlayer = player;
    }

    /**
     * Registers players by adding them to the arraylist and linking players to stores
     *
     * @param player1 Player 1 to register.
     * @param player2 Player 2 to register.
     */
    public void setPlayers(final Player player1, final Player player2) {
        players.clear();
        players.add(player1);
        players.add(player2);
        rules.registerPlayers(player1, player2);

        setCurrentPlayer(player1);
    }

    /**
     * Set the up the stores and pits for the next game.
     */
    public void startNewGame() {
        rules.resetBoard();
    }

    // helper 
    private String getCurName(final Player curPlayer) {
        return curPlayer.getName();
    }

    /**
     * Empty all pits and stores.
     */
    public void resetBoard() {
        final MancalaDataStructure dataStructure = rules.getDataStructure();

        for (int i = 1; i <= 12; i++) {
            removeStones(dataStructure, i);
        }
        dataStructure.emptyStores();
    }

    private void removeStones(final MancalaDataStructure dataStructure, final int pitNum) {
        dataStructure.removeStones(pitNum);
    }

    /**
     * Get text representation of the board's state.
     *
     * @return Text representation of the board's current state.
     */
    @Override
    public String toString() {
        return getBoard()+"\nIt is "+getCurName(getCurrentPlayer())+"'s turn.";
    }
}

