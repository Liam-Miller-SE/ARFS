public class Reservation 
{
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
}
