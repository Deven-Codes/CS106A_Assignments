/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		
		gameSetup();	
		gamePlay();
		
	}
	
	/* setup paddle and bricks along with paddle control */
	private void gameSetup() {
		
		brickSetup();	
		paddle();	
		addMouseListeners();
		bounceClip = MediaTools.loadAudioClip("bounce.au"); 
		
	}
	
	/* Play phase of the game positioning of ball in the game and movement of ball */
	private void gamePlay() {
		
		ballPosition();
		ballMotion();
		
	}
	
	/* setup bricks in the game */
	private void brickSetup() {	
		
		for(int i = 1; i <= NBRICK_ROWS; i++) {	
			// X point of the starting of row.
			double xStart = (WIDTH - (10 * BRICK_WIDTH) - (9 * BRICK_SEP)) / 2;
	
			for(int j = 1; j <= NBRICKS_PER_ROW; j++ ) {
				
				// Starting y point of row 1
				double yStart = BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (i-1) ;	
				brick = new GRect(xStart, yStart, BRICK_WIDTH, BRICK_HEIGHT);
				brickColor(brick, i );
				
				// Starting y point of next row.
				xStart += (BRICK_WIDTH + BRICK_SEP);
				
			}
		}
		
	}
	
	/* Color coding bricks according to rows of the bricks in rainbow like sequence */
	private void brickColor( GRect brick, int brickNumber ) {
		
		brick.setFilled(true);
		
		// if bricks are of row 1 or 2 then the bricks will be of color RED.
		if ( brickNumber == 1 || brickNumber == 2) {	
			brick.setColor(Color.RED);
			
		// if bricks are of row 3 or 4 then the bricks will be of color ORANGE.
		} else if (brickNumber == 3 || brickNumber == 4) {
			brick.setColor(Color.ORANGE);
		
		// if bricks are of row 5 or 6 then the bricks will be of color YELLOW.	
		} else if (brickNumber == 5 || brickNumber == 6) {
			brick.setColor(Color.YELLOW);
		
        // if bricks are of row 7 or 8 then the bricks will be of color GREEN.
		} else if (brickNumber == 7 || brickNumber == 8) {
			brick.setColor(Color.GREEN);
		
		// bricks of any other row will be of color CYAN.
		} else {
			brick.setColor(Color.CYAN);
			
		}
		
		add(brick);
	}

	/*  Setting up paddle at equal distance from both walls */
	private void paddle() {
		
		// Paddle is initially at the centre of the window but at fixed height.
		double xStart = (APPLICATION_WIDTH - PADDLE_WIDTH) / 2;
		double yStart = (APPLICATION_HEIGHT - 2*(PADDLE_Y_OFFSET + PADDLE_HEIGHT));
		
		paddle = new GRect( xStart, yStart, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);	
		paddle.setColor(Color.BLACK);	
		add(paddle);
		
	}
	
	/* controlling x direction of the paddle using mouse tracking */
	public void mouseMoved(MouseEvent e) {
		
		double xEnd = APPLICATION_WIDTH - PADDLE_WIDTH;
		double yStart = (APPLICATION_HEIGHT - 2*(PADDLE_Y_OFFSET + PADDLE_HEIGHT));
		
		if(e.getX() > 0 && e.getX() < xEnd){
			paddle.setLocation(e.getX(), yStart);
			
		}
	}
	
	/* positioning the ball in the middle of window */
	private void ballPosition () {
	
		double ballXPoint = (APPLICATION_WIDTH / 2) - BALL_RADIUS;
		double ballYPoint = (APPLICATION_HEIGHT / 2) - BALL_RADIUS;
		
		ball = new GOval( ballXPoint, ballYPoint, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
		add(ball);
		
	}
	
	/* Motion and behaviour of ball when moving and colliding with boundaries and objects*/
	private void ballMotion(){
		
		lives = NTURNS;
		brickCount = NBRICKS_PER_ROW * NBRICK_ROWS;
		vx = rgen.nextDouble(1.0, 3.1);
		if(rgen.nextBoolean(0.5)) vx = -vx;
		vy = 5.0;
		waitForClick();
		
		while(lives > 0){
			if(brickCount > 0){
				ball.move(vx, vy);
				displayScore();
				displaylife();
				checkObstacles();
				checkCollision();
				pause(50);
			} else if(brickCount == 0) {
				gameWon();
			}	
		}
		gameOver();
	}
	
	/* checking whether ball hits any of the boundaries */
	private void checkObstacles() {
		checkDown();
		checkUp();
		checkX();
		
	}
	
	/* Checking wheather ball hits the battom of the window */
	private void checkDown() {
		
		if(  ball.getY() > APPLICATION_HEIGHT - (4 * BALL_RADIUS) ) {
			
			// in case of ball hitting the bottom of the window player will lose 1 life.
			lives--;
			remove(ball);
			
			// when player loses all the lives game will be over.
			if(lives == 0) {
				
				gameOver();
				
			} else {
				
				// After player lost a life the ball will repositioned again and wait for user to click before start moving.
				ballPosition();
				waitForClick();
			}
			vy = 5.0;
		} 
	}
	
	/* checking if the ball hits the top boundary */
	private void checkUp() {
		
		// changing y direction of the ball when it hits the top boundary.
		if( 0 > ball.getY() ) {
			vy = -vy;
		} 
	}
	
	/* checking if the ball hits either of the side boundaries */
	private void checkX() {
		
		// changing x direction of the ball when it hits either of the side boundaries.
		if ( 0 > ball.getX() || ball.getX() > APPLICATION_WIDTH - (2 * BALL_RADIUS) ) {
			 vx = -vx;
		}
	}
	
	/* checking if the ball hits any of the objects present in the game */
	private void checkCollision() {
		
		double x = ball.getX();
		double y = ball.getY();
		double r = BALL_RADIUS;
		
		// checking if the ball hits any of the objects present at its one of the four bounding rectangle's point.
		if(getElementAt(x, y) != null) { // top left corner
			
			collider(x, y);	
			
		}  else if(getElementAt(x + (2 * r) , y) != null) { // top right corner
			
			collider(x + (2 * r) , y);
			
		} else if(getElementAt(x, y + (2 * r)) != null) { // bottom left corner
			
			collider(x, y + (2 * r));
			
		}  else if(getElementAt(x + (2 * r), y + (2 * r)) != null) { // bottom right corner
			
			collider(x + (2 * r), y + (2 * r));
			
		}
	}
	
	/* Deciding motion of the ball and object status after collision */
	private void collider(double x, double y) {
		GObject object = getElementAt(x, y);
		if (object == paddle){
			
			// if the object oollides with ball is paddle the diection of ball will change.
			
			if((ball.getX() + BALL_RADIUS ) > (paddle.getX() + (paddle.getWidth() / 2) ) ) {
				vx = rgen.nextDouble(1.0, 3.1);
			//	vx = vx;
			} else {
				vx = rgen.nextDouble(1.0, 3.1);
				vx = -vx;
			}
				
			vy = -vy;
			bounceClip.play();
			
		} else if(object == score || object == lifeDisplay) {
			
		} else {
			scoreCount(object);
			remove(object);
			if(rgen.nextBoolean(0.5)) vx = -vx;
			vy = -vy;
			brickCount--;	
			bounceClip.play();
		}
		
		
		
		if(vy < 17.0 && vy > -17.0){
			vy = 1.02 * vy;
		}	
	}
	
	private void scoreCount(GObject object) {
		if(object.getColor() == Color.CYAN ) {
			
			totalScore += 10;
			
		} else if(object.getColor() == Color.GREEN ) {
			
			totalScore += 20;
			
		} else if(object.getColor() == Color.YELLOW ) {
			
			totalScore += 30;
			
		} else if(object.getColor() == Color.ORANGE ) {
			
			totalScore += 40;
			
		} else if(object.getColor() == Color.RED ) {
			
			totalScore += 50;		
		}
		//remove(score);
		
	}
	
	private void displayScore() {
		
		if(score != null) remove(score);
		
		score = new GLabel(" Score : " + String.valueOf(totalScore));
		score.setFont("SansSerif-14");
		score.setLocation(10, HEIGHT - 25);	
		add(score);
		
		
	}
	
	private void displaylife() {
		
		if(lifeDisplay != null) remove(lifeDisplay);
		
		lifeDisplay = new GLabel(" Life : " + String.valueOf(lives - 1 ));
		lifeDisplay.setFont("SansSerif-14");
		lifeDisplay.setLocation(10, HEIGHT - 40);	
		add(lifeDisplay);
		
		
	}
	
	private void gameWon() {
		double xPoint = (APPLICATION_WIDTH - 150) / 2;
		double yPoint = (APPLICATION_HEIGHT - 50) / 2;
		
		
		GRect rect = new GRect(xPoint, yPoint, 150, 50);
		rect.setFilled(true);
		rect.setFillColor(Color.GREEN);
		
		GLabel label = new GLabel("YOU WON!!!" );
		label.setFont("SansSerif-18");
		label.setLocation( xPoint + (150 - label.getWidth()) / 2, yPoint + (50 + label.getAscent()) / 2 );
		
		add(rect);
		add(label);
		
	}
	
	private void gameOver() {
		double xPoint = (APPLICATION_WIDTH - 150) / 2;
		double yPoint = (APPLICATION_HEIGHT - 50) / 2;
		
		GRect rect = new GRect(xPoint, yPoint, 150, 50);
		rect.setFilled(true);
		rect.setFillColor(Color.RED);
		
		GLabel label = new GLabel("Game Over!!!");
		label.setFont("SansSerif-18");
		label.setLocation( xPoint + (150 - label.getWidth()) / 2, yPoint + (50 + label.getAscent()) / 2 );
		
		add(rect);
		add(label);
		
	}
	

	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int lives;
	private int brickCount;
	private AudioClip bounceClip;
	private double vx, vy;
	private GOval ball;
	private GRect brick;
	private GRect paddle;
	private GLabel score = null;
	private GLabel lifeDisplay = null;
	private int totalScore = 0;
}

