//package ARFS;
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
	public void storeItinerary(Itinerary itin)
	{
		rn.storeItinerary(itin);
	}
}
