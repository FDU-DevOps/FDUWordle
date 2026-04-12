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
        GameManager manager = new GameManager();
        gameResponse = manager.startGame("APPLE");

        assertNotNull(gameResponse.targetWord(), "Target word should be NOT be null when the game starts.");
        assertEquals("APPLE", manager.getTargetWord(),"Target should be accessible through getTargetWord()");
    }

    @Test
    void getGuessesUsed() {
        GameManager manager = new GameManager();
        assertEquals(0, manager.getGuessesUsed(), "Initial guesses should be 0");
        manager.doesGuessMatch("Hello");
        assertEquals(1, manager.getGuessesUsed(), "Guesses used should be 1 after one guess");
    }

    @Test
    void doesGuessMatch() {
        GameManager manager = new GameManager();
        gameResponse = manager.startGame("APPLE");
        assertTrue(manager.doesGuessMatch(manager.getTargetWord()), "Should return true for correct guess");
        assertFalse(manager.doesGuessMatch("ZZZZZ"), "Should return false for incorrect guess");
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
        manager.startGame("CRANE");
        manager.setDebugTargetWord("CRANE");

        GameResponse response = manager.submitGuess(new MessageData("CLOSE"));

        assertTrue(response.isValidGuess());
        assertNotNull(response.feedbackColors());
        assertEquals(5, response.feedbackColors().length);
        assertEquals(5, response.guessesRemaining());
        assertEquals("CLOSE", response.previousGuess());
        assertFalse(response.isGameOver());
        assertNotNull(response.targetWord());
    }

    @Test
    @DisplayName("Valid guess, correct guess")
    void testSubmitGuessValidResponseCorrectGuess() {
        GameManager manager = new GameManager();
        manager.setDebugTargetWord("GREEN");

        GameResponse response = manager.submitGuess(new MessageData("GREEN"));

        assertTrue(response.isValidGuess());
        assertTrue(response.hasWon());
        assertTrue(response.isGameOver());
        assertNotNull(response.feedbackColors());
        assertEquals(5, response.guessesRemaining());
        assertEquals("GREEN", response.previousGuess());
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

        GameResponse response = manager.submitGuess(new MessageData("BLOCK"));

        assertFalse(response.hasWon());
        assertTrue(response.isValidGuess());
        assertFalse(response.isGameOver());
        assertNotNull(response.targetWord());
        assertEquals("BLOCK", response.previousGuess());

        for (String color : response.feedbackColors()) {
            assertEquals("GRAY", color);
        }
    }
    @Test
    @DisplayName("Correct Initial State with fields properly initialized")
    void startGameReturnsCorrectInitialState(){
        GameManager manager = new GameManager();
        GameResponse response = manager.startGame("CRANE");

        assertNotNull(response.targetWord());
        assertEquals(6, response.guessesRemaining());
        assertEquals(6, response.maxGuesses());
        assertEquals(5, response.wordLength());
        assertFalse(response.hasWon());
        assertFalse(response.isGameOver());
        assertTrue(response.isValidGuess());
        assertNull(response.previousGuess());
        assertNull(response.feedbackColors());
    }

    @Test
    @DisplayName("Game over state with target word revealed after 6 wrong guesses")
    void submitGuessSixWrongGuessesReturnsCorrectState(){
        GameManager manager = new GameManager();
        manager.startGame("CRANE");

        String[] wrongGuesses=
                {"PLANE", "STARE","BLARE", "HAIRY","FAIRY", "SNARE"};

        GameResponse last = null;
        for(String guess: wrongGuesses){
            last = manager.submitGuess(new MessageData(guess));
        }

        assertNotNull(last);
        assertTrue(last.isGameOver());
        assertFalse(last.hasWon());
        assertEquals("CRANE", last.targetWord());
        assertEquals(0, last.guessesRemaining());
    }

    @Test
    @DisplayName("startGame after a Win resets all game state fields correctly")
    void startGameAfterWinResetsCorrectly(){
        GameManager manager = new GameManager();
        manager.startGame("CRANE");

        manager.submitGuess(new MessageData("CRANE"));
        GameResponse gameResponseALT = manager.startGame("STARE");

        assertEquals(6, gameResponseALT.guessesRemaining());
        assertFalse(gameResponseALT.hasWon());
        assertFalse(gameResponseALT.isGameOver());
        assertNull(gameResponseALT.previousGuess());

    }
}