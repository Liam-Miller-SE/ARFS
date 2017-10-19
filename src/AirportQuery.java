package ARFS;
/**
 * Created by melis on 10/5/2017.
 */
public class AirportQuery implements IQuery
{
	public String processData(String[] a)
	{
		String airport = a[0] ;
		RouteNetwork rn = RouteNetwork.getInstance() ;
		return rn.getAirport(airport).toString() ;
	}
}
