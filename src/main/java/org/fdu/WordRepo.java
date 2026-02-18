package org.fdu;

import java.util.HashMap;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This is a static utility
 * Maintains a dictionary of allowable words and validates player guesses against the rules <br>
 * <p>
 * Expose method to pick a random word from the game dictionary
 * Expose method to return if a guess is valid, ie meets the rules (e.g. 5 letters, a-z, A-Z)
 *
 * @author tbd
 */


public class WordRepo {
    /* initial small Wordle dictionary */
    private static final List<String> words = Arrays.asList(
            "TABLE", "CLASS", "BYTES", "INPUT","APPLE",
            "BOARD", "STORE", "WHICH", "FRUIT",
            "PHONE","DEVIL", "ARRAY", "ASSET", "WATER", "WORDS");

    private static final Random random =  new  Random();
    public static final int WORD_LENGTH = 5;

    /**
     * Utility class - mark constructor as private
     */
    private WordRepo(){}

    /**
     * Picks a random target word from the static list.
     * Objective: Provide the word the user is trying to guess.
     * Scope: Called once per game start.
     *
     * @return the chosen target word (Uppercase)
     */
    public static String pickTargetWord()
    {
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    /**
     * Returns the full list of words.
     * Objective: Provide access to the static word list if needed later.
     * Scope: Not required for this story but useful for future features.
     *
     * @return immutable list of candidate words
     */
    public static List<String> getWords() {
        return words;
    }

    /**
     * Generates enum array with color-coded feedback <br>
     <p> Scope: Logic only, does not track number of user guesses
     </p>
     @param playerGuess - user guess for the word
     @param targetWord - chosen answer for this wordle game
     @return - enum array with color-coded feedback
     @author Xavier Orrala
     */
    public static ConsoleUI.FeedbackType[] GenerateColoredFeedback(String playerGuess, String targetWord)
    {
        // Array to store color coded feedback results -- CAN BE REFACTORED TO INCLUDE MAX_GUESS CONSTANT?
        ConsoleUI.FeedbackType[] results = new ConsoleUI.FeedbackType[WORD_LENGTH];

        // Separate playerGuess and targetWord into character arrays
        char[] playerGuessToCharArray = playerGuess.toCharArray();
        char[] targetWordToCharArray = targetWord.toCharArray();

        // Create hashmap to store letter(s) and letter count of targetWord <KEY: letter, VALUE: count>
        HashMap<Character,Integer> targetWordLetterCount = new HashMap<>();
        for(char letter: targetWordToCharArray)
        {
            // Find the count of each letter from the targetWord, then add to the hashmap
            // Default value for count of each letter is 0
            targetWordLetterCount.put(letter, targetWordLetterCount.getOrDefault(letter,0)+1);
        }

        // Run through playerGuessToCharArray and compare to targetWordToCharArray - color code as appropriate - prioritize green, then yellow, then gray
        // First run to check for letters in the correct location (GREEN)
        for(int i = 0; i < results.length; i++)
        {
            if(playerGuessToCharArray[i] == targetWordToCharArray[i])
            {
                // Set correct letter location to GREEN enum + decrease count for letter
                results[i] = ConsoleUI.FeedbackType.GREEN;
                targetWordLetterCount.put(playerGuessToCharArray[i], targetWordLetterCount.get(playerGuessToCharArray[i]) - 1);
            }
        }

        // Run to check for correct letters in the incorrect location (YELLOW), then the rest are (GRAY/RESET)
        for(int i = 0; i < results.length; i++)
        {
            // Skip index if letter was correct (GREEN)
            if(results[i] == null)
            {
                char currentGuessCharacter = playerGuessToCharArray[i];

                // Get the remaining count of specified letter
                int remainingTargetWordLetterCount = targetWordLetterCount.getOrDefault(currentGuessCharacter, 0);
                if(remainingTargetWordLetterCount > 0)
                {
                    // Set correct letter, but incorrect location to YELLOW enum + decrease count for letter
                    results[i] = ConsoleUI.FeedbackType.YELLOW;
                    targetWordLetterCount.put(currentGuessCharacter, remainingTargetWordLetterCount - 1);
                }
                else { // Letter not found (no count for specified letter)
                    results[i] = ConsoleUI.FeedbackType.RESET;
                }
            }
        }
        return results;
    }
}
