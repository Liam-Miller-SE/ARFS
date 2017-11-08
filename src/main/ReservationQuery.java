//package ARFS;
import java.util.ArrayList;
/**
 * Created by melis on 10/5/2017.
 */
public class ReservationQuery implements IQuery
{
	public ArrayList<Reservation> processData(String[] query)
	{
		String ret = "";
		String pass = query[0];
		RouteNetwork rn = RouteNetwork.getInstance() ;
		ArrayList<Reservation> r = rn.getReservations();
		ArrayList<Reservation> pmatch = new ArrayList<Reservation>();
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
		//TODO move to TextUI
		/*
		for(int j = 0; j < pmatch.size(); j++)
		{
			ret += pmatch.get(j);
		}
		return ret;
		*/
	}
}
