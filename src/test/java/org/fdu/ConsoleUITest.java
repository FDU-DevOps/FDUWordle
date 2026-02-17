package org.fdu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ConsoleUITest {

    // Bucket to capture console output (instead of printing to screen)
    // ByteArrayOutputStream stores output data in memory as array of bytes
    private ByteArrayOutputStream bucket;

    // Saves the original System.out so we can restore it
    private PrintStream originalOut;

    @BeforeEach
    // Runs before each test to ensure that each test gets fresh and empty bucket (not merged with the previous test)
    void setUp() {
        // Create a new bucket before each test
        bucket = new ByteArrayOutputStream();
        // Save the original console output
        originalOut = System.out;
        // Redirect System.out to our bucket instead of console
        System.setOut(new PrintStream(bucket));
    }

    @AfterEach
    // Runs after each test to clean up and make the system to point back to the console
    void tearDown() {
        // Restore System.out back to normal console
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Display winning guess message")
    void displayGuessResultWinningGuess() {
        // Create feedback array with all GREEN (correct guess)
        ConsoleUI.FeedbackType[] feedback = { // All letters are in the correct position
                ConsoleUI.FeedbackType.GREEN,
                ConsoleUI.FeedbackType.GREEN,
                ConsoleUI.FeedbackType.GREEN,
                ConsoleUI.FeedbackType.GREEN,
                ConsoleUI.FeedbackType.GREEN
        };
        // Call the method (output goes to our bucket, not console)
        ConsoleUI.DisplayGuessResult(feedback, true, 3, "ARENA");

        // Get the captured text from bucket and check it
        String output = bucket.toString();  // Convert bucket contents to String
        assertTrue(output.contains("Congratulations"), "Winning message is not displayed");
    }

    @Test
    @DisplayName("Display game over message when out of guesses")
    void displayGuessResultOutOfGuesses() {
        // Create feedback with mixed colors (incorrect guess)
        ConsoleUI.FeedbackType[] feedback = {
                ConsoleUI.FeedbackType.YELLOW,  // Wrong position
                ConsoleUI.FeedbackType.RESET,   // Incorrect letter
                ConsoleUI.FeedbackType.RESET,   // Incorrect letter
                ConsoleUI.FeedbackType.GREEN,   // Correct position
                ConsoleUI.FeedbackType.RESET    // Incorrect letter
        };

        // Call with isWin=false and guessCount=0 (out of guesses)
        ConsoleUI.DisplayGuessResult(feedback, false, 0, "APPLE");

        // Check that "Game Over" message appears in captured output
        String output = bucket.toString();
        assertTrue(output.contains("Game Over"), "Game over message is not displayed");
    }

    @Test
    @DisplayName("Display remaining guesses during gameplay")
    void displayGuessResultGuessesRemaining() {
        // Create feedback with mixed colors (game still ongoing)
        ConsoleUI.FeedbackType[] feedback = {
                ConsoleUI.FeedbackType.GREEN,   // Correct position
                ConsoleUI.FeedbackType.YELLOW,  // Wrong position
                ConsoleUI.FeedbackType.RESET,   // Incorrect letter
                ConsoleUI.FeedbackType.YELLOW,  // Wrong position
                ConsoleUI.FeedbackType.GREEN    // Correct position
        };

        // Call with isWin=false and guessCount=4 (game continues)
        ConsoleUI.DisplayGuessResult(feedback, false, 4, "OCEAN");

        // Check that remaining guesses count appears
        String output = bucket.toString();
        assertTrue(output.contains("Guesses remaining: 4"), "Remaining guesses are not displayed correctly");
    }

    @Test
    void println() {
    }

    @Test
    void readLine() {
    }
}