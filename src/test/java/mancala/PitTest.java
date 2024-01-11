/**
 * These test cases are intended to ensure that the correct API has been implemented for the classes under test.
 * They do not provide exhaustive coverage and thorough testing of all possible scenarios.
 * Additional test cases should be added to cover  edge cases and behaviors.
 */
package mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PitTest {
    private Pit pit;

    @BeforeEach
    public void setUp() {

        pit = new Pit();
    }

    @Test
    public void testGetStoneCount() {
        assertEquals(0, pit.getStoneCount());
    }

    @Test
    public void testAddStone() {
        pit.addStone();
        assertEquals(1, pit.getStoneCount());
    }

    @Test
    public void testRemoveStones() {
        pit.addStone();
        pit.addStone();
        int removedStones = pit.removeStones();
        assertEquals(2, removedStones);
        assertEquals(0, pit.getStoneCount());
    }
}
