package org.fdu;

import org.junit.jupiter.api.Test;

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
}