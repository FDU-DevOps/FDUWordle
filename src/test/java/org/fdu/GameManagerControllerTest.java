package org.fdu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerControllerTest {

    private GameManagerController controller;
    private GameResponse gameResponse;
    private GameManager gameManager;

    @BeforeEach
    void setUp()
    {
        gameManager = new GameManager();
        controller = new GameManagerController(gameManager);
    }

    @Test
    @DisplayName("Testing getTargetWord sends back a properly instantiated DTO.")
    void getTargetWord() {
        String[] feedback = new String[0];
        gameResponse = new GameResponse("DUMMY","", false,0,true, feedback);
        assertNotNull(gameResponse.targetWord());
        assertEquals("", gameResponse.messageToPlayer());
        assertFalse(gameResponse.hasWon());
        assertEquals(0, gameResponse.guessesUsed());
        assertTrue(gameResponse.isValidGuess());
        assertEquals(feedback, gameResponse.feedbackColors());
    }

    @Test
    @DisplayName("Testing checkUserGuess correctly checks the user guess vs the target word.")
    void checkUserGuess() {
        gameManager.setDebugTargetWord("APPLE");

        // Test correct guess returns hasWon = true
        GameResponse wonResponse = controller.checkUserGuess(new MessageData("APPLE"));
        assertTrue(wonResponse.hasWon());
        assertEquals("APPLE", wonResponse.targetWord());
        assertEquals(GameManager.gameStateMessage(true), wonResponse.messageToPlayer());

        // Test incorrect guess returns hasWon = false
        GameResponse lostResponse = controller.checkUserGuess(new MessageData("TABLE"));
        assertFalse(lostResponse.hasWon());
        assertEquals("APPLE", lostResponse.targetWord());
        assertEquals(GameManager.gameStateMessage(false), lostResponse.messageToPlayer());

    }
}