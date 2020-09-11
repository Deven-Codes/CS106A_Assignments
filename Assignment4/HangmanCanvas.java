/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		removeAll();
		addScaffold();
		character = "";
		
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		/* You fill this in */
		if(label != null) remove(label);
		
		label = new GLabel(word);
		label.setFont("SansSerif-24");
		label.setLocation(40, (HEIGHT - 50));
		add(label);
		
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		/* You fill this in */
		if(guess != null) remove(label);
		
		character = character + letter;
		
		guess = new GLabel(character);
		guess.setFont("SansSerif-16");
		guess.setLocation(40, (HEIGHT - 20));
		add(guess);
	}

/* This function will the width and height of the canvas added in HANGMAN class. */
	public void setCanvasDimension(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
	}
	
	private void addScaffold() {
		int xStart = (WIDTH / 2) - BEAM_LENGTH;
		int yStart = (HEIGHT / 2) - (SCAFFOLD_HEIGHT / 2) - 50;
		
		GLine scaffold = new GLine(xStart, yStart, xStart, (yStart + SCAFFOLD_HEIGHT));
		
		GLine beam = new GLine(xStart, yStart, (xStart + BEAM_LENGTH), yStart);
		
		GLine rope = new GLine((xStart + BEAM_LENGTH), yStart, (xStart + BEAM_LENGTH), (yStart + ROPE_LENGTH));
		
		add(scaffold);
		add(beam);
		add(rope);
		
		setBodyStart((xStart + BEAM_LENGTH), (yStart + ROPE_LENGTH));
	}
	
	/* This function initialize the top mid point of the body. This point is the end point of the rope. */
	private void setBodyStart(int X_body, int Y_body) {
		bodyMidX = X_body;
		bodyTopY = Y_body;
	}
	
	public void hangman(int part) {
		switch((8 - part)){
		
		case 1:
			head();
			break;
		
		case 2:
			body();
			break;
		
		case 3:
			rightArm();
			break;
			
		case 4:
			leftArm();
			break;
			
		case 5:
			rightLeg();
			break;
			
		case 6:
			leftLeg();
			break;
			
		case 7:
			rightFoot();
			break;
		
		case 8:
			leftFoot();
			break;
			
		default:
			break;
		}
	}
	
	private void head() {
		
		int headX = bodyMidX - (HEAD_RADIUS);
		GOval head = new GOval(headX, bodyTopY, (2*HEAD_RADIUS),(2*HEAD_RADIUS) );
		add(head);
	}
	
	private void body() {
		
		int body_Y1 = bodyTopY + (2*HEAD_RADIUS);
		int body_Y2 = body_Y1 + BODY_LENGTH;
		
		int hip_X1 = bodyMidX - HIP_WIDTH;
		int hip_X2 = bodyMidX + HIP_WIDTH;
		
		GLine body = new GLine(bodyMidX, body_Y1, bodyMidX, body_Y2);
		
		GLine hip = new GLine(hip_X1, body_Y2, hip_X2, body_Y2);
		
		add(body);
		add(hip);
	}
	
	private void rightArm() {

		int upperArmY = bodyTopY + (2*HEAD_RADIUS) + ARM_OFFSET_FROM_HEAD;
		int upperArmX1 = bodyMidX - UPPER_ARM_LENGTH;
		int upperArmX2 = bodyMidX;
		
		int lowerArmX = upperArmX1;
		int lowerArmY1 = upperArmY;
		int lowerArmY2 = upperArmY + LOWER_ARM_LENGTH;
		
		GLine upperArm = new GLine(upperArmX1, upperArmY, upperArmX2, upperArmY);
		
		GLine lowerArm = new GLine(lowerArmX, lowerArmY1, lowerArmX, lowerArmY2);
		
		add(upperArm);
		add(lowerArm);
		
	}
	
	private void leftArm() {
		
		int upperArmY = bodyTopY + (2*HEAD_RADIUS) + ARM_OFFSET_FROM_HEAD;
		int upperArmX1 = bodyMidX;
		int upperArmX2 = bodyMidX + UPPER_ARM_LENGTH;
		
		int lowerArmX = upperArmX2;
		int lowerArmY1 = upperArmY;
		int lowerArmY2 = upperArmY + LOWER_ARM_LENGTH;
		
		GLine upperArm = new GLine(upperArmX1, upperArmY, upperArmX2, upperArmY);
		
		GLine lowerArm = new GLine(lowerArmX, lowerArmY1, lowerArmX, lowerArmY2);
		
		add(upperArm);
		add(lowerArm);
	}
	
	private void rightLeg() {
		int legX = bodyMidX - HIP_WIDTH;
		int legY1 = bodyTopY + (2*HEAD_RADIUS) + BODY_LENGTH;
		int legY2 = legY1 + LEG_LENGTH;
		
		GLine leg = new GLine(legX, legY1, legX, legY2);
		
		add(leg);
	}
	
	private void leftLeg() {
		int legX = bodyMidX + HIP_WIDTH;
		int legY1 = bodyTopY + (2*HEAD_RADIUS) + BODY_LENGTH;
		int legY2 = legY1 + LEG_LENGTH;
		
		GLine leg = new GLine(legX, legY1, legX, legY2);
		
		add(leg);
	}
	
	private void rightFoot() {
		
		int footX1 = bodyMidX - HIP_WIDTH - FOOT_LENGTH;
		int footX2 = bodyMidX - HIP_WIDTH;
		int footY = bodyTopY + (2*HEAD_RADIUS) + BODY_LENGTH + LEG_LENGTH;
		
		GLine foot = new GLine(footX1, footY, footX2, footY);
		
		add(foot);
	}
	
	private void leftFoot() {
		
		int footX1 = bodyMidX + HIP_WIDTH;
		int footX2 = bodyMidX + HIP_WIDTH + FOOT_LENGTH;
		int footY = bodyTopY + (2*HEAD_RADIUS) + BODY_LENGTH + LEG_LENGTH;
		
		GLine foot = new GLine(footX1, footY, footX2, footY);
		
		add(foot);
		
	}
	
	public void gameWon() {
		double xPoint = (WIDTH - 150) / 2;
		double yPoint = (HEIGHT - 50) / 2;
		
		
		GRect rect = new GRect(xPoint, yPoint, 150, 50);
		rect.setFilled(true);
		rect.setFillColor(Color.GREEN);
		
		GLabel label = new GLabel("YOU WON!!!" );
		label.setFont("SansSerif-24");
		label.setLocation( xPoint + (150 - label.getWidth()) / 2, yPoint + (50 + label.getAscent()) / 2 );
		
		add(rect);
		add(label);
		
	}
	
	public void gameOver() {
		double xPoint = (WIDTH - 150) / 2;
		double yPoint = (HEIGHT - 50) / 2;
		
		GRect rect = new GRect(xPoint, yPoint, 150, 50);
		rect.setFilled(true);
		rect.setFillColor(Color.RED);
		
		GLabel label = new GLabel("Game Over!!!");
		label.setFont("SansSerif-24");
		label.setLocation( xPoint + (150 - label.getWidth()) / 2, yPoint + (50 + label.getAscent()) / 2 );
		
		add(rect);
		add(label);
		
	}
	
/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

/* Instance variables of class for Arithmetic Calculations. */	
	private static int WIDTH;
	private static int HEIGHT;
	private static int bodyMidX;
	private static int bodyTopY;
	private GLabel label;
	private GLabel guess;
	private String character = "";
}
