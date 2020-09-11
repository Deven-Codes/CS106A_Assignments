/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

//import acm.graphics.*;
import acm.program.*;
import acm.util.*;

//import java.awt.*;

public class Hangman extends ConsoleProgram {

	public static final String WELCOME_TEXT = "Welcome to Hangman!!";
	public static final int GUESS = 8;
	
	public void init(){
		canvas = new HangmanCanvas();
		add(canvas);
		             
	}
	
    public void run() {
		/* You fill this in */
    	game();
    }	
    
    private void game() {
    	canvasSetup();
    	gameSetup();
    	gamePlay();
    	gameReplay();
    }
    
    /* This function will set the canvas dimentions and initalize objects added at the beginning of the game. */
    private void canvasSetup(){
    	
    	width = canvas.getWidth();
    	height = canvas.getHeight();
    	
    	canvas.setCanvasDimension(width, height);
    	
    	canvas.reset();
    }
    
    /* gameSetup() will initialize or reset the game. */
    private void gameSetup() {
    	println(WELCOME_TEXT);
    	randomWord();
    	characterChecker();
    	wordDisplay();
    	guessCounter();
    	
    }
    
    /* This program will randomly select the word from HangmanLexicon. */
    private void randomWord() {
    	
    	wordIndex = rgen.nextInt(0, lexicon.getWordCount());
    	word = lexicon.getWord(wordIndex);
    	
    }
    
    /* This function will interact with user. */
    private void gamePlay() {
    	while(true){
    		if(result.equals(word)){
    			println("Congratulaions! \nYou guess the correct word");
    			canvas.gameWon();
    			break;
    		} else if(guess < 1) {
    			println("You couldn't guess the word. \nGAME OVER!!!");
    			println("Secret Word : "+ word);
    			canvas.gameOver();
    			break;
    		} else {
    			characterEnter();
            	characterChecker();
            	wordDisplay();
            	guessCounter();
            	canvas.hangman(guess);
    		}
    	}	
    } 
    
    /* This function will display the word. */
    private void wordDisplay() {
    	print("The word now look like this: ");
    	println(result);
    	canvas.displayWord(result);    	
    }
    
    /* This function will ask user to guess and enter the character */
    private void characterEnter() {
    	text = readLine("Your Guess: ");
    	if(text.length()>1){
    		println("Please enter only one character!!!");
    		println("Selecting first character: "+text.charAt(0));
    	} else if(text.length() == 0) {
    		println("Please enter a character!!!");
    		characterEnter();
    	}
    	char i = text.charAt(0);
    	if((Character.isLetter(i)) || (Character.isDigit(i))){
    		ch = text.charAt(0);
    		if((Character.isLetter(ch))){
    			ch = Character.toUpperCase(ch);
    		}	
    	} else {
    		println("Illegal Character Entered. \nPlease try again.");
    		characterEnter();
    	}
    	
    }
    
    /* This program initialize the result variable for the first time as a string of '-' upto choosen word length.
     * and checks if the user make the right guess or not throughout the gameplay.*/
    private void characterChecker() {
    	flag = 0;
    	String localResult = "";
    	if(result.length() == 0) {
    		for(int i = 0; i < word.length(); i++) {	
        			localResult = localResult + "-";
        	}
        } else {
        	for(int i = 0; i < word.length(); i++) {	
        		if(result.charAt(i) != '-') {
            		localResult = localResult + result.charAt(i);
            		if(ch == result.charAt(i)) {
            			println("You have already guessed that character!!!");
            			flag = 2;
            		}
            		
            	} else if(ch == word.charAt(i)) {
            		localResult = localResult + word.charAt(i);
            		flag = 1;
            	} else {
            		localResult = localResult + '-';
            	}
        	}
        }	
    	result = localResult;
    	guessChecker(); 	
    }
    
    /* This function will check whether the guess was right or not. */
    private void guessChecker() {
    	if(ch != 0) {
    		if(flag == 1) {
    			println("That guess is correct");
    		} else {
    			println("There are no "+ch+"'s in the word.");
    			canvas.noteIncorrectGuess(ch);
    		}
    	}
    }
    
    /* This function will count for guesses */
    private void guessCounter() {
    	if(flag == 0 && ch != 0 ) {
    		guess--;
        	println("You have "+ guess +" guesses left.");	
        	
    	} else {
    		println("You have "+ guess +" guesses left.");
    	}
    }
    
    private void gameReplay() {
    	
    	replay = readLine("Replay ? ...... enter (y/n): ");
    		
    	if(replay.charAt(0) == 'y' || replay.charAt(0) == 'Y') {	
    		ch = 0;
    		guess = GUESS;
    		result = "";
    		canvas.reset();
    		game();
    	} else if(replay.charAt(0) == 'n' || replay.charAt(0) == 'N') {
    		println("Thank you for playing.");
    	}
    }
    
    private RandomGenerator rgen = RandomGenerator.getInstance();
    private int wordIndex;
    private int flag;
    private int guess = GUESS;
    private String word;
    private String text;
    private String result = "";
    private char ch = 0;
    private String replay;
    private HangmanLexicon lexicon = new HangmanLexicon();
    private HangmanCanvas canvas;
    private int width;
    private int height;
}
