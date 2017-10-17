/**
 * Created by melis on 10/5/2017.
 */
public class AirportQuery implements IQuery
{
	public String processData(String[] a)
	{
		airport = a[0] ;
		return getData(airport) ;
	}
}
