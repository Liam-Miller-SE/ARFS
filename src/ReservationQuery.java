/**
 * Created by melis on 10/5/2017.
 */
public class ReservationQuery implements IQuery
{
	public String processData(String[] query)
	{
		String pass = query[0];
		RouteNetwork rn = RouteNetwork.getInstance() ;
		List<Reservation> r = rn.getReservations();
		List<Reservation> pmatch = new ArrayList<Reservation>();
		for(int i = 0; i < r.size(); i++)
		{
			if(r.get(i).getPassenger() == pass)
			{
				pmatch.add(r.get(i));
			}
		}
		/**if (query.length == 1)
		{
			
			
		}*/
		//how to query by length of query passed in
		return null;
	}
}
