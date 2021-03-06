package main;
public class Reservation 
{
	//class holding an itinerary, and the passengers name
  private Itinerary itinerary;
  private String passengerName;
  public Reservation(String name, Itinerary itin)
  {
    itinerary = itin;
    passengerName = name;
  }
  public Itinerary getItinerary()
  {
	  return this.itinerary;
  }
  public String getPassenger()
  {
	  return this.passengerName;
  }
  public String toString()
  {
	  return ("Passenger Name: " + this.getPassenger() +
	  "\n" + this.getItinerary());
  }
}
