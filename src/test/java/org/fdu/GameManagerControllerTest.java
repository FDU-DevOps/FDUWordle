package org.fdu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.client.assertj.RestTestClientResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient

class GameManagerControllerTest {

    //private GameManager gameManager;
    private GameResponse gameResponse;
    @Autowired private RestTestClient restClient;

    @BeforeEach
    void setUp() {
        //gameManager = new GameManager();
    }

    @Test
    @DisplayName("Testing getTargetWord sends back a properly instantiated DTO.")
    void getTargetWord() {
        String[] feedback = new String[0];
        gameResponse = new GameResponse("DUMMY", "", false, 0, true, feedback);
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
        GameManager gameManager = new GameManager();
        gameManager.startGame("APPLE");

        // Test correct guess matches target word -- happy path
        MessageData correctGuess = new MessageData("APPLE");
        gameResponse = gameManager.submitGuess(correctGuess);
        assertEquals(correctGuess.playerGuess(), gameResponse.targetWord());
        assertTrue(gameResponse.hasWon());

        // Test incorrect guess returns hasWon = false
        MessageData incorrectGuess = new MessageData("OCEAN");
        gameResponse = gameManager.submitGuess(incorrectGuess);
        assertNotEquals(gameManager.getTargetWord(), incorrectGuess.playerGuess());
        assertFalse(gameResponse.hasWon());
    }

    @Test
    @DisplayName("Test starting a new game via API")
    void testStartGame() {
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

    @Test
    @DisplayName("Testing that submit game works for invalid guesses")
    void testSubmitGameInvalid()
    {
        // Start a game of Wordle prior to checking for guess submission
        GameResponse newGame = restClient.post()
                .uri("/api/FDUWordle/start-game")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(newGame).isNotNull();
        String actualTarget = newGame.targetWord();
        int initialGuessesUsed = newGame.guessesUsed();
        System.out.println(initialGuessesUsed);

        // Assume the User has guessed an invalid word
        MessageData userGuessInvalidWord = new MessageData("DEVILS"); // 6 letter word is invalid

        GameResponse responseInvalidWord = restClient.post()
                .uri("/api/FDUWordle/submit-guess")
                .contentType(MediaType.APPLICATION_JSON).body(userGuessInvalidWord)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseInvalidWord).isNotNull();
        assertThat(responseInvalidWord.targetWord()).isEqualTo(actualTarget);
        assertThat(responseInvalidWord.hasWon()).isFalse();
        assertThat(responseInvalidWord.isValidGuess()).isFalse();
        System.out.println("Invalid Word Guesses Used: " + responseInvalidWord.guessesUsed());
        System.out.println("Initial Guesses Used: " + initialGuessesUsed);
        assertThat(responseInvalidWord.guessesUsed()).isEqualTo(initialGuessesUsed); // guesses used should not increase
        assertThat(responseInvalidWord.feedbackColors()).isNull();
    }

    @Test
    @DisplayName("Test submit guess works for correct words")
    void testSubmitGuessCorrect()
    {
        // Start a game of Wordle prior to checking for guess submission
        GameResponse newGame = restClient.post()
                .uri("/api/FDUWordle/start-game")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(newGame).isNotNull();
        String actualTarget = newGame.targetWord();

        // Assume the User has guessed the word correctly
        MessageData userGuessCorrectWord = new MessageData(actualTarget);

        GameResponse responseCorrectWord = restClient.post()
                .uri("/api/FDUWordle/submit-guess")
                .contentType(MediaType.APPLICATION_JSON).body(userGuessCorrectWord)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseCorrectWord).isNotNull();
        assertThat(responseCorrectWord.targetWord()).isEqualTo(actualTarget);
        assertThat(responseCorrectWord.hasWon()).isTrue();
        assertThat(responseCorrectWord.isValidGuess()).isTrue();
        assertThat(responseCorrectWord.guessesUsed()).isGreaterThanOrEqualTo(1);
        assertThat(responseCorrectWord.feedbackColors()).isNotEmpty();
    }

    @Test
    @DisplayName("Testing that submit game works for incorrect user guesses")
    void testSubmitGameIncorrect()
    {
        // Start a game of Wordle prior to checking for guess submission
        GameResponse newGame = restClient.post()
                .uri("/api/FDUWordle/start-game")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(newGame).isNotNull();
        String actualTarget = newGame.targetWord();

        // Assume the User has guessed the word incorrectly
        // Testing with a word that will never be the target word
        MessageData userGuessIncorrectWord = new MessageData("AARON");

        GameResponse responseIncorrectWord = restClient.post()
                .uri("/api/FDUWordle/submit-guess")
                .contentType(MediaType.APPLICATION_JSON).body(userGuessIncorrectWord)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseIncorrectWord).isNotNull();
        assertThat(responseIncorrectWord.targetWord()).isEqualTo(actualTarget);
        assertThat(responseIncorrectWord.hasWon()).isFalse();
        assertThat(responseIncorrectWord.isValidGuess()).isTrue();
        assertThat(responseIncorrectWord.guessesUsed()).isGreaterThanOrEqualTo(1);
        assertThat(responseIncorrectWord.feedbackColors()).isNotEmpty();
    }
}