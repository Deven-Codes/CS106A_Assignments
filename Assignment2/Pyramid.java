/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	
	float start;
	float bottom;
	int row = BRICKS_IN_BASE ; 
	int column = 1;
	
	public void run() {
		/* You fill this in. */
		while(column != (BRICKS_IN_BASE+1)){
			floor();			
		}
		
	}
	
	/*
	 * floor() will be called for every new floor.
	 * This method will recalculate the starting point and bottom of the floor.
	 */
	
	private void floor(){
		float widthWindow = getWidth();
		float heightWindow = getHeight();
		start = (widthWindow - (row * BRICK_WIDTH)) / 2;
		bottom = (heightWindow - (BRICK_HEIGHT * column));
		builder();
		column++;
		row--;
	}
	
	/*
	 * builder() method will construct rows of the pyramid from the bottom in decending order to the top.
	 */
	
	private void builder(){
		
		for(int i=0; i<row; i++){
			GRect brick = new GRect(30, 12);
			brick.setLocation(start, bottom);
			add(brick);
			start = start + 30;
		}
	}
}



