package org.fdu;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Stores a static list of valid words and selects a random target word.
 * Scope:
 * - Hard-coded list of 10-15 valid words
 * - Words are stored in UPPERCASE
 */
public class WordRepo {

    private final List<String> words;
    private final Random random;

    /**
     * Constructs the repository with a static list of words.
     */
    public WordRepo() {
        this.words = Arrays.asList("TABLE", "CLASS", "BYTES", "INPUT","APPLE",
                "BOARD", "STORE", "WHICH", "FRUIT", "PHONE",
                "DEVIL", "ARRAY", "ASSET", "WATER", "WORDS")
                ;
        this.random = new Random();
    }

    /**
     * Picks a random target word from the list.
     *
     * @return the selected target word in UPPERCASE
     */
    public String pickTargetWord() {
        return words.get(random.nextInt(words.size()));
    }
}
