// /**
//  * These test cases are intended to ensure that the correct API has been implemented for the classes under test.
//  * They do not provide exhaustive coverage and thorough testing of all possible scenarios.
//  * Additional test cases should be added to cover  edge cases and behaviors.
//  */


// package mancala;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertSame;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// // import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;



// public class MancalaGameTest {
//     private MancalaGame game;
//     private Player playerOne;
//     private Player playerTwo;

//     @BeforeEach    
//     public void setUp() {
//         game = new MancalaGame();
//         playerOne = new Player();
//         playerTwo = new Player();
//         game.setPlayers(playerOne,playerTwo);
//         game.startNewGame();
//     }

//     @Test
//     public void testSetPlayers() {
//         game.setPlayers(playerOne, playerTwo);
//         //assumption that Player One is current Player at start of game
//         assertEquals(playerOne, game.getCurrentPlayer());
//         assertSame(playerOne, game.getCurrentPlayer());
//     }

//     @Test
//     public void testSetCurrentPlayer() {
//         game.setPlayers(playerOne, playerTwo);
//         game.setCurrentPlayer(playerTwo);

//         assertEquals(playerTwo, game.getCurrentPlayer());
//         assertSame(playerTwo, game.getCurrentPlayer());
//     }

//     @Test
//     public void testSetAndGetBoard() {
//         KalahRules newBoard = new KalahRules();
//         game.setBoard(newBoard);

//         assertEquals(newBoard, game.getBoard());
//         assertSame(newBoard, game.getBoard());
//     }

//     @Test
//     public void testGetNumStones() throws PitNotFoundException{
//         game.setPlayers(playerOne, playerTwo);

//         int numStones = game.getNumStones(1);

//         assertEquals(4, numStones); // Assuming initial setup
//     }

//     @Test
//     public void testMove() throws InvalidMoveException{
//         game.setPlayers(playerOne, playerTwo);

//         int stonesInPits = game.move(1);

//         assertTrue(stonesInPits >= 0);
//     }

//     @Test
//     public void testGetStoreCount() throws NoSuchPlayerException{
//         game.setPlayers(playerOne, playerTwo);

//         int storeCount = game.getStoreCount(playerOne);

//         assertEquals(0, storeCount); // Assuming initial setup
//     }

//     @Test
//     public void testGetWinner() {
//         game.setPlayers(playerOne, playerTwo);

//         assertThrows(GameNotOverException.class, () -> game.getWinner());
//     }

//     @Test
//     public void testIsGameOver() {
//         game.setPlayers(playerOne, playerTwo);

//         assertFalse(game.isGameOver());
//     }

//     @Test
//     public void invalidMove() {
//         game.setPlayers(playerOne, playerTwo);
//         assertThrows(InvalidMoveException.class, () -> game.move(0));
//     }

//     @Test
//     public void testStartNewGame() throws PitNotFoundException, InvalidMoveException {
//         game.setPlayers(playerOne, playerTwo);
//         game.move(1);
//         int stonesBeforeNewGame = game.getBoard().getNumStones(1);

//         game.startNewGame();
//         int stonesAfterNewGame = game.getBoard().getNumStones(1);

//         assertEquals(playerOne, game.getCurrentPlayer());
//         assertSame(playerOne, game.getCurrentPlayer());
//         assertFalse(game.isGameOver());

//         assertEquals(0, stonesBeforeNewGame);
//         assertEquals(4, stonesAfterNewGame); // Assuming initial setup
//     }

//     @Test
//     public void testToString() {
//         game.setPlayers(playerOne, playerTwo);

//         assertNotNull(game.toString());
//     }
// }
