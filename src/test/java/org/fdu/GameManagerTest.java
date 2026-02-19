package org.fdu;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
    public void showIntro() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        GameManager manager = new GameManager();
        GameManager.showIntro(manager);

        System.setOut(System.out);

        assertFalse(outContent.toString().isEmpty());
    }

    @Test
    void getNormalizedGuess() {
        GameManager manager = new GameManager();

        assertEquals("", manager.getNormalizedGuess(null));
        assertEquals("", manager.getNormalizedGuess(""));
        assertEquals("HAIRY", manager.getNormalizedGuess("hairy"));
        assertEquals("HAIRY", manager.getNormalizedGuess("  HAIRY  "));
        assertEquals("HAIRY", manager.getNormalizedGuess("  hAiRy  "));
        assertEquals("TABLE", manager.getNormalizedGuess("TABLE"));
    }
}