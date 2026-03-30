package org.fdu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    private GameResponse gameResponse;

    @BeforeEach
    void setUp() {
        WordRepo.loadDictionary("valid_dictionary.csv");
    }

    @Test
    void getTargetWord() {
        String[] feedback = null;
        gameResponse = new GameResponse(null, 6,6,5,false, false,true, false, null, null);

        assertNull(gameResponse.targetWord());
        assertFalse(gameResponse.hasWon());
        assertEquals(6, gameResponse.guessesRemaining());
        assertTrue(gameResponse.isValidGuess());
        assertNull(gameResponse.feedbackColors());
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
        GameManager manager2 = new GameManager();
        gameResponse = manager.startGame("APPLE");
        // alternative game
        GameResponse gameResponseALT = manager2.startGame("DEVIL");
        assertEquals(gameResponse.maxGuesses(), gameResponseALT.maxGuesses(), "Max Guesses should be the same across different instances.");
        assertTrue(gameResponse.maxGuesses() > 0, "Max Guesses should always be above 0.");
        assertEquals(6,gameResponse.maxGuesses(),"Max Guesses should should be 6");
    }

    @Test
    void doesGuessMatch() {
        GameManager manager = new GameManager();
        gameResponse = manager.startGame("APPLE");
        assertTrue(manager.doesGuessMatch(manager.getTargetWord()), "Should return true for correct guess");
        assertFalse(manager.doesGuessMatch("ZZZZZ"), "Should return false for incorrect guess");
    }


    @Test
    void isGameNotOver() {
        GameManager manager = new GameManager();
        gameResponse = manager.startGame("APPLE");

        assertFalse(gameResponse.hasWon(),"Game should not be over at the start of the game loop");
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
        gameResponse = manager.startGame("APPLE");
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
        System.out.println("Original Target word: " + original);
        manager.setDebugTargetWord("CRANE");
        assertNotEquals(original, manager.getTargetWord());
    }

    @Test
    public void testResetGuessesUsed(){
        GameManager manager = new GameManager();
        manager.setDebugTargetWord("CRANE");
        manager.doesGuessMatch("HELLO");
        assertTrue(manager.getGuessesUsed() > 0, "GuessesUsed should increase after a guess");
        manager.resetGuessesUsed();
        assertEquals(0, manager.getGuessesUsed(), "GuessesUsed should be 0 after reset");
    }

    @Test
    @DisplayName("Check setter and getter for hasWon state")
    void testGetAndSetWon() {
        GameManager manager = new GameManager();

        manager.setWon(true);
        assertTrue(manager.getWon(), "hasWon should be true after setWon(true)");

        manager.setWon(false);
        assertFalse(manager.getWon(), "hasWon should be false after setWon(false)");
    }

    @Test
    @DisplayName("Invalid guess check too short")
    void testSubmitGuessInvalidGuessTooShort() {
        GameManager manager = new GameManager();
        manager.setDebugTargetWord("CRANE");

        //Too short
        GameResponse response = manager.submitGuess(new MessageData("CAT"));

        assertFalse(response.hasWon());
        assertEquals(6, response.guessesRemaining() );
        assertFalse(response.isValidGuess());
        assertNull(response.feedbackColors());
        assertNull(response.previousGuess());
    }

    @Test
    @DisplayName("Invalid guess check too long")
    void testSubmitGuessValidGuessTooLong(){
        GameManager manager = new GameManager();
        manager.setDebugTargetWord("CRANE");

        GameResponse response = manager.submitGuess(new MessageData("Enormous"));

        assertFalse(response.hasWon());
        assertEquals(6, response.guessesRemaining());
        assertFalse(response.isValidGuess());
        assertNull(response.feedbackColors());
        assertNull(response.previousGuess());
    }

    @Test
    @DisplayName("Valid guess with valid response")
    void testSubmitGuessValidResponse() {
        GameManager manager = new GameManager();
        manager.setDebugTargetWord("CRANE");

        MessageData guess = new MessageData("CLOSE");
        GameResponse response = manager.submitGuess(guess);

        assertTrue(response.isValidGuess());
        assertNotNull(response.feedbackColors());
        assertEquals(5, response.feedbackColors().length);
        assertEquals(5, response.guessesRemaining());
        assertEquals(guess, response.previousGuess());
        assertFalse(response.isGameOver());
        assertNotNull(response.targetWord());
    }

    @Test
    @DisplayName("Valid guess, correct guess")
    void testSubmitGuessValidResponseCorrectGuess() {
        GameManager manager = new GameManager();
        manager.setDebugTargetWord("GREEN");
        MessageData guess = new MessageData("GREEN");
        GameResponse response = manager.submitGuess(guess);
        assertTrue(response.isValidGuess());
        assertTrue(response.hasWon());
        assertTrue(response.isGameOver());
        assertNotNull(response.feedbackColors());
        assertEquals(5, response.guessesRemaining());
        assertEquals(guess, response.previousGuess());
        assertEquals("GREEN", response.targetWord());

        for (String color : response.feedbackColors()) {
            assertEquals("GREEN", color);
        }
    }

    @Test
    @DisplayName("Valid guess, wrong guess")
    void testSubmitGuessValidResponseWrongGuess() {
        GameManager manager = new GameManager();
        manager.setDebugTargetWord("GRAAY");
        MessageData guess = new MessageData("BLOCK");
        GameResponse response = manager.submitGuess(guess);

        assertFalse(response.hasWon());
        assertTrue(response.isValidGuess());
        assertFalse(response.isGameOver());
        assertNotNull(response.targetWord());
        assertEquals(guess, response.previousGuess());

        for (String color : response.feedbackColors()) {
            assertEquals("GRAY", color);
        }
    }
}