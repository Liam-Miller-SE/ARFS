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
		Itinerary i = new Itinerary(rn.getAirport(query[0]), rn.getAirport(query[1]), "ItineraryQuery");
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
		itins = rn.createItineraries(i, Integer.parseInt(query[2]));
		switch (sortMethod)
		{
			case ("DEPARTURE"):	
				itins.sort( (a, b) -> (int)(Duration.between(a.getFirstDeparture(), b.getFirstDeparture()).toMillis()));
				break;
		}
		return null;
	}
}
