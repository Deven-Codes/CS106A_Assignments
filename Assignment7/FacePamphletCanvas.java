/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.program.*;
import acm.graphics.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants, ComponentListener {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
		message = "";
		currentProfile = null;
		column1 = LEFT_MARGIN;
		nameY = 0;
		imageY = 0;
		statusY = 0;
		dialougeY = 0;
		dialouge = null;
		System.out.println("Constructor");
		
	}

	public void setCanvas(FacePamphletProfile profile) {
		message = "Displaying " + profile.getName();
		currentProfile = profile;
		update();
		System.out.println("set canvas");
	}
	
	public void update() {
		
		removeAll();
		if(!message.equals("")) {
			showMessage();
		}
		if(currentProfile != null) {
			displayProfile();
		}
		System.out.println("update");
	}
	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage() {
		// You fill this in

		GLabel label = new GLabel(message);
		double x = ((getWidth() - label.getWidth()) / 2);
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		label.setFont(MESSAGE_FONT);
		label.setLocation(x, y);
		add(label);
		
		System.out.println("show message");
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile() {
		// You fill this in
		displayName();
		displayImage();
		displayStatus();
		displayFriends();
	}
	
	private void displayName() {
		GLabel name = new GLabel(currentProfile.getName());
		
		nameY = TOP_MARGIN + name.getAscent();
		
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.BLUE);
		name.setLocation(column1, nameY);
		
		add(name);
		System.out.println("display name");
	}
	
	private void displayImage() {
		imageY = nameY + IMAGE_MARGIN;
		
		double x1 = column1;
		double y1 = imageY;
		double x2 = column1 + IMAGE_WIDTH;
		double y2 = imageY + IMAGE_HEIGHT;
		
		if(currentProfile.getImage() == null) {
			GRect rect = new GRect(x1, y1, x2, y2);
			rect.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			GLabel label = new GLabel("No Image");
			label.setFont(PROFILE_IMAGE_FONT);

			double labelX = (x2 - label.getWidth()) / 2;
			double labelY = nameY + ((y2 ) / 2);
						
			label.setLocation(labelX, labelY);
			
			add(rect);
			add(label);
		} else {
			GImage image = currentProfile.getImage();
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			image.setLocation(x1, y1);
			add(image);
		}
		System.out.println("display image");
	}
	
	private void displayStatus() {
		if(!currentProfile.getStatus().equals("")) {
			double statusY = imageY + IMAGE_HEIGHT + STATUS_MARGIN;
			
			String status = currentProfile.getStatus();
			
			GLabel label = new GLabel(status);
			label.setFont(PROFILE_STATUS_FONT);
			
			double y = statusY + label.getAscent();
			label.setLocation(column1, y);
			add(label);
			System.out.println("display status");
		}
	}
	
	private void displayFriends() {

		
		GLabel friendLabel = new GLabel("Friends:");
		friendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		
		double column2 = (getWidth() / 2);
		double y = nameY + friendLabel.getAscent();
	
		friendLabel.setLocation(column2, y);	
		add(friendLabel);
		
		if(currentProfile.hasFriends()) {
			Iterator<String> it = currentProfile.getFriends();
			while(it.hasNext()) {
				String name = it.next();
				GLabel friend = new GLabel(name);
				friend.setFont(PROFILE_FRIEND_FONT);
				
				y = y + friend.getAscent() + 8;
				friend.setLocation(column2, y);
				
				add(friend);
			}
		}
		System.out.println("display friends");
	}
	
	public void dialouge(String line) {
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		dialougeY = y -(2 * 18);
		dialouge = new GLabel(line);
		dialouge.setFont(PROFILE_FRIEND_FONT);
		dialouge.setLocation(column1, dialougeY);
		dialouge.setVisible(true);
		add(dialouge);
	
	}
	
	public void deleteDialouge() {
		remove(dialouge);
	}

	public void invalidInput(String line) {
		removeAll();
		GLabel label = new GLabel(line);
		label.setFont(PROFILE_IMAGE_FONT);
		double x = (getWidth() - label.getWidth()) / 2;
		double y = (getHeight() + label.getAscent()) / 2;
		label.setLocation(x, y);
		add(label);
		System.out.println("invalid input2");
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }

	/* private instance variable */
	private String message;
	private FacePamphletProfile currentProfile;
	private double column1;
	private double nameY;
	private double imageY;
	private double statusY;
	private double dialougeY;
	private GLabel dialouge;
}
