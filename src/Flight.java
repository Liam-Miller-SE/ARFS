import java.time.*;

public class Flight 
{
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

  public Airport GetOrigin()
  {
    return origin;
  }
  public Airport GetDestination()
  {
    return destination;
  }
}
