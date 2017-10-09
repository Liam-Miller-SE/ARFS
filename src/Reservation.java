public class Reservation 
{
  Itinerary itinerary;
  String passengerName;
  public Reservation(String name, Itinerary itin)
  {
    itinerary = itin;
    passengerName = name;
  }
}
