

public class Main {
	public static void main(String[] args) {
		system system=new system();
		system.commandHandling(args[0]);
		fileWriting writer=new fileWriting();
		writer.fileWriter(system.output,args[1]);
	}
}
