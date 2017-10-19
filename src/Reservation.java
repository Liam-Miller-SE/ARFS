public class Reservation 
{
  private Itinerary itinerary;
  private String passengerName;
  public Reservation(String name, Itinerary itin)
  {
    itinerary = itin;
    passengerName = name;
  }
  public getItinerary()
  {
	  return this.itinerary;
  }
  public getPassenger()
  {
	  return this.passengerName;
  }
}
