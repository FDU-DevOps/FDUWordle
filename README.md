FDU DEVOPS Class Wordle Project

Requirements: We support Java 25, 21, and 17 and the Windows 11 Command Prompt

In order to run this, download the latest successful workflow under Actions. Download the jar file under artifacts. 
Then, extract the zip file and navigate to the directory containing the jar file. 
Then either open command prompt in that directory directly, or navigate to that directory from command prompt using the cd command.

Example Path: "C:\Users\<username>\Downloads\wordle-jar

Then, run the command java -jar FDUWordle-1.0-VERSION.jar

Finally, open a browser and go to either localhost:8080 or 127.0.0.1:8080

**Current Open Bugs:**
* If game is ran in same browser, it will affect how the guesses are played, allowing you to enter more than 6 guesses. This only occurs if you open the game on the same browser, but different tabs. If this is done with different browsers, or one normal tab/one private tab, this bug does not occur.
* __DEVOPS-509__ - Manual build fails to start new build (old build continues to run on server)

**Updates & Fixes:**
* Version 1.0-DEVOPS-496
    * Migrated to new path structure on servers
        * e.g. test.fdugames.org/battleship
        * test.fdugames.org/wordle
        * test.fdugames.org/jenkins
    * Will also support without application change production server as well
    * Note: once merged, local access will also required explicit path
        * e.g. http://localhost:8081/battleship

* Version: 1.0-DEVOPS-274
     * Fixed bug where user could not play more than one instance at a time. Introduced sessions to fix this bug.
     * Players can now play multiple instances of the game at the same time.
       * Note: Some browsers on the same machine may experience this differently. To test, open one instance in a normal browser and another in a private browser.


* Version: 1.0-DEVOPS-389
     * Added "Submit Game Feedback Button" in the UI - leads user to feedback form for Wordle


* Version: 1.0-DEVOPS-273
     * created multiple guesses game


* Version: 1.0-DEVOPS-333
     * Introduced architectural changes for a better user experience, better utilizing DTOs.
     * Current functionality is still the same - one guess, one run through

* Version: 1.0-DEVOPS-283
     * Transition UI to Wordle Look and Feel

* Version: 1.0-DEVOPS-421
     * added new dictionary
     * checks if the word exists in the dictionary, otherwise throws error
  


