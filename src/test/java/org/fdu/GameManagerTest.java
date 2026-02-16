package org.fdu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void getTargetWord() {
        GameManager manager = new GameManager();
        String targetWord = manager.getTargetWord();
        assertNotNull(targetWord, "Target word should not be null");
    }

    @Test
    void getGuessesUsed() {
        GameManager manager = new GameManager();
        assertEquals(0, manager.getGuessesUsed(), "Initial guesses should be 0");
        manager.doesGuessMatch("Hello");
        assertEquals(1, manager.getGuessesUsed(), "Guesses used should be 1 after one guess");
    }

    @Test
    void getMaxGuesses() {
        GameManager manager = new GameManager();
        assertEquals(1, manager.getMaxGuesses(), "Max guesses should be 1");
        assertEquals(1, GameManager.MAX_GUESSES, "MAX_GUESSES constant should be 1");
    }

    @Test
    void doesGuessMatch() {
        GameManager manager = new GameManager();
        String targetWord = manager.getTargetWord();
        assertTrue(manager.doesGuessMatch(targetWord), "Should return true for correct guess");
        assertFalse(manager.doesGuessMatch("ZZZZZ"), "Should return false for incorrect guess");
    }
    @Test
    void isGameNotOver() {
        GameManager manager = new GameManager();
        assertTrue(manager.isGameNotOver(),"Game should not be over at the start of the game loop");
        manager.doesGuessMatch("WRONG");
        assertFalse(manager.isGameNotOver(),"Game should be over after 1 guess");
    }

    @Test
    void normalize() {
        GameManager manager = new GameManager();
        assertEquals("HELLO", manager.normalize("hello"),"Should convert string from lowercase to uppercase");
        assertEquals("HELLO",manager.normalize("    hello    "), "Should trim leading and trailing whitespace");
        assertEquals("HELLO", manager.normalize("HeLLo"), "Should convert mixed case to uppercase");
        assertEquals("DEVIL", manager.normalize("DEVIL"),"Should handle already normalized input");
        assertEquals("WORLD", manager.normalize("   WoRlD   "), "Should trim and convert to uppercase");
        assertEquals("", manager.normalize(null), "Should return empty string for null input");
    }
}