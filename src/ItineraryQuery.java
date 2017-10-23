//package ARFS;
import java.util.ArrayList;
import java.time.Duration;
/**
 * Created by melis on 10/5/2017.
 */
public class ItineraryQuery implements IQuery
{
	public String processData(String[] query)
	{
		//query is defined as [origin, destination, {connections, sort-order}]
		RouteNetwork rn = RouteNetwork.getInstance();
		Airport orig = rn.getAirport(query[0]);
		Airport dest = rn.getAirport(query[1]);
		Itinerary i = new Itinerary(orig, dest, "ItineraryQuery");
		ArrayList<Itinerary> itins = new ArrayList<Itinerary>();
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
			hops = Integer.parseInt(query[2]);
			sortMethod = query[3];
		}
		itins = rn.createItineraries(i, dest, hops);
		switch (sortMethod)
		{
			case ("DEPARTURE"):	
				itins.sort( (a, b) -> (int)(Duration.between(a.getFirstDeparture(), b.getFirstDeparture()).toMillis()));
				break;
		}
		String output = "info,";//TODO:Number of itins to display
		int num = 0;
		for(Itinerary j : itins)
		{
			output +=num++ + "," +j.toOutputString() + "\n";
		}
		return output;
	}
}
