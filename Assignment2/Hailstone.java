/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	
	int number;
	int count = 0;
	
	public void run() {
		/* You fill this in */
		
		
		number = readInt("Enter the number: ");
		
		hail();
		
		println("The process took "+count+" steps to reach 1");
	}
	
	private void hail(){
		while(number>1){
			if(number%2 == 0){
				println(number+" is even, so we take half: "+(number/2));
				number = number/2;
				
			} else {
				println(number+" is odd, so we make 3n+1: "+(3*number+1));
				number = 3*number+1;
			}
			count++;
		}
	}
}

