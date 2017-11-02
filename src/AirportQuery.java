//package ARFS;
/**
 * Created by melis on 10/5/2017.
 */
public class AirportQuery implements IQuery
{
	/*
		query sent with string array of length 1
		returns all airport info from airport 
		code sent in
	*/
	public String processData(String[] a)
	{
		String airport = a[0] ;
		RouteNetwork rn = RouteNetwork.getInstance() ;
		return rn.getAirport(airport).toString() ;
	}
}
