package org.fdu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ConsoleUITest {

    // Bucket to capture console output (instead of printing to screen)
    // ByteArrayOutputStream stores output data in memory as array of bytes
    private ByteArrayOutputStream bucket;

    // Saves the original System.out so we can restore it
    private PrintStream originalOut;

    @BeforeEach
    // Runs before each test to ensure that each test gets fresh and empty bucket (not merged with the previous test)
    void setUp() {
        // Create a new bucket before each test
        bucket = new ByteArrayOutputStream();
        // Save the original console output
        originalOut = System.out;
        // Redirect System.out to our bucket instead of console
        System.setOut(new PrintStream(bucket));
    }

    @AfterEach
    // Runs after each test to clean up and make the system to point back to the console
    void tearDown() {
        // Restore System.out back to normal console
        System.setOut(originalOut);
    }


    @Test
    void println() {
    }

    @Test
    void readLine() {
    }
}