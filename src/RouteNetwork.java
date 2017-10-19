import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.time.*;


public class RouteNetwork
{
    private HashMap routes = new HashMap();
    private ArrayList<Flight> flights = new ArrayList<Flight>();
    private ArrayList<Airport> airports = new ArrayList<Airport>();
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    private ArrayList<Itinerary> itineraries = new ArrayList<Itinerary>();

    private RouteNetwork()
    {

    }
    private static class SingletonHolder
    {
        private static RouteNetwork INSTANCE = new RouteNetwork();
    }
    public static RouteNetwork getInstance()
    {
        return SingletonHolder.INSTANCE;
    }

    public ArrayList<Reservation> getReservations()
    {
        return reservations;
    }

    public void readInfo(File f)
    {
        String filename = f.getName();
        //System.out.println(filename);
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
                        this.storeAirport(a);
                        //System.out.println(args[0] + " " + args[1]);
                        break;
                    case "connections.txt":
                        //airport, minutes
                        Airport connectionAirport = getAirport(args[0]);
                        int min = Integer.parseInt(args[1]);
                        connectionAirport.setConnection(min);
                        //System.out.println(args[0] + " " + args[1]);
                        break;
                    case "delays.txt":
                        //airport, minutes
                        Airport delayAirport = getAirport(args[0]);
                        int mins = Integer.parseInt(args[1]);
                        delayAirport.setDelay(mins);
                        //System.out.println(args[0] + " " + args[1]);
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
                        oa.addFlight(fly);
                        da.addFlight(fly);


                        //System.out.println(args[0] + " " + args[1] + " " + args[2] + " " +
                         //       args[3] + " " + args[4] + " " + args[5]);
                        break;
                    case "weather.txt":
                        //input csv not always the same number of args
                        //use a for loop to split the weather and temp; the first
                        //index will be the airport, the even # indices will be
                        //weather, the odd # will be the temp
                        String aCode = args[0];

                        int i = 1;
                        //String[] weat = new String[10];
                        ArrayList<String> weat = new ArrayList<String>();
                        ArrayList<Integer> temp = new ArrayList<Integer>();

                        //int[] temp = new int[10];

                        int w =0;
                        int t = 0;
                        while(i < args.length)
                        {
                            if(i%2==0)
                            {
                                temp.add(Integer.parseInt(args[i]));
                                t++;
                            }
                            else
                            {

                                weat.add(args[i]);
                                w++;
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
    public void writeData()
    {
        try {


            //Write Airport File airport code,airport city
            BufferedWriter writer = new BufferedWriter(new FileWriter("./textFiles/airports.txt"));
            for (Airport a : airports)
            {
                writer.write(a.getCode() + "," + a.getCity()+"\n");
            }
            writer.close();
            //Write connections File: code,time
            writer = new BufferedWriter(new FileWriter("./textFiles/connections.txt"));
            for(Airport a : airports)
            {
                writer.write(a.getCode() + "," + a.getConnections()+ "\n");
            }
            writer.close();
            //Write Delays File: code,delay time
            writer = new BufferedWriter(new FileWriter("./textFiles/delays.txt"));
            for(Airport a : airports)
            {
                writer.write(a.getCode() + "," + a.getDelays() +"\n");
            }
            writer.close();

            //Write Flights file: origin,destination,depart time, arrival time, flight num, airfare
            writer = new BufferedWriter(new FileWriter("./textFiles/flights.txt"));
            for(Flight f : flights)
            {
                writer.write(f.getOrigin().getCode() + "," + f.getDestination().getCode() + ","
                        + convertTime(f.getDeparture()) + "," + convertTime(f.getArrival()) + ","
                        + f.getFlightNumber() + "," + (int)f.getAirfare() + "\n");
            }
            writer.close();
            //Write Weather File: airport, weather, temp...
            writer = new BufferedWriter(new FileWriter("./textFiles/weather.txt"));
            for(Airport a : airports)
            {
                writer.write(a.getCode()+",");
                for(int i = 0; i<a.getWeather().size(); i++)
                {
                    writer.write(a.getWeather().get(i) + "," + a.getTemp().get(i)+",");
                }
                writer.write("\n");

            }
            writer.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void storeFlight(Flight f)
    {

    }
    private String convertTime(LocalTime t)
    {
        String hour = "";
        String min = Integer.toString(t.getMinute());
        String ampm = "";

        if(t.getHour() > 12)
        {
            hour = t.getHour()-12 +":";
            ampm = "p";
        }
        else
        {
            hour = t.getHour() + ":";
            ampm = "a";
        }
        if(min.length() == 1)
        {
            min = "0" + min ;
        }

        return hour + min + ampm;

    }

    public synchronized void storeAirport(Airport a)
    {
        boolean inArray = false;

        if(airports.size()== 0)
        {
            airports.add(a);
        }

        for(int i = 0; i<airports.size(); i++)
        {
            Airport adb = airports.get(i);

            if(adb.getCode().equals(a.getCode()))
            {
                inArray = true;
                String codeDB = adb.getCode();
                ArrayList<String> weatherDB = adb.getWeather();
                ArrayList<Integer> tempDB = adb.getTemp();

                //String[] weatherDB = adb.getWeather();
                //int[] tempDB = adb.getTemp();
                String cityDB = adb.getCity();

                String codeA = a.getCode();
                //String[] weatherA = a.getWeather();
                //int[] tempA = a.getTemp();
                ArrayList<String> weatherA = a.getWeather();
                ArrayList<Integer> tempA = a.getTemp();

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
        if(!inArray)
        {
            airports.add(a);
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
        char military = timeSlots[1].charAt(2);
        int hour = Integer.parseInt(timeSlots[0]);
        int min = Integer.parseInt(timeSlots[1].substring(0,2));

        if(military == 'p' && hour != 12)
        {
            hour += 12;

        }
        else
        {
            hour = hour;
        }

        return LocalTime.of(hour, min);
    }
    
    public ArrayList<Itinerary> createItineraries(Itinerary itin)
    {
      return createItineraries(itin, Itinerary.MAXIMUM_TRANSFERS);
    }

    public ArrayList<Itinerary> createItineraries(Itinerary itin, int hopsLeft)
    {
      //make a list of itins,this will be returned at the end
      ArrayList<Itinerary> mainList = new ArrayList();
      //we need to know where we've been, if anywhere
      ArrayList<Flight> hops = itin.getFlights();  
      ArrayList<Airport> visited = new ArrayList();
      for(Flight f : hops)
      {
        visited.add(f.getOrigin());
      }
      Airport location = hops.get(hops.size() - 1).getDestination();
      //base case: is this the final hop?
      if(hopsLeft <= 0)
      {
        //if so, did we fail to reach our destination?
        if(location != itin.getDestination())
        {
          return null;
        }
        //if not, the itinerary is valid
        else
        {
          mainList.add(itin);
          return mainList;
        }
      }
      //we have hops remaining
      else
      {
        for(Flight fl : location.getFlights())
        {
          //is the flight after we arrive and to an airport we've never been to?
          if(fl.getDeparture().isAfter(itin.getNextAvailibleTime()) && !visited.contains(fl.getDestination()))
          {
            Itinerary cpitin = itin;
            cpitin.addFlight(fl);
            //if so, this is a potential transfer flight
            mainList.addAll(createItineraries(cpitin, hopsLeft - 1));
          }
        }
        return mainList;
      } 
    }
    public void storeItinerary(Itinerary itin)
    {
      itineraries.add(itin);
    }

    public static void main(String[] args)
    {

        RouteNetwork rn = RouteNetwork.getInstance();
        /*Airport a = new Airport("ATL", "Atlanta");
        rn.storeAirport(a);
        Airport b = new Airport("BOS", "Boston");
        rn.storeAirport(b);
        String[] w = {"hot", "sunny", "humid"};
        int[] t = {20, 40, 50};

        a.setWeather(w);
        a.setTemperature(t);
        b.setWeather(w);
        b.setTemperature(t);
        System.out.println(a.getTemperature()+ " "+ a.getWeath());
        */
        File f = new File("src/inputFiles/airports.txt");
        File f1 = new File("src/inputFiles/connections.txt");
        File f2 = new File("src/inputFiles/delays.txt");
        File f3 = new File("src/inputFiles/flights.txt");
        File f4 = new File("src/inputFiles/weather.txt");

        rn.readInfo(f);
        rn.readInfo(f1);
        rn.readInfo(f2);
        rn.readInfo(f3);
        rn.readInfo(f4);

        rn.writeData();
    }



}


