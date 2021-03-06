package main;
import java.util.ArrayList;
/**
 * Created by melis on 10/5/2017.
 */
public class ReservationQuery implements IQuery
{
	public ArrayList<Object> processData(String[] query)
	{
		//if no match found, returns empty string
		//takes in request in form:
		//["passenger name", (optional)"origin", (optional)"destination"]
		//returns all reservations that match request
		String ret = "";
		String pass = query[0];
		RouteNetwork rn = RouteNetwork.getInstance() ;
		ArrayList<Reservation> r = rn.getReservations();
		ArrayList<Object> pmatch = new ArrayList<Object>();
		for(int i = 0; i < r.size(); i++)
		{
			if(r.get(i).getPassenger().equals(pass))
			{
				if (query.length == 1)
				{
					pmatch.add(r.get(i));
				}
				else if (query[1].equals( "" ) )
				{
					if (query[2].equals(r.get(i).getItinerary().getDestination().getCode()))
					{
						pmatch.add(r.get(i));
					}
				}
				else
				{
					if ((query[2].equals(r.get(i).getItinerary().getDestination().getCode()))
						&& query[1].equals(r.get(i).getItinerary().getOrigin().getCode()))
						{
							pmatch.add(r.get(i));
						}
				}
			}
		}
		return pmatch;
	}
}
