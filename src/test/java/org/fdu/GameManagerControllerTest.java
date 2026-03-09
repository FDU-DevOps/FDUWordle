package org.fdu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.client.assertj.RestTestClientResponse;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Test
    @DisplayName("Test starting a new game via API")
    void testStartGame(@Autowired RestTestClient restClient) {
        // Hitting api and gathering response
        RestTestClient.ResponseSpec spec = restClient.post().uri("/api/FDUWordle/start-game").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);

       // Check for 200 OK response and if DTO items are contained -- just checking fields
        assertThat(response).hasStatusOk().bodyText().contains("targetWord").contains("hasWon")
                .contains("guessesUsed").contains("isValidGuess").contains("feedbackColors");

        // Load up a response of /start-game
        GameResponse loadedGame = restClient.post()
                .uri("/api/FDUWordle/start-game")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .returnResult()
                .getResponseBody();

        // Assert that all the proper initialized pieces of the DTO match -- checking actual response
        assertThat(loadedGame).isNotNull();
        assertThat(loadedGame.targetWord()).isNotEmpty();
        assertThat(loadedGame.hasWon()).isFalse();
        assertThat(loadedGame.guessesUsed()).isEqualTo(0);
        assertThat(loadedGame.isValidGuess()).isTrue();
        assertThat(loadedGame.feedbackColors()).isEmpty();
    }
}