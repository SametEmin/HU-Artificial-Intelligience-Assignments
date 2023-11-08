

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	public static String output="";
	public static void main(String[] args) {
		if (args.length>0 && 2>args.length) {
			try {
				readFile(Paths.get(args[0]));
			} catch (IOException e) {
				output+="There should be an input file in the specified path\n";
			}
		}
		else {
			output+="There should be only 1 paremeter\n";
		}
		writeFile("output.txt",output);
	}
	public static void readFile(Path outPath) throws IOException {
		
		try {
			String contents = new String(Files.readAllBytes(outPath), StandardCharsets.UTF_8);
			boolean bool=true;
			if (contents.equals(null)) {
				output+="The input file should not be empty\n";
			}
			else {
				for (int i = 0; i < contents.length(); i++) {
					 if( Character.isLetter(contents.charAt(i))) {
						 continue;
						}
					 else {
						 output+="The input file should not contains unexpected characters\n";
						 bool=false;
						 break;
					}
				}
				if (bool) {
					output+=contents;
				}
				
			}
			
		} catch (FileNotFoundException e) {
			output+="There should be an input file in the specified path\n";
		}
		
		
		
	}
	public static void writeFile(String outPath,String output) {
			try {
				File file=new File(outPath);
				FileWriter writer=new FileWriter(outPath,true);
				BufferedWriter b = new BufferedWriter(writer);
				PrintWriter printWriter=new PrintWriter(b);
				printWriter.print(output);
				printWriter.close();
			} catch (IOException e) {
			
			}
	}
}
