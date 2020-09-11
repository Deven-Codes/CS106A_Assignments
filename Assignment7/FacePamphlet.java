/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		// You fill this in
		addWest();
		addNorth();
		
		canvas = new FacePamphletCanvas();
		add(canvas);
		
		database = new FacePamphletDatabase();
		addActionListeners();
    }
    
	private void addWest() {
	
		status();
		picture();
		friend();
		

	}
	
	private void addNorth() {
		name = new JTextField(TEXT_FIELD_SIZE);
		name.setActionCommand("Lookup");
		name.addActionListener(this);
		
		add(new JLabel("Name"), NORTH);
		add(name, NORTH);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		
	}
	
	private void status() {
		status = new JTextField(TEXT_FIELD_SIZE);
		status.setActionCommand("Change Status");
		status.addActionListener(this);
		add(status, WEST);
		add(new JButton("Change Status"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

	}
	
	private void picture() {
		picture = new JTextField(TEXT_FIELD_SIZE);
		picture.setActionCommand("Change Picture");
		picture.addActionListener(this);
		add(picture, WEST);
		add(new JButton("Change Picture"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
	
	}
	
	private void friend() {
		addFriend = new JTextField(TEXT_FIELD_SIZE);
		addFriend.setActionCommand("Add Friend");
		addFriend.addActionListener(this);
		add(addFriend, WEST);
		add(new JButton("Add Friend"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

	}
	

  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		// You fill this in as well as add any additional methods
    	if(!name.getText().equals("")) {
    		profile = database.getProfile(name.getText());
    		String cmd = e.getActionCommand();
           	line = "";
        	if(profile == null) {
        		System.out.println("Profile : " + profile);
        		if(cmd.equals("Add")) {
        			add();
        			System.out.println("Add" );
                    canvas.setCanvas(profile);
                    canvas.dialouge(line);
        		} else {
        			System.out.println("invalid input1");
                	canvas.invalidInput("Profile do not Exist");
                }
        		
        	} else {
        		if(cmd.equals("Delete")) {
                	delete();
                	
                } else if(cmd.equals("Lookup")) {
                	lookup();
                } else if(cmd.equals("Change Status")) {
                	changeStatus();
                } else if(cmd.equals("Change Picture")) {
                	changePicture();
                } else if(cmd.equals("Add Friend")) {
                	addFriend();
                }
        		
        		if(database.isEmpty()) {
        			canvas.invalidInput("Database is Empty");
        		} else if(profile != null){
        			canvas.setCanvas(profile);
        			canvas.dialouge(line);
        		} else {
        			canvas.invalidInput("Face Pamphlet");
        		}
                
        	}

    	}
	}
  
    
    private void add() {
    	profile = new FacePamphletProfile(name.getText());
		System.out.println("Profile : " + profile);
    	if(database.isEmpty()){
    		database.addProfile(profile);
    		line = name.getText() + " added to the database";
    	} else {
    		if(!database.containsProfile(name.getText())) {
        		database.addProfile(profile);
        		line = name.getText() + " added to the database";
        	}
    	}

    }
    
    private void delete() {
    	
       	System.out.println("Profile : " + profile);
       	if(profile.hasFriends()) {
       		Iterator<String> it = profile.getFriends();
       		
           	while(it.hasNext()){
           		FacePamphletProfile localPro = database.getProfile(it.next());
           		localPro.removeFriend(name.getText());
           	}
       	}
       
       	database.deleteProfile(name.getText());
       	System.out.println("Profile deleted ");
       	canvas.invalidInput(name.getText() + " Deleted");
   		profile = null;
   		
    }
    
    private void lookup() {
    	if(!database.isEmpty()) {
    		if(database.containsProfile(name.getText())) {
        		profile = database.getProfile(name.getText());
        	} else {
        		line = name.getText() + " do no exist in the database";
        	}
    	}
    }
    
    private void changeStatus() {
    	profile.setStatus(status.getText());
    	line = "Status chnaged.";
    }
    
    private void changePicture() {
    	GImage image = null;
    	try{
    		image = new GImage(picture.getText());
    		profile.setImage(image);
    		line = "Profile photo changed.";
    	} catch(ErrorException ex) {
    		line = "Picture do not exist.";
    	}
    }
    
    private void addFriend() {
    	if(database.containsProfile(addFriend.getText())) {
    		if(profile.existFriend(addFriend.getText())) {
    			line = addFriend.getText() + " already is a friend.";
    		} else if(profile.getName().equals(addFriend.getText())) { 
    			line = "You can't add yourself as friend.";
    		} else {
    			profile.addFriend(addFriend.getText());
    			database.getProfile(addFriend.getText()).addFriend(profile.getName());
    			line = addFriend.getText() + " added as Friend.";
    		}
    	} else {
    		line = addFriend.getText() + " do no exist in the database";
    	}
    }
    
    private JTextField status;
    private JTextField picture;
    private JTextField addFriend;
    private JTextField name;
    private FacePamphletProfile profile = null ;
    private FacePamphletDatabase database;
    private FacePamphletCanvas canvas;
    private String line = "";
}
