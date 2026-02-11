package org.fdu;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;

    /**
     * Constructs ConsoleUI.
     * Objective: Prepare to read input from the console.
     * Scope: Created once in App.
     */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints a line to the console.
     * Objective: Display messages to the user.
     * Scope: Used for intro and responses.
     *
     * @param message message text to print
     * @return void
     */
    public void println(String message) {
        System.out.println(message);
    }

    /**
     * Prompts the user and returns the entered line.
     * Objective: Ask for a guess and capture the user's input.
     * Scope: Returns the raw string (may be blank).
     *
     * @param prompt prompt text to display
     * @return the raw user input line
     */
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Closes console resources.
     * Objective: Cleanly close the scanner at the end of the program.
     * Scope: Call once after the game ends.
     *
     * @return void
     */
    public void close() {
        scanner.close();
    }
}

