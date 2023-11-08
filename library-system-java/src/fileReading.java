/**

A class for reading files and storing data as lists and arrays.
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class fileReading {

public int inputSize=0;

/**
 * An ArrayList of String arrays containing the data from the file.
 */
public List<String[]> comArr = new ArrayList<>();


public List<String> stringList;


public ArrayList<ArrayList<String[]>> inputList;



public  void readFile(String path) throws IOException{
	
	BufferedReader br = new BufferedReader(new FileReader(path));
	try {
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();

	    while (line != null ) {
	        if (line.isEmpty()) {
		        line = br.readLine();   
	        }
	        else {
	        	sb.append(line);
		        sb.append("\n");
		        line = br.readLine();
	        }	
	    }
	    
	    String[] books = sb.toString().split("\n");
	    
	    for (int i = 0; i < books.length; i++) {
	    	comArr.add(books[i].split("\t"));	
		}
	    
	} finally {
	    br.close();
	}
}
}