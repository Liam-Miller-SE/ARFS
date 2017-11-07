import java.util.ArrayList;
//package ARFS;
/**
 * Created by melis on 10/5/2017.
 */
public class AirportQuery implements IQuery
{
	public ArrayList<Airport> processData(String[] a)
	{
		String airport = a[0] ;
		RouteNetwork rn = RouteNetwork.getInstance() ;
		ArrayList<Airport> output = new ArrayList<Airport>();
		output.add(rn.getAirport(airport));
		return output;
	}
}
