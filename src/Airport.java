import java.util.ArrayList;

public class Airport 
{
	private String code;
	private int[] temperature;
	private int tempI;
	private int weathI; 
	private String[] weather; 
	private String city;
    private ArrayList<Flight> flights = new ArrayList<Flight>();
	private int connectionTime;
	private int delayTime;

	
	public Airport(String co, int[] temp, String[] weath, String cit)
	{
		code = co; 
		temperature = temp;
		weather = weath;
		city = cit;
		tempI = 0;
		weathI = 0;
	}
	public Airport(String co, String cit)
	{
		code = co;
		city = cit;
		tempI = 0;
		weathI = 0;
	}
	public String getCode()
	{
		return this.code; 
	}
	public int[] getTemp()
	{
		return this.temperature ;
	}
	public String getTemperature()
	{
		int tem = this.temperature[this.tempI] ;
		if(this.tempI == (this.temperature.length - 1))
		{
			this.tempI = 0;
		}
		else
		{
			this.tempI++;
		}
		String temp = Integer.toString(tem);
		return temp ;
	}
	public String[] getWeather()
	{
		return this.weather;
	}
	public String getWeath()
	{
		String weath = this.weather[this.weathI] ;
		if(this.weathI == (this.weather.length - 1))
		{
			this.weathI = 0;
		}
		else
		{
			this.weathI++;
		}
		return weath ;
	}
	public String getCity()
	{
		return this.city;
	}
	public String getDelays()
	{
		String delay = Integer.toString(this.delayTime) ;
		return delay ;
	}
	public String getConnections()
	{
		return Integer.toString(connectionTime);
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
	public void setConnection(int min)
	{
		this.connectionTime = min;
	}
	public void setDelay(int min)
	{
		this.delayTime = min;

	}

	public String toString() 
	{
		//return "an airport";
		return ("Airport Name: " + this.getCity() + " "
		+ this.getCode()	+	"\nCurrent Weather: "
		+ this.getWeath() + "\nCurrent Temperature: "
		+ this.getTemperature() + "\nDelays: " + this.getDelays() + "\n");
	}
}

