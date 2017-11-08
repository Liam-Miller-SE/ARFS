package main;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by melis on 10/5/2017.
 */
public class Scheduler
{


	private Stack<Pair> undo;
	private Stack<Pair> redo;
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
	public int deleteReservation(Integer cid, String name, Airport org, Airport dest)
	{
		int error = 1;

		ArrayList<Reservation> rdb = rn.getReservations();
		for(Reservation r : rdb)
		{
			if(r.getPassenger().equals(name) &&
					r.getItinerary().getOrigin().getCode().equals(org.getCode())
					&& r.getItinerary().getDestination().getCode().equals(dest.getCode()))
			{

				addElementUndo(cid, r);
				rn.deleteReservation(r);
				error = 0;
			}
		}
		return error;

	}

	public void addElementUndo(Integer id, Reservation r)
	{
		Pair<Integer, Reservation> ele = new Pair<>(id, r);
		undo.push(ele);
		removeIdElements(id);
	}

	public void removeIdElements(Integer id)
	{
		Iterator<Pair> itr = redo.iterator();
		int count =0;
		while(itr.hasNext())
		{
			Pair<Integer, Reservation> temp = itr.next();
			if(temp.getKey().equals(id))
			{
				redo.remove(count);
			}
			else{
				count++;
			}
		}
	}
	public void addElementRedo(Pair<Integer, Reservation> ele)
	{
		redo.push(ele);
	}

	public void undo(Integer id) {
		//clear redo stack before you undo an item

		Stack<Pair> temp = new Stack<>();

		boolean check = true;
		while(check)
		{
			Pair<Integer, Reservation> ele = undo.pop();
			if (ele.getKey().equals(id))
			{
				//See if the reservation is in the route network
				//if it is then remove it from rn else add it
				boolean inRN = false;
				for(Reservation r: rn.getReservations())
				{
					if(r.getPassenger().equals(ele.getValue().getPassenger()) &&
							r.getItinerary().getOrigin().getCode().equals(ele.getValue().getItinerary().getOrigin().getCode())
							&& r.getItinerary().getDestination().getCode().equals(ele.getValue().getItinerary().getDestination().getCode()))
					{
						rn.deleteReservation(r);
						inRN = true;
					}
				}
				if(!inRN)
				{
					rn.storeReservation(ele.getValue());
				}

				addElementRedo(ele);
				check = false;
			}
			else
			{
				temp.push(ele);
			}

		}
		while(temp.size() != 0)
		{
			undo.push(temp.pop());
		}
	}
	public void redo(Integer id)
	{
		Stack<Pair> temp = new Stack<>();
		Pair<Integer, Reservation> element = redo.pop();
		while(!element.getKey().equals(id) && redo.size() != 0)
		{
			temp.push(element);
			element = redo.pop();
		}
		if(redo.size()!=0)
		{
			//check reservations in routenetwork for this reservation
			boolean inRN = false;
			for(Reservation r : rn.getReservations())
			{
				if(r.getPassenger().equals(element.getValue().getPassenger())
						&& r.getItinerary().getOrigin().getCode().equals(element.getValue().getItinerary().getOrigin().getCode())
						&& r.getItinerary().getDestination().getCode().equals(element.getValue().getItinerary().getDestination().getCode()))
					{
						rn.deleteReservation(r);
						inRN = true;
					}

			}
			if(!inRN)
			{
				rn.storeReservation(element.getValue());
			}

		}
		while(temp.size() !=0)
		{
			redo.push(temp.pop());
		}

	}

}
