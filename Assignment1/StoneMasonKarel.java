/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	// You fill in this part
	public void run() {
		int i = 0;
		while(i < 1) {
			repair();
			check();
			if(frontIsClear()){
				jump();
			} else{
				i = 1;
			}
						
		}
	}
	
	
	private void repair() {
		turnLeft();
		while(frontIsClear()) {
			if(beepersPresent()){
				move();
			} else{
				putBeeper();
				move();
			}
		}
		turnAround();			 
	}

	private void check(){
		while(frontIsClear()) {
			if(beepersPresent()){
				move();
			} else{
				putBeeper();
				move();
			}
		}
		turnLeft();
	}



	private void jump() {
		for (int j=0; j<4;j++){
			move();
		}
		if(beepersPresent()){
			
		} else{
			putBeeper();
		}
	}

//private void repairTheQuad() {
//	if(rightIsBlocked()){
//		turnLeft();
//		while(frontIsClear()) {
//			if(beepersPresent()){
//				move();
//			} else{
//				putBeeper();
//				move();
//			}
//		}
//		turnRight();
//	} else{
//		turnRight();
//		while(frontIsClear()) {
//			if(beepersPresent()){
//				move();
//			} else{
//				putBeeper();
//				move();
//			}
//		}
//		turnLeft();
//	}
//}

}


	
	
	
	