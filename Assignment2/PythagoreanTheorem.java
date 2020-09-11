/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		/* You fill this in */
		int a;
		int b;
		double c;
		
		println("Enter values to compute Pythagorean Theorem.");		
		a = readInt("Enter a: ");
		b = readInt("Enter b: ");
		
		c = Math.pow(a,2) + Math.pow(b, 2);
		c = Math.sqrt(c);
		
		println("c: "+c);
	}
}
