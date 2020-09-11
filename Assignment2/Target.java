/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	public void run() {
		/* You fill this in. */
		int width = getWidth();
		int height = getHeight();
		
		
		width = width/2;
		height = height/2;
	
		
		GOval circle1 = new GOval(width-36,height-36,72,72);
		GOval circle2 = new GOval(width-23.4,height-23.4,46.8,46.8);
		GOval circle3 = new GOval(width-10.8,height-10.8,21.6,21.6);
		
		circle1.setFilled(true);
		circle1.setColor(Color.RED);
		
		circle2.setFilled(true);
		circle2.setColor(Color.WHITE);
		
		circle3.setFilled(true);
		circle3.setColor(Color.RED);
		
		add(circle1);
		add(circle2);
		add(circle3);
		
	}
}
