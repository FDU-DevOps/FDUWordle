package org.fdu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordRepoTest {

    @Test
    @DisplayName("Test method for color coded feedback method")
    void generateColoredFeedbackTest() {

         /*
            Cases:
            Letter matches
            Letter doesn't match
            Letter is present in word, but not in correct spot
         */
        String targetWord = "OASIS";
        String playerGuessAllColors = "OCEAN";
        String playerGuessHappyPath = "OASIS";
        String playerGuessIncorrectAllGray = "PLUCK";
        String playerGuessAnagram = "CANOE"; // All Yellow
        String targetWordAnagram = "OCEAN";

        ConsoleUI.FeedbackType[] expectedAllColorsGuess = {
                ConsoleUI.FeedbackType.GREEN,
                ConsoleUI.FeedbackType.RESET,
                ConsoleUI.FeedbackType.RESET,
                ConsoleUI.FeedbackType.YELLOW,
                ConsoleUI.FeedbackType.RESET
        };

        ConsoleUI.FeedbackType[] expectedHappyPath = {
                ConsoleUI.FeedbackType.GREEN, ConsoleUI.FeedbackType.GREEN, ConsoleUI.FeedbackType.GREEN, ConsoleUI.FeedbackType.GREEN, ConsoleUI.FeedbackType.GREEN
        };

        ConsoleUI.FeedbackType[] expectedIncorrectAllGray = {
                ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET,
        };

        ConsoleUI.FeedbackType[] expectedAnagram = {
                ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW,
        };

        // Test: HAPPY PATH
        assertArrayEquals(expectedHappyPath,WordRepo.GenerateColoredFeedback(playerGuessHappyPath, targetWord),"Happy Path not working correctly.");

        // Test: All incorrect letters (all gray)
        assertArrayEquals(expectedIncorrectAllGray,WordRepo.GenerateColoredFeedback(playerGuessIncorrectAllGray, targetWord),"Feedback for all gray letter assignment not working correctly");

        // Test: 2 correct letters: 1 correct position, 1 incorrect position
        assertArrayEquals(expectedAllColorsGuess, WordRepo.GenerateColoredFeedback(playerGuessAllColors, targetWord), "Feedback for 2 correct letters: 1 correct position, 1 incorrect position not working properly");

        // Test: All incorrect letters (all yellow - anagram)
        assertArrayEquals(expectedAnagram, WordRepo.GenerateColoredFeedback(playerGuessAnagram, targetWordAnagram), "Feedback for anagram not working correctly.");


        /*
            Double Letter Cases
            User Guess: 2 letters, Target Word: 1 letter, 1 of the letters is in the Correct Location = GREEN for correct letter, other letter is Gray
            User Guess: 2 letters, Target Word: 1 letter, letters in incorrect location = First instance of letter in guess is yellow, second instance is gray
            User Guess: 2 letters, Target Word: 2 letter, incorrect location for both = 2 YELLOWS
            User Guess: 2 letters, Target Word: 2 letters, 2 correct locations = 2 GREEN
            User Guess: 2 letters, Taget Word: 2 letters: 1 correct location, 1 incorrect location = GREEN and YELLOW
            User Guess: 1 letter, Target Word: 2 letters, Correct Location for letter guessed = GREEN for correct letter
            User Guess: 1 letter, Target Word: 2 letters, user letter guessed is in incorrect Location = YELLOW
            Prioritization rule( Green, then yellow, then gray
         */
    }
}