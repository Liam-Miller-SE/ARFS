public class Airport 
{
	private int code;
	private int temperature;
	private String weather; 
	private String city; 
	
	public Airport(int co, int temp, String weath, String cit)
	{
		code = co; 
		temperature = temp;
		weather = weath;
		city = cit;
	}
	public getCode()
	{
		return this.code; 
	}
	public getTemp()
	{
		return this.temperature;
	}
	public getWeather()
	{
		return this.weather;
	}
	public getCity()
	{
		return this.city;
	}
}
