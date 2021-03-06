package main;
import java.util.ArrayList;
import java.time.Duration;
/**
 * Created by melis on 10/5/2017.
 */
public class ItineraryQuery implements IQuery
{
	public ArrayList<Object> processData(String[] query)
	{
		//query is defined as [origin, destination, {connections, sort-order}]
		RouteNetwork rn = RouteNetwork.getInstance();
		Airport orig = rn.getAirport(query[0]);
		Airport dest = rn.getAirport(query[1]);
		if (orig == null || dest == null)
		{
			return new ArrayList<Object>();
		}
		Itinerary i = new Itinerary(orig, dest, "ItineraryQuery");
		ArrayList<Object> itins = new ArrayList<Object>();
		int hops = Itinerary.MAXIMUM_TRANSFERS;
		String sortMethod = "UNSORTED";
		if(query.length == 3)
		{
			try
			{
				hops = Integer.parseInt(query[2]);
			}
			catch(Exception ex)
			{
				sortMethod = query[2];
			}
		}
		else if (query.length > 3)
		{
			try
			{
				hops = Integer.parseInt(query[2]);
			}
			catch(Exception ex){}
			sortMethod = query[3];
		}
		itins.addAll(createItineraries(i, dest, hops));
		switch (sortMethod)
		{
			case ("DEPARTURE"):
				itins.sort( (a, b) -> (((Itinerary)a).getFirstDeparture().compareTo(((Itinerary)b).getFirstDeparture())));
				break;
            case ("AIRFARE"):
                itins.sort( (a, b) -> (((Itinerary)a).getAirfare().compareTo(((Itinerary)b).getAirfare())));
                break;
		}
		return itins;

	}
    private ArrayList<Itinerary> createItineraries(Itinerary itin, Airport destination)
    {
      return createItineraries(itin, destination, Itinerary.MAXIMUM_TRANSFERS);
    }

    private ArrayList<Itinerary> createItineraries(Itinerary itin, Airport destination, int hopsLeft)
    {
      RouteNetwork rn = RouteNetwork.getInstance();
      ArrayList<Flight> flights = itin.getFlights();
      ArrayList<Airport> visited = new ArrayList<Airport>();
      visited.add(itin.getOrigin());
      Airport location;
      if(flights.size() > 0)
      {
        location = flights.get(flights.size() - 1).getDestination();
      }
      else
      {
        location = itin.getOrigin();
      }
      for(Flight f : flights)
      {
        visited.add(f.getDestination());
      }
      //Mooooom, are we there yet?
      if (location.getCode().equals(destination.getCode()))
      {
        ArrayList<Itinerary> out = new ArrayList<Itinerary>();
        out.add(itin);
        return out;
      }
      //See if we've reached our last hop
      if (hopsLeft <= 0)
      {
        return new ArrayList<Itinerary>();
      }
      else
      {
        ArrayList<Itinerary> itins = new ArrayList<Itinerary>();
        for(Flight f : location.getFlightsFromHere())
        {
          if (!visited.contains(f.getDestination()) && itin.getNextAvailibleTime().isBefore(f.getDeparture()))
          {
            Itinerary newItin = new Itinerary(itin);
            newItin.addFlight(f);
            itins.addAll(createItineraries(newItin, destination, hopsLeft - 1));
          }
        }
        return itins;
      }
    }
}
