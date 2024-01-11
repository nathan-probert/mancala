package mancala;

public class InvalidMoveException extends Exception {
    private static final long serialVersionUID = 3724878338534103269L;
    
    public InvalidMoveException() {
        super("Invalid move.");
    }
}