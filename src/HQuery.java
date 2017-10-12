public class HQuery
{
	public HQuery
	{
	}
	public String query(String[] query)
	{
		if( query[0] == "Reservation")
		{
			ReservationQuery rq = new ReservationQuery() ;
			return rq.getData(query[1:]) ;
		}
		else if( query[0] == "Airport")
		{
			AirportQuery aq = new AirportQuery() ;
			return aq.getData(query[1:]) ;
		}
		else if( query[0] == "Flight")
		{
			FlightQuery fq = new FlightQuery() ;
			return fq.getData(query[1:]) ;
		}
		else if( query[0] == "Itinerary")
		{
			ItineraryQuery iq = new ItineraryQuery() ;
			return iq.getData(query[1:]) ;
		}
	}
}
