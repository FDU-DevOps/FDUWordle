package org.fdu;

/**
 * Contains main() and creates/starts the game components
 */
public class App {

    /**
     * Entry point for the Wordle (Lite) application.
     * Objective: Construct required classes and start the game.
     * Scope: Run from IDE only by running App.java.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        WordRepo repo = new WordRepo();

        GameManager manager = new GameManager(ui, repo);
        manager.runGame();

        ui.close();
    }
}

