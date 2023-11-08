
import java.time.LocalTime;

public class Trip {
	public String tripname;
	public LocalTime departureTime;
	public LocalTime arrivalTime;
	public int Duration;
	public String state="IDLE";
	
	public Trip(String tripname,LocalTime departureTime,int Duration) {
		this.tripname=tripname;
		this.departureTime=departureTime;
		this.Duration=Duration;
	}
	
	public LocalTime getDepartureTime() {
		return departureTime;
	}
	
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state=state;
	}
	public void calculateArrival() {
		
	}
}
