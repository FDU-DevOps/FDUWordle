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
     * - User uses 6 guesses (loss message shown)
     * Blank input does NOT count as an attempt
     * EvaluateInvalid guesses (non-5-letter or non-alphabetic)
     */
    public static void runGame() {
        GameManager manager = new GameManager();
        GameManager.showIntro(manager);

        while (manager.isGameNotOver()) {
            String rawGuess = ConsoleUI.readLine("ENTER YOUR GUESS: ");
            String guess = manager.getNormalizedGuess(rawGuess);

            // OTHER: blank input does not count as an attempt
            if (guess.isEmpty()) {
                ConsoleUI.println("PLEASE ENTER A GUESS (NOT BLANK).");
                continue;
            }
            if (WordRepo.isInvalidGuess(guess)) {
                ConsoleUI.println("INVALID GUESS. PLEASE ENTER A 5-LETTER WORD (A–Z ONLY).");
                continue;
            }

            if (manager.doesGuessMatch(guess)) {
                ConsoleUI.DisplayGuessResult(
                            GameManager.evaluateGuessAndGiveColoredFeedback(guess, manager.getTargetWord()),
                            guess);
                ConsoleUI.println("CORRECT! YOU GUESSED THE WORD: " + manager.getTargetWord());
                return;
            }
            else {
                int attemptsLeft = manager.getMaxGuesses() - manager.getGuessesUsed();
                if (attemptsLeft > 0) {
                    ConsoleUI.DisplayGuessResult(
                            GameManager.evaluateGuessAndGiveColoredFeedback(guess, manager.getTargetWord()),
                            guess);
                    ConsoleUI.println("NOT CORRECT. ATTEMPTS LEFT: " + attemptsLeft);
                }
            }
        }
        ConsoleUI.println("YOU LOST! THE CORRECT ANSWER WAS:"+manager.getTargetWord());
    }
}

