//package ARFS;
import java.time.*;

public class Flight 
{
	//holds airports times, and flight numbers and airfare
  Airport origin;
  Airport destination;
  LocalTime departureTime;
  LocalTime arrivalTime;
  int flightNumber;
  double airfare;

  public Flight(Airport orig, Airport dest, LocalTime depart, LocalTime arrive, int num, double cost)
  {
    origin = orig;
    destination = dest;
    departureTime = depart;
    arrivalTime = arrive;
    flightNumber = num;
    airfare = cost;
  }

  public Duration calculateDelay()
  {
    //TODO: calculate delay
    return Duration.ZERO;
  }

  public Airport getOrigin()
  {
    return origin;
  }
  public Airport getDestination()
  {
    return destination;
  }
  public LocalTime getArrival()
  {
    return arrivalTime;
  }
  public LocalTime getDeparture()
  {
    return departureTime;
  }
  public int getFlightNumber()
  {
    return flightNumber;
  }
  public double getAirfare()
  {
    return airfare;
  }
  public String toString()
  {
	  return ("Flight Number: " + this.flightNumber + 
	  "\nAirfare: " + this.airfare + "\n");
  }
}
