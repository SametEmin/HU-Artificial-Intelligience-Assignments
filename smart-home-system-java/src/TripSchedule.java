

public class TripSchedule {
	public static Trip[] trips=new Trip[100];
	public static int numberofTrips=0;
	
	public TripSchedule() {
		
	}
	
	public static Trip[] getTripSchedule() {
		return trips;
	}
	public static void setTripSchedule(Trip trip) {
		trips[numberofTrips++]=trip;
		
	}
	
	
}	
