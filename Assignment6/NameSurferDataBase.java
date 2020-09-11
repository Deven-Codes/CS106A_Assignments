///*
 /* File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */


	public NameSurferDataBase(String filename) {
		// You fill this in //
		BufferedReader rd = openFile(filename);
		try{
			while(true) {
				String line = rd.readLine();
				if(line == null) break;
				database(line);
			}
		} catch(IOException ex) {
			throw new ErrorException(ex);
		}
		
//		display();
	}	
	
	private BufferedReader openFile(String filename) {
		BufferedReader rd = null;
		while(rd == null) {
			try{
				rd = new BufferedReader(new FileReader(filename));
			} catch(IOException ex) {
				System.out.println("File not Found.");
			}
		}
		return rd;
	}
	
	private void database(String line) {
		int nameEnd = line.indexOf(" ");
		String name = line.substring(0, nameEnd);
		NameSurferEntry entry = new NameSurferEntry(line);
		nameDatabase.put(name, entry);
	}
		
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		// You need to turn this stub into a real implementation //
		char ch = name.charAt(0);
        if(Character.isLowerCase(ch) == true) {
            ch = Character.toUpperCase(ch);
        }
        String otherLetters = name.substring(1);
        otherLetters = otherLetters.toLowerCase();
        name = ch + otherLetters;
		if(nameDatabase.containsKey(name)) {
			return nameDatabase.get(name);
		} else {
			return null;
		}
	}
	
	private void display() {
		Iterator<String> it = nameDatabase.keySet().iterator();
		while(it.hasNext()) {
			String name = it.next();
			NameSurferEntry entry = nameDatabase.get(name);
			System.out.println(entry.toString());
		}
	}
	
	/* private instance variable */
	private HashMap<String,NameSurferEntry> nameDatabase = new HashMap<String,NameSurferEntry>();
}
