import java.util.ArrayList;

public class Airport 
{
	private String code;
	private int[] temperature;
	private String[] weather; 
	private String city;
        private ArrayList<Flight> flights; 
	
	public Airport(String co, int[] temp, String[] weath, String cit)
	{
		code = co; 
		temperature = temp;
		weather = weath;
		city = cit;
	}
	public Airport(String co, String cit)
	{
		code = co;
		city = cit;
	}
	public String getCode()
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

	public void setCode()
	{

	}
	public void setTemperature(int[] temps)
	{
		temperature = temps;
	}
	public void setWeather(String[] weaths)
	{
		weather = weaths;
	}
	public void setCity(String newCity)
	{
		city = newCity;
	}
	public void addFlight(Flight f)
	{
		flights.add(f);
	}
	public ArrayList<Flight> getFlights()
	{
		return flights;
	}
}

