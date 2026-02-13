package org.fdu;

/**
 * Contains main() and creates/starts the game components
 */
public class App 
{
    public static void main( String[] args )
    {
        WordRepo repo = new WordRepo();
        GameManager manager = new GameManager(repo);
        manager.runGame();
    }
}