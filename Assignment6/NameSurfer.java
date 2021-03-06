/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.util.*;

import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
	    // You fill this in, along with any helper methods //
		
		
		text = new JTextField(20);
		text.setActionCommand("Graph");
		text.addActionListener(this);

		graph = new NameSurferGraph();
		add(graph);
		
		add(new JLabel("Name"), SOUTH);
		add(text, SOUTH);
		add(new JButton("Graph"), SOUTH);
		add(new JButton("Clear"), SOUTH);
		
		nameDatabase = new NameSurferDataBase(NAMES_DATA_FILE);
				
		addActionListeners();
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in //
		String cmd = e.getActionCommand();
		
		if(cmd.equals("Graph")) {
			if(!text.getText().equals("")) {		
				checkName();
			}
		} else if(cmd.equals("Clear")) {
			graph.clear();

		}	
	}

	
	private void checkName() {
		NameSurferEntry entry = nameDatabase.findEntry(text.getText());
		if(entry != null) {
			graph.addEntry(entry);
		}
	}
	
	/* private instance variable */
	private JTextField text;
	private NameSurferDataBase nameDatabase;
	private NameSurferGraph graph;

}

