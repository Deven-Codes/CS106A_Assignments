/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	int number;
	int smallest;
	int largest;
	
	public void run() {
		
		
		
		number = readInt("Enter number to find range or Enter '0' to quit: ");
		
		smallest = number;
		largest = number;
		
		Range();
		
		Print();
		
		
	}
	
	/*
	 * Range() will compare the entered number with smallest and largest number.
	 */
	private void Range(){
		while(number != 0){
			if(number > largest){
				largest = number;
			} else if(number < smallest){
				smallest = number;
			}
			number = readInt("Enter number to find range or Enter '0' to quit: ");
		}
	}
	
	
	/*
	 * Print() will print will the smallest and largest numbers entered by user.
	 */
	private void Print(){
		if(number == 0){
			println("Successfully Quit.");
			if((smallest != 0) && (largest != 0)){
				println("Largest: "+largest);
				println("Smallest: "+smallest);
				
			}
		}
	}
	
}

