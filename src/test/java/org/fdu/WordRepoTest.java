package org.fdu;

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
}