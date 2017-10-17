import java.util.Arrays;
public class HQuery
{
	public HQuery()
	{
	}
	public String query(String[] query)
	{
		if( query[0].equals("info"))
		{
			ItineraryQuery iq = new ItineraryQuery() ;
			return iq.processData(Arrays.copyOfRange(query, 1, query.length)) ;
		}
		else if( query[0].equals("retrieve"))
		{
			ReservationQuery rq = new ReservationQuery() ;
			return rq.processData(Arrays.copyOfRange(query, 1, query.length)) ;
		}
		else if( query[0].equals("airport"))
		{
			AirportQuery aq = new AirportQuery() ;
			return aq.processData(Arrays.copyOfRange(query, 1, query.length)) ;
		}
                else
		{
			return null;
		}
	}
}
