package org.fdu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordRepoTest {

    @Test
    void pickTargetWordTest() {
        String word = WordRepo.pickTargetWord();
        String wordUpperCase = word.toUpperCase();

        assertEquals(5, word.length(),"Word is not 5 letters");
        assertEquals(wordUpperCase, word, "Word is not in uppercase");

        for(int i=0; i<10; i++){
            System.out.println(WordRepo.pickTargetWord());
        }
    }

    @Test
    public void getWords(){
        List<String> words = WordRepo.getWords();
        assertNotNull(words, "Word list should not be null");
        assertFalse(words.isEmpty(), "Word list should not be empty");

        // Verify list has exactly 15 words
        assertEquals(15, words.size(), "Word list should contain exactly 15 words");

        // Verify all words are uppercase and 5 letters
        for (String word : words) {
            assertNotNull(word, "Word should not be null");
            assertEquals(5, word.length(), "Word should be 5 letters: " + word);
            assertEquals(word, word.toUpperCase(), "Word should be uppercase: " + word);
        }
        // Verify contains expected words
        assertTrue(words.contains("TABLE"), "Should contain TABLE");
        assertTrue(words.contains("APPLE"), "Should contain APPLE");
        assertTrue(words.contains("WATER"), "Should contain WATER");
    }
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
        String playerGuessHappyPath = "OASIS";
        String playerGuessAllColors = "OCEAN";
        String playerGuessIncorrectAllGray = "PLUCK";
        String targetWordAnagram = "OCEAN";
        String playerGuessAnagram = "CANOE"; // All Yellow

        // Double Letter Cases
        String playerOneGreenOneGray = "ALARM";
        String targetOneGreenOneGray = "ABIDE";

        String playerOneYellowOneGray = "ALARM";
        String targetOneYellowOneGray = "BASIC";

        String playerTwoYellows = "STATE";
        String targetTwoYellows = "TITAN";

        String playerTwoGreens = "SPOON";
        String targetTwoGreens = "BLOOM";

        String playerOneGreenOneYellow = "PHOTO";
        String targetOneGreenOneYellow = "SPOOL";

        String playerOneGreen = "CHOKE";
        String targetOneGreen = "SPOOL";

        String playerOneYellow = "BRICK";
        String targetOneYellow = "ABBEY";

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
                ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET
        };

        ConsoleUI.FeedbackType[] expectedAnagram = {
                ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW
        };

        ConsoleUI.FeedbackType[] expectedOneGreenOneGray = {
                ConsoleUI.FeedbackType.GREEN, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET
        };

        ConsoleUI.FeedbackType[] expectedOneYellowOneGray = {
                ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET
        };

        ConsoleUI.FeedbackType[] expectedTwoYellows = {
                ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.RESET
        };

        ConsoleUI.FeedbackType[] expectedTwoGreens = {
                ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.GREEN, ConsoleUI.FeedbackType.GREEN, ConsoleUI.FeedbackType.RESET
        };

        ConsoleUI.FeedbackType[] expectedOneGreenOneYellow = {
                ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.GREEN, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.YELLOW
        };

        ConsoleUI.FeedbackType[] expectedOneGreen = {
                ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.GREEN, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET
        };

        ConsoleUI.FeedbackType[] expectedOneYellow = {
                ConsoleUI.FeedbackType.YELLOW, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET, ConsoleUI.FeedbackType.RESET
        };

        // Test: HAPPY PATH
        assertArrayEquals(expectedHappyPath,WordRepo.GenerateColoredFeedback(playerGuessHappyPath, targetWord),"Happy Path not working correctly.");

        // Test: All incorrect letters (all gray)
        assertArrayEquals(expectedIncorrectAllGray,WordRepo.GenerateColoredFeedback(playerGuessIncorrectAllGray, targetWord),"Feedback for all gray letter assignment not working correctly");

        // Test: 2 correct letters: 1 correct position, 1 incorrect position
        assertArrayEquals(expectedAllColorsGuess, WordRepo.GenerateColoredFeedback(playerGuessAllColors, targetWord), "Feedback for 2 correct letters: 1 correct position, 1 incorrect position not working properly");

        // Test: All incorrect letters (all yellow - anagram)
        assertArrayEquals(expectedAnagram, WordRepo.GenerateColoredFeedback(playerGuessAnagram, targetWordAnagram), "Feedback for anagram not working correctly.");

        // Testing Double Letter Cases
        // User Guess: 2 letters, Target Word: 1 letter, 1 of the letters is in the Correct Location = GREEN for correct letter, other letter is Gray
        // Target: ABIDE, Guess: ALARM
        assertArrayEquals(expectedOneGreenOneGray, WordRepo.GenerateColoredFeedback(playerOneGreenOneGray, targetOneGreenOneGray), "Double letter guessed, 1 in target, and correct location feedback not working properly");

        // User Guess: 2 letters, Target Word: 1 letter, letters in incorrect location = First instance of letter in guess is yellow, second instance is gray
        // Target: BASIC, Guess: ALARM
        assertArrayEquals(expectedOneYellowOneGray, WordRepo.GenerateColoredFeedback(playerOneYellowOneGray,targetOneYellowOneGray), "Double letter guessed, 1 in target, but incorrect location feedback not working properly");

        // User Guess: 2 letters, Target Word: 2 letter, incorrect location for both = 2 YELLOWS
        // Target: TITAN, Guess: STATE
        assertArrayEquals(expectedTwoYellows, WordRepo.GenerateColoredFeedback(playerTwoYellows,targetTwoYellows), "Double letter guessed, 2 in target, but BOTH in incorrect location feedback not working properly");

        // User Guess: 2 letters, Target Word: 2 letters, 2 correct locations = 2 GREEN
        // Target: GLOOM, Guess: SPOON
        assertArrayEquals(expectedTwoGreens, WordRepo.GenerateColoredFeedback(playerTwoGreens,targetTwoGreens), "Double letter guessed, 2 in target and BOTH in correct location feedback not working properly");

        // User Guess: 2 letters, Taget Word: 2 letters: 1 correct location, 1 incorrect location = GREEN and YELLOW
        // Target: SPOOL, Guess: PHOTO
        assertArrayEquals(expectedOneGreenOneYellow, WordRepo.GenerateColoredFeedback(playerOneGreenOneYellow,targetOneGreenOneYellow), "Double letter guessed, 2 in target, 1 in correct location, 1 in incorrect location feedback not working properly");

        // User Guess: 1 letter, Target Word: 2 letters, Correct Location for letter guessed = GREEN for correct letter
        // Target: SPOOL, Guess: CHOKE
        assertArrayEquals(expectedOneGreen, WordRepo.GenerateColoredFeedback(playerOneGreen,targetOneGreen), "Double letters 1 guessed, 2 in target, and correct location feedback not working properly");

        // User Guess: 1 letter, Target Word: 2 letters, user letter guessed is in incorrect Location = YELLOW
        // Target: ABBEY, Guess: BRICK
        assertArrayEquals(expectedOneYellow, WordRepo.GenerateColoredFeedback(playerOneYellow,targetOneYellow), "Double letters, 1 guessed, 2 in target and incorrect location for letter guessed, feedback not working properly");
    }
}