public class HQuery
{
	public HQuery
	{
	}
	public String query(String[] query)
	{
		if( query[0].equals("info"))
		{
			ItineraryQuery iq = new ItineraryQuery() ;
			return iq.processData(query[1:]) ;
		}
		else if( query[0].equals("retrieve"))
		{
			ReservationQuery rq = new ReservationQuery() ;
			return rq.processData(query[1:]) ;
		}
		else if( query[0].equals("airport"))
		{
			AirportQuery aq = new AirportQuery() ;
			return aq.processData(query[1:]) ;
		}
	}
}
