package org.fdu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureRestTestClient

class GameManagerControllerTest {

    private GameManager gameManager;
    private GameResponse gameResponse;

    @BeforeEach
    void setUp()
    {
        gameManager = new GameManager();
        //controller = new GameManagerController(gameManager);
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
        gameManager.startGame("APPLE");

        // Test correct guess matches target word -- happy path
        MessageData correctGuess = new MessageData("APPLE");
        gameResponse = gameManager.submitGuess(correctGuess);
        assertEquals(correctGuess.playerGuess(),gameResponse.targetWord());
        assertTrue(gameResponse.hasWon());

        // Test incorrect guess returns hasWon = false
        MessageData incorrectGuess = new MessageData("OCEAN");
        gameResponse = gameManager.submitGuess(incorrectGuess);
        assertNotEquals(gameManager.getTargetWord(), incorrectGuess.playerGuess());
        assertFalse(gameResponse.hasWon());
    }
}