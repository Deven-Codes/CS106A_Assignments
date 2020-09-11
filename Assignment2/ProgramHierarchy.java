/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {	
	
	private static final double WIDTH = 150;
	
	private static final double HEIGHT = 50;
	

	
	
	public void run() {
		
		double width = getWidth();
		
		double height = getHeight();
		
		rectBuild( width, height);
		
		labelBuild( width, height);
		
		lineBuild( width, height);

		/* You fill this in.*/    

	}
	
	private void rectBuild (double width, double height) {
		
		double mainWidth = (width-WIDTH)/2;
		
		double rowHeight1 = (height/2 - 2*HEIGHT)- 25;
		
		double rowHeight2 = (height - HEIGHT)/2 + 25;
		
		
		GRect rect1 = new GRect( mainWidth, rowHeight1, WIDTH, HEIGHT);
	       
	    GRect rect2 = new GRect( mainWidth - WIDTH -25, rowHeight2, WIDTH, HEIGHT);
	      
	    GRect rect3 = new GRect( mainWidth  , rowHeight2, WIDTH, HEIGHT);
	       
	    GRect rect4 = new GRect( mainWidth + WIDTH + 25 , rowHeight2, WIDTH, HEIGHT);	
	    
	    add(rect1);
	    
	    add(rect2);
	    
	    add(rect3);
	    
	    add(rect4);
		
	}
	
	private void labelBuild(double width, double height) {
		
		double mainWidth = (width-WIDTH)/2;
		
		double rowHeight1 = (height/2 - 2*HEIGHT)- 25;
		
		double rowHeight2 = (height - HEIGHT)/2 + 25;
		
		int num = 2222;
		
	    GLabel label1 = new GLabel ( String.valueOf(num) );
	    
	    label1.setLocation(mainWidth + (WIDTH - label1.getWidth())/2, rowHeight1 + (HEIGHT + label1.getHeight())/2);
	    
	    GLabel label2 = new GLabel ( "GraphicsProgram" );
	    
	    label2.setLocation(mainWidth - WIDTH -25  + ((WIDTH - label2.getWidth())/2) ,
	    		rowHeight2  + (HEIGHT + label2.getHeight())/2);
	    
	    GLabel label3 = new GLabel ( "ConsoleProgram" );
	    
	    label3.setLocation(mainWidth + ((WIDTH - label3.getWidth())/2) ,
	    		rowHeight2  + (HEIGHT + label3.getHeight())/2);
	   
	    GLabel label4 = new GLabel ( "DialogProgram" );
	    
	    label4.setLocation(mainWidth + WIDTH + 35 + ((WIDTH - label4.getWidth())/2) ,
	    		rowHeight2  + (HEIGHT + label4.getHeight())/2);
		
	    add(label1);
	    
	    add(label2);
	    
	    add(label3);
	    
	    add(label4);
	}
	
	private void lineBuild( double width, double height) {
		
	    double x1 = (width-WIDTH)/2 + 75;
	    double y1 = (height/2 - 2*HEIGHT) + 25; 
	    
	    double x2 = (width-WIDTH)/2 - WIDTH + 25;
	    double y2 = (height - HEIGHT)/2 + 25;
	    
	    double x3 = x1;
	    double y3 = y2;
	    
	    double x4 = (width-WIDTH)/2 + WIDTH + 75;
	    double y4 = y2;
	    
	    GLine line1 = new GLine ( x1, y1, x2, y2 );
	    
	    GLine line2 = new GLine ( x1, y1, x3, y3 );
	    
	    GLine line3 = new GLine ( x1, y1, x4, y4 );
	    
	    add(line1);
	    
	    add(line2);
	    
	    add(line3);
		
	}

}
