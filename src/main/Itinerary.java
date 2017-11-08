package main;
import java.time.*;
import java.util.ArrayList;

public class Itinerary 
{
  String passengerName;
  ArrayList<Flight> flights;
  Airport origin;
  Airport destination;
  
  public Itinerary(String name)
  {
    passengerName = name;
  }
  public Itinerary(Airport orig, Airport dest, String name)
  {
    origin = orig;
    destination = dest;
    passengerName = name;
    flights = new ArrayList<Flight>();
  }
  public Itinerary(Itinerary copy)
  {
    this.passengerName = copy.passengerName;
    this.origin = copy.origin;
    this.destination = copy.destination;
    this.flights = new ArrayList<Flight>();
    for(Flight f : copy.flights)
    {
      this.flights.add(f);
    }
  }
  public ArrayList<Flight> getFlights()
  {
    return flights;
  }
  public String flightsString()
  {
	  String ret = "";
	  for(Integer i = 0; i<this.flights.size(); i++)
	  {
		  ret += this.flights.get(i);
	  }
	  return ret;
  }
  public Airport getOrigin()
  {
	  return this.origin;
  }
  public Airport getDestination()
  {
    return destination;
  }
  public void addFlight(Flight f)
  {
    flights.add(f);
    origin = flights.get(0).getOrigin();
    destination = flights.get(flights.size() - 1).getDestination();
  } 
  public LocalTime getNextAvailibleTime()
  {
    if (flights.size() == 0)
    {
      return null;
    }
    else
    {
      return flights.get(flights.size() - 1).getArrival();
    }
  }

  public LocalTime getFirstDeparture()
  {
    if(flights.size() <= 0)
    {
      return null;
    }
    else
    {
      return flights.get(0).getDeparture();
    }
  }
  public static int MAXIMUM_TRANSFERS = 3;
  
  public String toString()
  {
	  return ( "Origin: " + this.getOrigin() + "\nDestination: "
	  + this.getDestination() + "\n" + this.flightsString() + "\n");
  }
  public int getAirfare()
  {
    int out = 0;
    for (Flight f : flights)
    {
      out += f.getAirfare();
    }
    return out;
  }
  public String toOutputString()
  {
    String c = ",";
    String out = this.getAirfare()+c+this.flights.size()+"\n";
    int num = 0;
    for(Flight f : flights)
    {
      String depart = (f.getDeparture().getHour() % 12) + ":" + f.getDeparture().getMinute();
      String arrive = (f.getArrival().getHour() % 12) + ":" + f.getArrival().getMinute();
      out+=num++ +c+f.getFlightNumber()+c+f.getOrigin().getCode()+c+RouteNetwork.convertTime(f.getDeparture())+c+f.getDestination().getCode()+c+RouteNetwork.convertTime(f.getArrival()) + "\n";
    }
    return out;
  }
}
