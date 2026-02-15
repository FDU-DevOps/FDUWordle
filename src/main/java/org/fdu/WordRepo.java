package org.fdu;

import java.util.HashMap;

/**
 * Maintains a dictionary of allowable words and validates player guesses against the rules <br>
 * <p>
 * Expose method to pick a random word from the game dictionary
 * Expose method to return if a guess is valid, ie meets the rules (e.g. 5 letters, a-z, A-Z)
 *
 * @author tbd
 */
public class WordRepo {

    /**
     * Generates enum array with color-coded feedback <br>
     <p> Scope: Logic only, does not track number of user guesses
     </p>
     @param playerGuess - user guess for the word
     @param targetWord - chosen answer for this wordle game
     @return - enum array with color-coded feedback
     @author (developer name)
     */

    public static ConsoleUI.FeedbackType[] GenerateColoredFeedback(String playerGuess, String targetWord)
    {
        // Array to store color coded feedback results
        ConsoleUI.FeedbackType[] results = new ConsoleUI.FeedbackType[5];

        // separate player guess and target word into character array
        char[] playerGuessToCharArray = playerGuess.toCharArray();
        char[] targetWordToCharArray = targetWord.toCharArray();

        // Create hashmap to store letter and letter count of targetWord
        HashMap<Character,Integer> targetWordLetterCount = new HashMap<>();
        for(char count: targetWordToCharArray)
        {
            // Find the count of each letter from the targetWord, add to the hashmap, default value for each letter is 0
            targetWordLetterCount.put(count, targetWordLetterCount.getOrDefault(count,0)+1);
        }

        // run through guess array, compare to target array, color code
        // Check for Green, then yellow, then gray
        //int remainingLetterCount = targetWordLetterCount.getOrDefault()

        // give color code to each enum

        // make enum array out of character array




        /*
            Cases:
            Letter matches
            Letter doesn't match
            Letter is present in word, but not in correct spot

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
        return results;
    }
}
