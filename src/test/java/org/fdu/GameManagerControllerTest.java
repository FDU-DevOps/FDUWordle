package org.fdu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerControllerTest {

    private GameManagerController controller;
    private GameManager gameManager;

    @BeforeEach
    void setUp()
    {
        gameManager = new GameManager();
        controller = new GameManagerController(gameManager);
    }

    @Test
    void getTargetWord() {
        String result = controller.getTargetWord();

        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(result, result.toUpperCase());
        assertTrue(WordRepo.getWords().contains(result));
    }

    @Test
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