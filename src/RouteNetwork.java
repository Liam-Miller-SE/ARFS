import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.*;


public class RouteNetwork
{
    private HashMap routes = new HashMap();
    private List<Flight> flights = new ArrayList<Flight>();
    private List<Airport> airports = new ArrayList<Airport>();
    private List<Reservation> reservations = new ArrayList<Reservation>();
    private List<Itinerary> itineraries = new ArrayList<Itinerary>();


    public RouteNetwork()
    {

    }

    public void readInfo(File f)
    {
        String filename = f.getName();
        System.out.println(filename);
        try
        {
            BufferedReader fr = new BufferedReader(new FileReader(f));
            String line = fr.readLine();

            while(line != null)
            {
                String[] args = line.split(",");
                switch (filename){

                    case "airports.txt":
                        //code, city
                        Airport a = new Airport(args[0], args[1]);
                        storeAirport(a);
                        System.out.println(args[0] + " " + args[1]);

                        break;
                    case "connections.txt":
                        //airport, minutes

                        System.out.println(args[0] + " " + args[1]);
                        break;
                    case "delays.txt":
                        //airport, minutes
                        System.out.println(args[0] + " " + args[1] );
                        break;
                    case "flights.txt":
                        //orgin airport, destination airport, depart time, arrival time, flight num, airfare
                        Airport oa = getAirport(args[0]);
                        Airport da = getAirport(args[1]);
                        //Take in time as a HH:MM(a/p) needs to be converted to Local time object
                        LocalTime dTime = createTime(args[2]);
                        LocalTime aTime = createTime(args[3]);
                        int num = Integer.parseInt(args[4]);
                        double fare = Double.parseDouble(args[5]);
                        Flight fly = new Flight(oa, da, dTime, aTime, num, fare);
                        flights.add(fly);

                        System.out.println(args[0] + " " + args[1] + " " + args[2] + " " +
                                args[3] + " " + args[4] + " " + args[5]);
                        break;
                    case "weather.txt":
                        //input csv not always the same number of args
                        //use a for loop to split the weather and temp; the first
                        //index will be the airport, the even # indices will be
                        //weather, the odd # will be the temp
                        String aCode = args[0];

                        int i = 1;
                        String[] weat = new String[10];
                        int[] temp = new int[10];

                        int w =0;
                        int t = 0;
                        while(i < args.length)
                        {
                            if(i%2==0)
                            {
                               weat[w] = args[i];
                                w++;
                            }
                            else
                            {
                               temp[t] = Integer.parseInt(args[i]);
                                t++;
                            }
                            i++;
                        }
                        Airport weatA  = getAirport(aCode);
                        weatA.setTemperature(temp);
                        weatA.setWeather(weat);
                        break;
                    default:
                        System.out.println(line);
                        break;
                }
                    line = fr.readLine();
            }
            fr.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void storeFlight(Flight f)
    {

    }

    public void storeAirport(Airport a)
    {
        for (Airport adb : airports)
        {
            if(adb.getCode().equals(a.getCode()))
            {
                String codeDB = adb.getCode();
                String[] weatherDB = adb.getWeather();
                int[] tempDB = adb.getTemp();
                String cityDB = adb.getCity();

                String codeA = a.getCode();
                String[] weatherA = a.getWeather();
                int[] tempA = a.getTemp();
                String cityA = a.getCity();
                if(weatherDB != weatherA && weatherA != null)
                {
                    adb.setWeather(weatherA);
                }
                if(tempDB != tempA && tempA != null)
                {
                    adb.setTemperature(tempA);
                }
                if(!cityDB.equals(cityA) && cityA != null)
                {
                    adb.setCity(cityA);
                }


            }


        }
    }
    public Airport getAirport(String code)
    {
        for (Airport a: airports)
        {
            if(a.getCode().equals(code))
            {
                return  a;
            }
        }
        return null;

    }

    private static LocalTime createTime(String time)
    {
        String[] timeSlots = time.split(":");
        char military = timeSlots[0].charAt(2);
        int hour = Integer.parseInt(timeSlots[0]);
        int min = Integer.parseInt(timeSlots[1].substring(0,2));

        if(military == 'p')
        {
            hour += 12;

        }
        else
        {
            hour = hour;
        }

        return LocalTime.of(hour, min);

    }




}
