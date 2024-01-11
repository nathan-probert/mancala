package mancala;

public class GameNotOverException extends Exception {
    private static final long serialVersionUID = -6386606498972578863L;

    public GameNotOverException() {
        super("The game is not over.");
    }
}