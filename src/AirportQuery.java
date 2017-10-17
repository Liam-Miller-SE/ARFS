/**
 * Created by melis on 10/5/2017.
 */
public class AirportQuery implements IQuery
{
	public String returnData()
	{
		return null;
	}
	public String getData(String airport)
	{
		return null;
	}
	public String processData(String[] a)
	{
		airport = a[0] ;
		return getData(airport) ;
	}
}
