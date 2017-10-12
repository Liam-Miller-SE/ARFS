public class Airport 
{
	private int code;
	private int[] temperature;
	private String[] weather; 
	private String city; 
	
	public Airport(int co, int[] temp, String[] weath, String cit)
	{
		code = co; 
		temperature = temp;
		weather = weath;
		city = cit;
	}
	public int getCode()
	{
		return this.code; 
	}
	public int[] getTemp()
	{
		return this.temperature;
	}
	public String[] getWeather()
	{
		return this.weather;
	}
	public String getCity()
	{
		return this.city;
	}
}
