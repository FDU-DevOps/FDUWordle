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
    public void isInvalidGuess(){
        // Invalid cases (should return true)
        assertTrue(WordRepo.isInvalidGuess(null));
        assertTrue(WordRepo.isInvalidGuess(""));
        assertTrue(WordRepo.isInvalidGuess("CAT"));
        assertTrue(WordRepo.isInvalidGuess("TIREDNESS"));
        assertTrue(WordRepo.isInvalidGuess("WH90A"));
        assertTrue(WordRepo.isInvalidGuess("HE LO"));
        assertTrue(WordRepo.isInvalidGuess("HE@LO"));

        // Lowercase letters (below 'A' in ASCII won't apply, but lowercase is outside A-Z range)
        assertTrue(WordRepo.isInvalidGuess("hairy"));

        // Valid cases (should return false)
        assertFalse(WordRepo.isInvalidGuess("HAIRY"));
        assertFalse(WordRepo.isInvalidGuess("TABLE"));
        assertFalse(WordRepo.isInvalidGuess("ABCDE"));
    }

    @Test
    void normalize() {

        assertEquals("HELLO", WordRepo.normalize("hello"),"Should convert string from lowercase to uppercase");
        assertEquals("HELLO", WordRepo.normalize("    hello    "), "Should trim leading and trailing whitespace");
        assertEquals("HELLO", WordRepo.normalize("HeLLo"), "Should convert mixed case to uppercase");
        assertEquals("DEVIL", WordRepo.normalize("DEVIL"),"Should handle already normalized input");
        assertEquals("WORLD", WordRepo.normalize("   WoRlD   "), "Should trim and convert to uppercase");
        assertEquals("", WordRepo.normalize(null), "Should return empty string for null input");
    }

    @Test
    @DisplayName("Test method for color coded feedback method")
    void generateColoredFeedbackTest() {

        //These test cases examine the IllegalArgumentException to be thrown within the generateColoredFeedback method
        assertThrows(IllegalArgumentException.class, () ->
                WordRepo.GenerateColoredFeedback("CAT", "TABLE"));

        assertThrows(IllegalArgumentException.class, () ->
                WordRepo.GenerateColoredFeedback("TIREDNESS", "TABLE"));

        assertThrows(IllegalArgumentException.class, () ->
                WordRepo.GenerateColoredFeedback("WH90A", "TABLE"));

        assertThrows(IllegalArgumentException.class, () ->
                WordRepo.GenerateColoredFeedback("HE@LO", "TABLE"));

        assertThrows(IllegalArgumentException.class, () ->
                WordRepo.GenerateColoredFeedback("hairy", "TABLE"));

        assertThrows(IllegalArgumentException.class, () ->
                WordRepo.GenerateColoredFeedback(null, "TABLE"));

        assertThrows(IllegalArgumentException.class, () ->
                WordRepo.GenerateColoredFeedback("", "TABLE"));

         /*
            Cases:
            Letter matches
            Letter doesn't match
            Letter is present in word, but not in correct spot
         */
        String targetWord = "OASIS";
        String playerGuessHappyPath = "OASIS";

        WordRepo.FeedbackType[] expectedHappyPath = {
                WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GREEN
        };

        // Test: HAPPY PATH
        assertArrayEquals(expectedHappyPath,WordRepo.GenerateColoredFeedback(playerGuessHappyPath, targetWord),"Happy Path not working correctly.");

        String playerGuessAllColors = "OCEAN";
        String playerGuessIncorrectAllGray = "PLUCK";

        String targetWordAnagram = "OCEAN";
        String playerGuessAnagram = "CANOE"; // All Yellow

        WordRepo.FeedbackType[] expectedAnagram = {
                WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.YELLOW
        };

        // Test: All incorrect letters (all yellow - anagram)
        assertArrayEquals(expectedAnagram, WordRepo.GenerateColoredFeedback(playerGuessAnagram, targetWordAnagram), "Feedback for anagram not working correctly.");

        WordRepo.FeedbackType[] expectedAllColorsGuess = {
                WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.GRAY
        };

        // Test: 2 correct letters: 1 correct position, 1 incorrect position
        assertArrayEquals(expectedAllColorsGuess, WordRepo.GenerateColoredFeedback(playerGuessAllColors, targetWord), "Feedback for 2 correct letters: 1 correct position, 1 incorrect position not working properly");

        WordRepo.FeedbackType[] expectedIncorrectAllGray = {
                WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY
        };

        // Test: All incorrect letters (all gray)
        assertArrayEquals(expectedIncorrectAllGray,WordRepo.GenerateColoredFeedback(playerGuessIncorrectAllGray, targetWord),"Feedback for all gray letter assignment not working correctly");

        // Double Letter Cases
        String playerOneGreenOneGray = "ALARM";
        String targetOneGreenOneGray = "ABIDE";

        WordRepo.FeedbackType[] expectedOneGreenOneGray = {
                WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY
        };

        // User Guess: 2 letters, Target Word: 1 letter, 1 of the letters is in the Correct Location = GREEN for correct letter, other letter is Gray
        // Target: ABIDE, Guess: ALARM
        assertArrayEquals(expectedOneGreenOneGray, WordRepo.GenerateColoredFeedback(playerOneGreenOneGray, targetOneGreenOneGray), "Double letter guessed, 1 in target, and correct location feedback not working properly");

        String playerOneYellowOneGray = "ALARM";
        String targetOneYellowOneGray = "BASIC";

        WordRepo.FeedbackType[] expectedOneYellowOneGray = {
                WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY
        };

        // User Guess: 2 letters, Target Word: 1 letter, letters in incorrect location = First instance of letter in guess is yellow, second instance is gray
        // Target: BASIC, Guess: ALARM
        assertArrayEquals(expectedOneYellowOneGray, WordRepo.GenerateColoredFeedback(playerOneYellowOneGray,targetOneYellowOneGray), "Double letter guessed, 1 in target, but incorrect location feedback not working properly");

        String playerTwoYellows = "STATE";
        String targetTwoYellows = "TITAN";

        WordRepo.FeedbackType[] expectedTwoYellows = {
                WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.GRAY
        };

        // User Guess: 2 letters, Target Word: 2 letter, incorrect location for both = 2 YELLOWS
        // Target: TITAN, Guess: STATE
        assertArrayEquals(expectedTwoYellows, WordRepo.GenerateColoredFeedback(playerTwoYellows,targetTwoYellows), "Double letter guessed, 2 in target, but BOTH in incorrect location feedback not working properly");

        String playerTwoGreens = "SPOON";
        String targetTwoGreens = "BLOOM";

        WordRepo.FeedbackType[] expectedTwoGreens = {
                WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GRAY
        };

        // User Guess: 2 letters, Target Word: 2 letters, 2 correct locations = 2 GREEN
        // Target: GLOOM, Guess: SPOON
        assertArrayEquals(expectedTwoGreens, WordRepo.GenerateColoredFeedback(playerTwoGreens,targetTwoGreens), "Double letter guessed, 2 in target and BOTH in correct location feedback not working properly");

        String playerOneGreenOneYellow = "PHOTO";
        String targetOneGreenOneYellow = "SPOOL";

        WordRepo.FeedbackType[] expectedOneGreenOneYellow = {
                WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.YELLOW
        };

        // User Guess: 2 letters, Taget Word: 2 letters: 1 correct location, 1 incorrect location = GREEN and YELLOW
        // Target: SPOOL, Guess: PHOTO
        assertArrayEquals(expectedOneGreenOneYellow, WordRepo.GenerateColoredFeedback(playerOneGreenOneYellow,targetOneGreenOneYellow), "Double letter guessed, 2 in target, 1 in correct location, 1 in incorrect location feedback not working properly");

        String playerOneGreen = "CHOKE";
        String targetOneGreen = "SPOOL";

        WordRepo.FeedbackType[] expectedOneGreen = {
                WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GREEN, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY
        };

        // User Guess: 1 letter, Target Word: 2 letters, Correct Location for letter guessed = GREEN for correct letter
        // Target: SPOOL, Guess: CHOKE
        assertArrayEquals(expectedOneGreen, WordRepo.GenerateColoredFeedback(playerOneGreen,targetOneGreen), "Double letters 1 guessed, 2 in target, and correct location feedback not working properly");

        String playerOneYellow = "BRICK";
        String targetOneYellow = "ABBEY";

        WordRepo.FeedbackType[] expectedOneYellow = {
                WordRepo.FeedbackType.YELLOW, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY, WordRepo.FeedbackType.GRAY
        };

        // User Guess: 1 letter, Target Word: 2 letters, user letter guessed is in incorrect Location = YELLOW
        // Target: ABBEY, Guess: BRICK
        assertArrayEquals(expectedOneYellow, WordRepo.GenerateColoredFeedback(playerOneYellow,targetOneYellow), "Double letters, 1 guessed, 2 in target and incorrect location for letter guessed, feedback not working properly");
    }
}