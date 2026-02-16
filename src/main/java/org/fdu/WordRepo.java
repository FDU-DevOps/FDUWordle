package org.fdu;

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
}
