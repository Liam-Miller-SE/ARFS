//package ARFS;

import java.util.ArrayList;

/**
 * Created by melis on 10/5/2017.
 */
public class Scheduler
{
	RouteNetwork rn;
	public Scheduler()
	{
		rn = RouteNetwork.getInstance();
	}


	/**
	 * Make Reservation- Take in a reservation
	 * search through the current reservations for the current passenger
	 * check to make sure the origin and destination are different
	 * return 1 - error
	 * 		  0 - success
	 */
	public int makeReservation(Itinerary i, String name)
	{
		Reservation r = new Reservation(name, i);
		//go through current passenger reservations
		ReservationQuery rQuery = new ReservationQuery();
		String[] queryInfo = {name, i.getOrigin().getCode(), i.getDestination().getCode()};
		if (rQuery.processData(queryInfo).equals(""))
		{
			rn.storeReservation(r);
			return 0;
		}
		else
		{
			return 1;
		}


	}


	/**
	 * Delete Reservation - take in a reservation and remove it from the
	 * RouteNetwork sends a success message
	 */
	public int deleteReservation(String name, Airport org, Airport dest)
	{
		int error = 1;

		ArrayList<Reservation> rdb = rn.getReservations();
		for(Reservation r : rdb)
		{
			if(r.getPassenger().equals(name) &&
					r.getItinerary().getOrigin().getCode().equals(org.getCode())
					&& r.getItinerary().getDestination().getCode().equals(dest.getCode()))
			{
				rn.deleteReservation(r);
				error = 0;
			}
		}
		return error;

	}

}
