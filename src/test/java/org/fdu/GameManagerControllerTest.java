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
    @Autowired
    private RestTestClient restClient;

    @BeforeEach
    void setUp() {
        //Start a fresh game before each test
        restClient.post().uri("/api/FDUWordle/start-game").exchange();
    }

    @Test
    @DisplayName("Testing getTargetWord sends back a properly instantiated DTO.")
    void getTargetWord() {
        gameResponse = new GameResponse(null, 6,6,5,false, false, true, null, null);

        assertNull(gameResponse.targetWord());
        assertFalse(gameResponse.hasWon());
        assertEquals(6, gameResponse.guessesRemaining());
        assertTrue(gameResponse.isValidGuess());
        assertNull(gameResponse.feedbackColors());
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
        GameManager gameManager2 = new GameManager();
        gameManager2.startGame("APPLE");
        MessageData incorrectGuess = new MessageData("OCEAN");
        gameResponse = gameManager2.submitGuess(incorrectGuess);
        assertFalse(gameResponse.hasWon());
        assertNull(gameResponse.targetWord());
    }

    @Test
    @DisplayName("Test starting a new game via API")
    void testStartGame() {
        // Hitting api and gathering response
        RestTestClient.ResponseSpec spec = restClient.post().uri("/api/FDUWordle/start-game").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);

        // Check for 200 OK response and if DTO items are contained -- just checking fields
        assertThat(response).hasStatusOk().bodyText()
                .contains("targetWord")
                .contains("hasWon")
                .contains("guessesRemaining")
                .contains("maxGuesses")
                .contains("wordLength")
                .contains("isGameOver")
                .contains("isValidGuess")
                .contains("previousGuess")
                .contains("feedbackColors");

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
        assertThat(loadedGame.targetWord()).isNull();
        assertThat(loadedGame.guessesRemaining()).isEqualTo(6);
        assertThat(loadedGame.maxGuesses()).isEqualTo(6);
        assertThat(loadedGame.wordLength()).isEqualTo(5);
        assertThat(loadedGame.hasWon()).isFalse();
        assertThat(loadedGame.isGameOver()).isFalse();
        assertThat(loadedGame.isValidGuess()).isTrue();
        assertThat(loadedGame.previousGuess()).isNull();
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
        int initialGuessesRemaining = newGame.guessesRemaining();
        System.out.println(initialGuessesRemaining);

        // Assume the User has guessed an invalid word
        MessageData userGuessInvalidWord = new MessageData("DEVILS"); // 6-letter word is invalid

        GameResponse responseInvalidWord = restClient.post()
                .uri("/api/FDUWordle/submit-guess")
                .contentType(MediaType.APPLICATION_JSON).body(userGuessInvalidWord)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseInvalidWord).isNotNull();
        assertThat(responseInvalidWord.targetWord()).isNull();
        assertThat(responseInvalidWord.hasWon()).isFalse();
        assertThat(responseInvalidWord.isValidGuess()).isFalse();
        assertThat(responseInvalidWord.isGameOver()).isFalse();
        assertThat(responseInvalidWord.previousGuess()).isNull();
        assertThat(responseInvalidWord.feedbackColors()).isNull();
        assertThat(responseInvalidWord.guessesRemaining()).isEqualTo(initialGuessesRemaining);
        System.out.println("Invalid Word Guesses Used: " + responseInvalidWord.guessesRemaining());
        System.out.println("Initial Guesses Used: " + initialGuessesRemaining);
        assertThat(responseInvalidWord.guessesRemaining()).isEqualTo(initialGuessesRemaining); // guesses used should not increase
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

        GameManager gameManager = new GameManager();
        gameManager.startGame("CRANE");
        String actualTarget = gameManager.getTargetWord();

        //Restart with known word via API
        restClient.post().uri("/api/FDUWordle/start-game").exchange();

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
        assertThat(responseCorrectWord.targetWord()).isNotNull();
        assertThat(responseCorrectWord.hasWon()).isTrue();
        assertThat(responseCorrectWord.isGameOver()).isTrue();
        assertThat(responseCorrectWord.isValidGuess()).isTrue();
        assertThat(responseCorrectWord.previousGuess()).isNotNull();
        assertThat(responseCorrectWord.guessesRemaining()).isLessThan(6);
        assertThat(responseCorrectWord.feedbackColors()).isNotNull();
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
        int initialGuessesRemaining = newGame.guessesRemaining();

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
        assertThat(responseIncorrectWord.targetWord()).isNull();
        assertThat(responseIncorrectWord.hasWon()).isFalse();
        assertThat(responseIncorrectWord.isGameOver()).isFalse();
        assertThat(responseIncorrectWord.isValidGuess()).isTrue();
        assertThat(responseIncorrectWord.previousGuess()).isEqualTo("AARON");
        assertThat(responseIncorrectWord.guessesRemaining()).isEqualTo(initialGuessesRemaining-1);
        assertThat(responseIncorrectWord.feedbackColors()).isNotNull();
    }
}