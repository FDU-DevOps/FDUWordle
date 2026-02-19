package org.fdu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static org.fdu.GameManager.*;

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
        assertEquals(6, manager.getMaxGuesses(), "Max guesses should be 6");
        assertEquals(6, GameManager.MAX_GUESSES, "MAX_GUESSES constant should be 6");
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
        for(int i = 0; i < 6; i ++)
        {
            manager.doesGuessMatch("WRONG");
        }
        assertFalse(manager.isGameNotOver(),"Game should be over after 6 guesses");
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

    @Test
    public void testIsDebugCommand_validCommand_noGuesses() {
        GameManager manager = new GameManager();
        assertTrue(manager.isDebugCommand("SECRET_WORD"));
    }

    @Test
    public void testIsDebugCommand_validCommand_withGuesses() {
        GameManager manager = new GameManager();
        manager.doesGuessMatch("HELLO"); // increments guessesUsed to 1
        assertFalse(manager.isDebugCommand("SECRET_WORD"));
    }

    @Test
    public void testIsDebugCommand_wrongWord() {
        GameManager manager = new GameManager();
        assertFalse(manager.isDebugCommand("HELLO"));
    }

    @Test
    public void testIsDebugCommand_emptyString() {
        GameManager manager = new GameManager();
        assertFalse(manager.isDebugCommand(""));
    }

    @Test
    public void testSetDebugTargetWord() {
        GameManager manager = new GameManager();
        manager.setDebugTargetWord("CRANE");
        assertEquals("CRANE", manager.getTargetWord());
    }

    @Test
    public void testSetDebugTargetWord_overridesOriginal() {
        GameManager manager = new GameManager();
        String original = manager.getTargetWord();
        manager.setDebugTargetWord("CRANE");
        assertNotEquals(original, manager.getTargetWord());
    }
}