FDU Devops Class Wordle Project

Requirements: We support Java 25, 21, and 17 and the Windows 11 Command Prompt

In order to run this, download the latest successful workflow under Actions. Download the jar file under artifacts. 
Then, extract the zip file and navigate to the directory containing the jar file. 
Then either open command prompt in that directory directly, or navigate to that directory from command prompt using the cd command.

Example Path: "C:\Users\<username>\Downloads\wordle-jar

Then, run the command java -jar FDUWordle-1.0-VERSION.jar

Finally, open a browser and go to either localhost:8080 or 127.0.0.1:8080

Updates & Fixes:
* Version: 1.0-DEVOPS-273 <br>
    created multiple guesses game
* Version: 1.0-DEVOPS-333
* Introduced architectural changes for a better user experience, better utilizing DTOs.
* Current functionality is still the same - one guess, one run through

Current Open Bugs:
* Game cannot be ran multiple times. Only ONE instance can be ran at a time as running multiple instances can affect either game.

