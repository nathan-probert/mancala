/**
 * These test cases are intended to ensure that the correct API has been implemented for the classes under test.
 * They do not provide exhaustive coverage and thorough testing of all possible scenarios.
 * Additional test cases should be added to cover  edge cases and behaviors.
 */
package mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


public class PlayerTest {
    private Player player;
    private Store store;

    @BeforeEach
    public void setUp() {
        player = new Player();
        player.setName("Player One");
        store = new Store();
        player.setStore(store);
    }

    @Test
    public void testGetName() {
        assertEquals("Player One", player.getName());
    }

    @Test
    public void testSetName() {
        player.setName("New Name");
        assertEquals("New Name", player.getName());
    }

    @Test
    public void testGetStore() {
        assertSame(store, player.getStore());
    }

    @Test
    public void testSetStore() {
        Store newStore = new Store();
        player.setStore(newStore);
        assertSame(newStore, player.getStore());
    }
}
