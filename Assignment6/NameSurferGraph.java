/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */
import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
//		nameSurferEntries = new ArrayList<NameSurferEntry>();
		addComponentListener(this);
		//	 You fill in the rest //
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		//	 You fill this in //
		nameSurferEntries.clear();
		update();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
		if(!nameSurferEntries.contains(entry))	nameSurferEntries.add(entry);
		update();
	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		//	 You fill this in //
		removeAll();
		backGround();
		if(!nameSurferEntries.isEmpty()){
			graph();
		}
	
	}
	
	/*
	 * This function will draw the background graph for the Name Surfer.
	 * And will be called every time resizing of program window occurs.
	 */
	private void backGround() {
		drawVertical();
		drawHorizontal();
		printDecades();
	}
	
	/* This function will draw the vertical lines of the graph */
	private void drawVertical() {
		double width = getWidth() / NDECADES;
		double x = 0;
		double y1 = 0;
		double y2 = getHeight();
		
		for(int i = 0; i <= NDECADES; i++){
			GLine line = new GLine(x, y1, x, y2);
			add(line);
			x += width;
		}
	}
	
	/* This function will draw the horizontal lines of the graph */
	private void drawHorizontal() {
		double x1 = 0;
		double x2 = getWidth();
		double y1 = GRAPH_MARGIN_SIZE;
		double y2 = getHeight() - GRAPH_MARGIN_SIZE;
		
		GLine topLine = new GLine(x1, y1, x2, y1 );
		GLine bottomLine = new GLine(x1, y2, x2, y2);
		
		add(topLine);
		add(bottomLine);
		
	}
	
	/* This function will print the decades intertval on x axis of the graph */
	private void printDecades() {
		int decade = START_DECADE;
		int num = 10;
		double width = (getWidth() / NDECADES);
		
		double rightMargin = 0.10 * width;
		double topMargin = getHeight() - 2;
		
		for(int i = 0; i < NDECADES; i++) {
			String text = Integer.toString(decade);
			GLabel label = new GLabel(text);
			label.setLocation(rightMargin, topMargin);
				
			add(label);
			decade += num;
			rightMargin += width;
		}
	}
	
	private void graph() {
		for(int i = 0; i < nameSurferEntries.size() ; i++) {
			if(nameSurferEntries.get(i) == null) break;
			plotLines(nameSurferEntries.get(i), i);
			plotNameAndRank(nameSurferEntries.get(i), i);
		}
	}
	
	
	private void plotLines(NameSurferEntry entry, int index) {
		int decade = START_DECADE;
		double width = getWidth() / NDECADES;
		int yearGap = 10; // decade = 10 years
		for(int i = 0; i < NDECADES - 1; i++) {
			int nextDecade = decade + yearGap; 
			
			int rank1 = entry.getRank(decade);
			int rank2 = entry.getRank(nextDecade);
			
			double x1 = width * i;
			double x2 = width * (i + 1);
			double y1 = yCoordinate(rank1);
			double y2 = yCoordinate(rank2);
			
			GLine line = drawLine(x1, y1, x2, y2, index);
			add(line);
			
			decade = nextDecade; 
		}
	}
	
	private void plotNameAndRank(NameSurferEntry entry, int index) {
		int decade = START_DECADE;
		double width = getWidth() / NDECADES;
		int yearGap = 10; // decade = 10 years
		String name = entry.getName();
		
		for(int i = 0; i < NDECADES; i++) {
			int rank = entry.getRank(decade);	
			double x = width * i;
			double y = yCoordinate(rank);
			
			GLabel label = nameAndRank(x, y, index, name, rank);
			add(label);
			decade = decade + yearGap;
		}
	}
	
	private double yCoordinate(int rank) {
		double height = getHeight() - (2 * GRAPH_MARGIN_SIZE);
		double y = 0;
		if(rank != 0) {
			y = GRAPH_MARGIN_SIZE + (height * rank) / MAX_RANK;
		} else {
			y = GRAPH_MARGIN_SIZE + height;
		}
		return y;
	}
	
	private GLine drawLine(double x1, double y1, double x2, double y2, int index) {
		GLine line = new GLine(x1, y1, x2, y2);
		if(index % 4 == 0) {
			line.setColor(Color.MAGENTA);
		} else if(index % 3 == 0) {
			line.setColor(Color.GREEN);
		} else if(index % 2 == 0) {
			line.setColor(Color.BLUE);
		} else {
			line.setColor(Color.BLACK);
		}
		
		return line;
	}
	
	private GLabel nameAndRank(double x, double y, int index, String name, int rank) {
		String text = null;
		if(rank == 0) {
			text = name + " *";
		} else {
			text = name + " " + Integer.toString(rank);
		}
		GLabel label = new GLabel(text);
		double xCO = x + 2;
		double yCO = y - 2; 
		label.setLocation(xCO, yCO);
		if(index % 4 == 0) {
			label.setColor(Color.MAGENTA);
		} else if(index % 3 == 0) {
			label.setColor(Color.GREEN);
		} else if(index % 2 == 0) {
			label.setColor(Color.BLUE);
		} else {
			label.setColor(Color.BLACK);
		}
		return label;
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }

	/* private instance variable */
	private ArrayList<NameSurferEntry> nameSurferEntries = new ArrayList<NameSurferEntry>();

}

