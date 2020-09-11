/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		// You fill this in //
		tokens(line);
		
	}
	
	private void tokens(String entryLine) {
		int nameEnd = entryLine.indexOf(" ");
		name = entryLine.substring(0, nameEnd);
		
		String rankString = entryLine.substring(nameEnd);
		StringTokenizer tokenizer = new StringTokenizer(rankString);
		
		for(int count = 0; tokenizer.hasMoreTokens(); count++) {
				int tokenRank = Integer.parseInt(tokenizer.nextToken());
				ranks.add(tokenRank);
		}
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		// You need to turn this stub into a real implementation //
		if(name != null) {
			return name;
		}else {
			return null;
		}
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		// You need to turn this stub into a real implementation //
		int index = (decade - START_DECADE) /10;
		return ranks.get(index);
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		// You need to turn this stub into a real implementation //
		String result = name + " [";
		for(int i = 0; i<ranks.size(); i++) {
			if(i == 10) {
				result += ranks.get(i) + "]";
			} else {
				result += ranks.get(i) + " ";
			}
		}
		
		
		return result;
	}
	
	/* private instance variable */
	private String name = null;
	private ArrayList <Integer> ranks = new ArrayList<Integer>();
}

