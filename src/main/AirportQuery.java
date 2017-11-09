package main;
import java.util.ArrayList;

/**
 * Created by melis on 10/5/2017.
 */
public class AirportQuery implements IQuery
{
	//a: code, local/web
	public ArrayList<Object> processData(String[] a)
	{
		String localWeb;
		String airport = a[0];
		try {
			localWeb = a[1];
		}
		catch (Exception e)
		{
			localWeb = "local";
		}
		ArrayList<Object> output = new ArrayList<Object>();
		if(localWeb.equals("local")) {
			RouteNetwork rn = RouteNetwork.getInstance();
			output.add(rn.getAirport(airport));
		}
		else if(localWeb.equals("web"))
		{
			//calling the webService getAirport method

		}
			return output;
	}
}
