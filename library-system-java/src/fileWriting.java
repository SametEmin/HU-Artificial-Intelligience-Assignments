
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class fileWriting {
	/**
	 * Function write the output txt onto output file.
	 */
	public void fileWriter(String output,String outPath) {
		try {
			File file=new File(outPath);
			FileWriter writer=new FileWriter(outPath);
			writer.write(output);
			writer.close();
		} catch (IOException e) {
		}
		
	}
}
