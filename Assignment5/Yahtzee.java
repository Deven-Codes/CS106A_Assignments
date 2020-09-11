/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.io.*;
import java.util.*;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	/* 
	 * Run will take the user input for number of players and 
	 * Displays the fresh game window for the game play.
	 */
	public void run() {
		dialog = getDialog();
		
		nPlayers = dialog.readInt("Enter number of players (Max-4) or Enter 0 to quit game.");
		if( nPlayers == 0) {
			dialog.println("Game Successfully Quit.");
		} else {
			if(nPlayers > MAX_PLAYERS) {
				dialog.println("Number of players exceeds the limit.");
				dialog.println("Selecting number of players as 4.");
				nPlayers = MAX_PLAYERS;
			} else if(nPlayers == 1) {
				dialog.println("You're playing in Single player mode.");
				targetScore = dialog.readInt("Enter target score for game.");
			} 
			playerNames = new String[nPlayers];
			for (int i = 1; i <= nPlayers; i++) {
				playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
			}
			display = new YahtzeeDisplay(getGCanvas(), playerNames);	
			playGame();

		}
		
	}
	


	/* this function will initialize the playerScores matrix and game window for next player and next round. */
	private void playGame() {
		playerScores = new int[N_CATEGORIES][nPlayers];
		
		for(int j = 0; j < nPlayers; j++){
			for(int i = 0; i < nCategories; i++){
				playerScores[i][j] = -1;
			}
		}
	
		for(int i=0; i<rounds; i++) {
			playerIndex = 1;
			for(int j = 0; j<nPlayers; j++) {
				if(nPlayers == 1) {
					display.printMessage("Score to beat : " + targetScore + "     Click  \"Roll Dice\".          Turns Left: "  + 3);
				} else {
					display.printMessage(playerNames[j] + " 's turn!          Click  \"Roll Dice\".          Turns Left: " + 3);
				}	
				display.waitForPlayerToClickRoll(playerIndex);
				playerTurn();
				playerIndex++;
			}
		}
		gameScore();
	}
	
	/* This function will keep track of the rolls and number of turns for a player */
	private void playerTurn() {
		roll();
		display.displayDice(dieRolls);
		while(turns <= 3) {
			if(nPlayers == 1){ 
				display.printMessage("Score to beat : " + targetScore + "     Click  \"Roll Dice\".          Turns Left: "  + (4-turns));
			} else {
				display.printMessage(playerNames[playerIndex - 1] + " 's turn!          Click  \"Roll Dice\".          Turns Left: " + (4-turns));

			}
			display.waitForPlayerToSelectDice();
			if(!checkDieSelection()) break;
			reRoll();
			display.displayDice(dieRolls);
		}
		sort();
		category = display.waitForPlayerToSelectCategory();	
		checkForYahtzee();
		scoreBoard();
		showScore();
	}
	
	/* This function which will handle the first roll for each player in every round*/
	private void roll() {
		turns = 2;
		dieRolls =  new int[5];
		for(int i = 0; i < dieRolls.length; i++) {
			dieRolls[i] = rgen.nextInt(1, 6);
			
		}
	}
	
	/* This function will handle the second and third rolls done by the players in each rounds. */
	private void reRoll() {	
		turns++;
		for(int i = 0; i < dieRolls.length; i++) {
			if(display.isDieSelected(i)) {
				dieRolls[i] = rgen.nextInt(1, 6);
			}
		}
	}
	
	private boolean checkDieSelection() {
		int flag = 0; 
		
		for(int i = 0; i < nDice; i++){
			if(display.isDieSelected(i)){
				flag = 1;
			}
		}
		
		if(flag == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/* This function will sort the dieRolls array using bubble sort. */
	private void sort() {
		for(int k = 1; k < (dieRolls.length ); k++) {
			int flag = 0;
			for(int i = 0; i < (dieRolls.length -k); i++) {
				if(dieRolls[i] > dieRolls[i+1]) {
					int num = dieRolls[i];
					dieRolls[i] = dieRolls[i+1];
					dieRolls[i+1] = num;
					flag = 1;
				}
			}
			if(flag == 0) break;
		}
	}
	
	/* This function will check the cateogires selected by players during gameplay. */
	private void scoreBoard() {
		switch(category) {
			case 1:
			case 2:	
			case 3:	
			case 4:
			case 5:
			case 6:
				numbers();
				break;
			case 9:
				threeOfAKind();
				break;
			case 10:
				fourOfAKind();
				break;
			case 11:
				fullHouse();
				break;
			case 12:
				smallStraight();
				break;
			case 13:
				largeStraight();
				break;
			case 14:
				yahtzee();
				break;
			case 15:
				chance();
				break;
			default:
				dialog.println("Please! Select a valid category.");
				category = display.waitForPlayerToSelectCategory();	
				scoreBoard();
		}
	}
	
	/* Calculate the score for die numbers */
	private void numbers() {
		score = 0;
		for(int i = 0; i < dieRolls.length; i++) {
			if(dieRolls[i] == category) {
				score = score + dieRolls[i];
			}
		}
	}
	
	/* Calculate the score for Three of a kind. */
	private void threeOfAKind() {
		int flag = 0;
		for(int i = 0; i < (dieRolls.length - 2); i++) {
			if(dieRolls[i] == dieRolls[i+1]) {
				if(dieRolls[i+1] == dieRolls[i+2]) {
					flag = 1;
					break;
				}
			}
		}
		
		if(flag == 1) {
			chance();
		} else {
			score = 0;

		}
	}

	/* Calculate the score for Four of a kind. */
	private void fourOfAKind() {
		int flag = 0;
		for(int i = 0; i < (dieRolls.length -3); i++) {
			if(dieRolls[i] == dieRolls[i+1]) {
				if(dieRolls[i+1] == dieRolls[i+2]) {
					if(dieRolls[i+2] == dieRolls[i+3]) {
						flag = 1;
						break;
					}
				}
			}
		}
		
		if(flag == 1) {
			chance();
		} else {
			score = 0;

		}

	}
	
	/* Calculate the score for Full House. */
	private void fullHouse() {
		int flag = 0;
		int mid = (dieRolls.length / 2);
		int last = (dieRolls.length - 1);
		
		if(dieRolls[0] == dieRolls[1] && dieRolls[last - 1] == dieRolls[last]) {
			if(dieRolls[mid] == dieRolls[mid - 1]){
				flag = 1;				
			} else if(dieRolls[mid] == dieRolls[mid + 1]) {
				flag = 1;
			}
		}
		
		if(flag == 1) {
			score = 25;
		} else {
			score = 0;
		}
		

	
	}
	
	/* Calculate the score for Small Straight. */
	private void smallStraight() {
		int flag = 0;
		for(int i = 0; i < (dieRolls.length - 1); i++) {
			int num1 = dieRolls[i];
			int num2 = dieRolls[i+1];
			if(flag < 2){
				if((num1 + 1) != num2) flag++;	
			} else {
				break;
			}
		}
		
		if(flag >= 2){
			score = 0;
		} else {
			score = 30;
		}
		

	}
	
	/* Calculate the score for Large Straight. */
	private void largeStraight() {
		int flag = 0;
		for(int i = 0; i < (dieRolls.length - 1); i++) {
			int num1 = dieRolls[i];
			int num2 = dieRolls[i+1];
			
			if((num1 + 1) != num2) {
				flag = 1;
				break;
			}
		}
		if(flag == 1){
			score = 0;
		} else {
			score = 40;
		}

	}
	
	/* Calculate the score for Yahtzee. */
	private void yahtzee() {
		int flag = 0;
	
		for(int i = 0; i < (dieRolls.length - 1); i++) {
			if(dieRolls[i] != dieRolls[i+1]) {
				flag = 1;
				break;
			}
		}
		
		if(flag == 1){
			score = 0;
		} else {
			score = 50;
		}
		yahtzeeFlag = flag;
	}
	
	/* Calculate the score for Chance. */
	private void chance() {
		score = 0;
		for(int i = 0; i < (dieRolls.length); i++) {
			score += dieRolls[i];
		}
		
	}
	
	private void checkForYahtzee() {
		if(category != yahtzeeRow){
			yahtzee();
			yahtzeeScore = playerScores[yahtzeeRow - 1][playerIndex - 1];
			if(yahtzeeFlag == 0 && (yahtzeeScore != 0 || yahtzeeScore != -1)){
				yahtzeeScore = yahtzeeScore + 100 ;
				playerScores[yahtzeeRow - 1][playerIndex - 1] = yahtzeeScore;
				display.updateScorecard(yahtzeeRow, playerIndex, yahtzeeScore);
			}
		}
	}
	
	/* Show the score for all categories and update playerScore.  */
	private void showScore() {
		if(playerScores[category - 1][playerIndex - 1] == -1){
			playerScores[category - 1][playerIndex - 1] = score;
			display.updateScorecard(category, playerIndex, score);
		} else {
			dialog.println("This category is already selected.");
			dialog.println("Please! Select another category.");
			category = display.waitForPlayerToSelectCategory();	
			scoreBoard();
			showScore();
		}
	}
	
	/* Calculate the Upper Score and Lower Score for all players. */
	private void gameScore() {
		for(int j = 0; j < nPlayers; j++) {
			upperScore = 0;
			lowerScore = 0;
			for(int i = 0; i < (upperLimit - 1); i++) {
				if(playerScores[i][j] > 0) {
					upperScore += playerScores[i][j];
				}
			}
			
			for(int i = upperBonus; i < (lowerLimit - 1); i++) {
				if(playerScores[i][j] > 0) {
					lowerScore += playerScores[i][j];
				}
			}
			
			int index = j + 1;
			
			playerScores[upperLimit - 1][j] = upperScore;
			display.updateScorecard(upperLimit, index, upperScore);
			
			playerScores[lowerLimit - 1][j] = lowerScore;
			display.updateScorecard(lowerLimit, index, lowerScore);
			
			upperBonus(j, index);
			totalScore(j, index);
		}
		
		winner();
	}
	
	/*Calculate the Upper Bonus for all players.*/
	private void upperBonus(int j, int index) {
		if(upperScore > 62) {
			bonusScore = 35;
			playerScores[upperBonus - 1][j] = bonusScore;
			display.updateScorecard(upperBonus, index, bonusScore);
		} else {
			bonusScore = 0;
			playerScores[upperBonus - 1][j] = bonusScore;
			display.updateScorecard(upperBonus, index, bonusScore);
		}
	}

	/*Calculate the Total Score for all players.*/
	private void totalScore(int j, int index) {
		totalScore = upperScore + lowerScore + bonusScore;
		playerScores[total - 1][j] = totalScore;
		display.updateScorecard(total, index, totalScore);
	}
	
	/*Decide the winner from all players .*/
	private void winner() {
		max = playerScores[total - 1][0];
		winner = playerNames[0];
		for(int i = 0; i < nPlayers; i++) {
			if(playerScores[total - 1][i] > max) {
				max = playerScores[total - 1][i];
				winner = playerNames[i];

			}
		}
		
		nameList.add(winner);
		scoreList.add(max);
		fileReader();
		winnerDeclare();
	//	highScores();
		run();
		
	}
	
	private void winnerDeclare() {
		if(nPlayers == 1){
			if(max >= targetScore){
				dialog.println("YOU WIN!!!");
			} else {
				dialog.println("YOU LOSE!!!");
			}
			
		} else {
			dialog.println( winner + " is the winner!!!!");
		}
	}
	
	private void fileReader() {
		BufferedReader rd = openFile();
		try {
			while(true) {
				String line = rd.readLine();
				if(line == null) break;
				token(line);
			}
			rd.close();
		} catch(IOException ex) {
			throw new ErrorException(ex);
		}
		
		
	}
	
	private void fileWriter() {
		listInit();
		try {
			PrintWriter wr = new PrintWriter( new FileWriter(fileName));
			int size = nameList.size();
			if(size > 5) size = 5;
			for(int i = 0; i < size; i++) {
				wr.println(names[i] + ": " + highScores[i]);
			}
			wr.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}
	
	private BufferedReader openFile() {
		BufferedReader rd = null;
		while(rd == null) {
			try{
				rd = new BufferedReader( new FileReader(fileName));
			}
			catch(IOException ex){
				fileWriter();
			}
		}	
		return rd;
		
	}
	
	private void token( String line) {
		StringTokenizer tokens = new StringTokenizer(line, ": ");
		for(int count = 0; tokens.hasMoreTokens(); count++) {
			if(count == 0) {
				nameList.add(tokens.nextToken());
			} else if(count == 1) {
				scoreList.add(Integer.parseInt(tokens.nextToken()));
			}
		}
	}
	
	private void listInit() {
		names = new String[nameList.size()];
		highScores = new int[scoreList.size()];
		for(int i = 0; i < nameList.size(); i++) {
			names[i] = nameList.get(i);
			highScores[i] = scoreList.get(i);
			}
		decrementSort();
	}
	
	private void decrementSort() {
		for(int k = 1; k < (highScores.length ); k++) {
			int flag = 0;
			for(int i = 0; i < (highScores.length -k); i++) {
				if(highScores[i] < highScores[i+1]) {
					
					int num = highScores[i+1];
					highScores[i+1] = highScores[i];
					highScores[i] = num;
					
					String name = names[i+1];
					names[i+1] = names[i];
					names[i] = name;
					
					flag = 1;
				}
			}
			if(flag == 0) break;
		}
		
		printList();
	}
	
	private void printList() {
		for(int i = 0; i < highScores.length; i++) {
			System.out.println(names[i] + " : " + highScores[i]);
		}
	}
	
	private void highScores() {
		dialog.println("High Score ------ " + names[0] + " : " + highScores[0]);

	}
	
	
/* Private instance variables */
	private IODialog dialog;
	private int nPlayers ;
	private String[] playerNames;
	private int playerIndex;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dieRolls ;
	private int[][] playerScores;
	private int turns = 1;
	private int category;
	private int score;
	private int upperScore = 0;
	private int upperLimit = UPPER_SCORE;
	private int lowerScore = 0;
	private int upperBonus = UPPER_BONUS;
	private int bonusScore ;
	private int lowerLimit = LOWER_SCORE;
	private int total = TOTAL;
	private int totalScore = 0;
	private int rounds = N_SCORING_CATEGORIES;
	private int nCategories = N_CATEGORIES;
	private String winner = null;
	private int max;
//	private String fileName = "HighScore.txt";
	private File fileName = new File("HighScore.txt");
	private int yahtzeeFlag;
	private int yahtzeeScore;
	private int yahtzeeRow = YAHTZEE;
	private int targetScore;
	private int nDice = N_DICE;
	private ArrayList<String> nameList = new ArrayList<String> ();
	private ArrayList<Integer> scoreList = new ArrayList<Integer> ();
	private String[] names;
	private int[] highScores;

}

