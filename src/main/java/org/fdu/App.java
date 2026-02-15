package org.fdu;

/**
 * Contains main() and creates/starts the game
 */
public class App 
{
    public static void main(String[] args) {
        runGame();
    }

    /**
     * Runs a single game session (maximum 1 guess).
     * End conditions:
     * - User guesses the word correctly
     * - User uses 1 guess (loss message shown)
     */
    public static void runGame() {
        GameManager manager = new GameManager();
        showIntro(manager);

        while (manager.isGameNotOver()) {
            String rawGuess = ConsoleUI.readLine("ENTER YOUR GUESS: ");
            String guess = manager.normalize(rawGuess);

            // OTHER: blank input does not count as an attempt
            if (guess.isEmpty()) {
                ConsoleUI.println("PLEASE ENTER A GUESS (NOT BLANK).");
                continue;
            }

            if (manager.doesGuessMatch(guess)) {
                ConsoleUI.println("CORRECT! YOU GUESSED THE WORD: " + manager.getTargetWord());
                return;
            } else {
                int attemptsLeft = manager.getMaxGuesses() - manager.getGuessesUsed();
                if (attemptsLeft > 0) {
                    ConsoleUI.println("NOT CORRECT. ATTEMPTS LEFT: " + attemptsLeft);
                }
            }
        }

        // Out of guesses
        ConsoleUI.println("YOU LOST! THE CORRECT ANSWER WAS: " + manager.getTargetWord());
    }

    /**
     * Displays the introduction messages for the game.
     */
    private static void showIntro(GameManager manager) {
        ConsoleUI.println("WELCOME TO WORDLE! GUESS THE SECRET WORD.");
        ConsoleUI.println("YOU HAVE " + manager.getMaxGuesses()+ " GUESS.");
    }
}

