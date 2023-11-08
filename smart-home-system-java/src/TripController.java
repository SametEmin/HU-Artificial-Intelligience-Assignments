
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;





public class TripController  implements DepartureController,ArrivalController{
	public static String output="";
	public static int  length=0;
	public static int  num=0;
	
	public  TripController(String inp){
		
		

				
		
		
		for (int i = 0; i < readFile(inp).length; i++) {
			String[] line=readFile(inp)[i].split("\t");
			
			LocalTime time= LocalTime.of(Integer.parseInt(line[1].split(":")[0]),Integer.parseInt(line[1].split(":")[1]));
			
			
			TripSchedule.setTripSchedule(new Trip(line[0],time ,Integer.parseInt(line[2])));
			
			}
		}
		
		
	
		
	
	static String[] readFile(String path) {
		
		
		try {
			length= Files.readAllLines(Paths.get(path)).size();
			
			String[] lines =new String[length];
			int i=0;
			for (String line:Files.readAllLines(Paths.get(path))) {
				lines[i++]=line;
				
		} 
			return lines;
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	
	static void writer(String out) {
		
		
		try {
			FileWriter myWriter = new FileWriter(out);
			myWriter.write(output);
			
			myWriter.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}





	@Override
	public void ArrivalSchedule(TripSchedule tripschedule) {
		Trip[] trips=TripSchedule.getTripSchedule();
		LocalTime[] arrTime=new LocalTime[length];
		LocalTime tim=LocalTime.of(0, 19);
		
		for (int i = 0; i < length; i++) {
			trips[i].arrivalTime=trips[i].departureTime.plusMinutes(trips[i].Duration);
			arrTime[i]=trips[i].arrivalTime;
			
			for (int j = 0; j < length; j++) {
				if( i!=j && trips[i].arrivalTime.equals(trips[j].arrivalTime )) {
					trips[i].state="DELAYED";
					trips[j].state="DELAYED";
					
				}
			}
			
		}
		
		
		Sort.bubbleSort(arrTime, length);
		output+="Arrival order:\n";
		for (int i = 0; i < length; i++) {
			if (tim.compareTo(arrTime[i])==0) {
				continue;
			}
			
			for (int j = 0; j <length; j++) {
				
				if (arrTime[i].equals(trips[j].arrivalTime)) {
					
					
					
					
					output+=trips[j].tripname;
					output+=" arrive at ";
					trips[j].arrivalTime=trips[j].departureTime.plusMinutes(trips[j].Duration);
					output+=trips[j].arrivalTime+"   ";
					output+="Trip State:";output+=trips[j].getState()+"\n";
					
					 tim=arrTime[i];
				}
				
			}
		}	

		
	}



	@Override
	public void DepartureSchedule( TripSchedule tripschedule) {
		Trip[] trips=TripSchedule.getTripSchedule();
		LocalTime[] arrTime=new LocalTime[length];
		LocalTime tim=LocalTime.of(0, 19);
		
		
		for (int i = 0; i < length; i++) {
			arrTime[i]=trips[i].departureTime;
			for (int j = 0; j < length; j++) {
				if( i!=j && trips[i].departureTime.equals(trips[j].departureTime )) {
					trips[i].state="DELAYED";
					trips[j].state="DELAYED";
					
				}
			}
			
		}
		
		
		Sort.bubbleSort(arrTime, length);
		output+="Departure order:\n";
		for (int i = 0; i < length; i++) {
			if (tim.compareTo(arrTime[i])==0) {
				continue;
			}
			
			for (int j = 0; j <length; j++) {
				
				if (arrTime[i].equals(trips[j].departureTime)) {
					
					
					
					
					output+=trips[j].tripname;
					output+=" depart at ";
					output+=trips[j].departureTime+"   ";
					output+="Trip State:";output+=trips[j].getState()+"\n";
					
					 tim=arrTime[i];
				}
				
			}
		}	
		output+="\n";
				
		
		
		
	}
	
	
		
	
	
}

