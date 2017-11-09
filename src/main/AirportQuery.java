package main;
import java.util.ArrayList;
/**
 * Created by melis on 10/5/2017.
 */
public class AirportQuery implements IQuery
{
	public ArrayList<Object> processData(String[] a)
	{
		String airport = a[0] ;
		RouteNetwork rn = RouteNetwork.getInstance() ;
		ArrayList<Object> output = new ArrayList<Object>();
		output.add(rn.getAirport(airport));
		return output;
	}
}
