package org.fdu;

/**
 * Maintains a dictionary of allowable words and validates player guesses against the rules <br>
 * <p>
 * Expose method to pick a random word from the game dictionary
 * Expose method to return if a guess is valid, ie meets the rules (e.g. 5 letters, a-z, A-Z)
 */
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordRepo {
    private final List<String> words;
    private final Random random;

    /**
     * Constructs the word repository.
     * Objective: Store a static list of 10-15 valid words.
     * Scope: List is hard-coded and does not change at runtime.
     */
    public WordRepo()
    {
        this.words = Arrays.asList(
                "TABLE", "CLASS", "DATA", "INPUT",
                "BITS", "STORAGE", "WHICH", "FRUIT", "LAPTOP",
                "DEVILS", "KNIGHTS", "SCHOOL", "WATER", "BAGS","APPLE");
        this.random = new Random();
    }

    /**
     * Picks a random target word from the static list.
     * Objective: Provide the word the user is trying to guess.
     * Scope: Called once per game start.
     *
     * @return the chosen target word (Uppercase)
     */
    public String pickTargetWord()
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
    public List<String> getWords() {
        return words;
    }
}
