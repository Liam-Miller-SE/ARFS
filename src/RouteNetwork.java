//package ARFS;
import javax.naming.spi.ResolveResult;
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

    /**
     * This class holds a reference to the RouteNetwork Object
     */
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

    /**
     * This method takes in a file and parses it and stores the data taken based on its filename
     * @param f: takes in a file for reading
     */
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
                    case "reservation.txt":
                        //this case is for the reservation file across system startup and shutdown
                        /**
                         * Reservations csv file format;
                         *  passengerName,Origin,Destination,flight num, flight num, flight num
                         */
                        String name = args[0];
                        Airport origin = getAirport(args[1]);
                        Airport destination = RouteNetwork.getInstance().getAirport(args[2]);
                        //ArrayList<Flight> flightList = new ArrayList<Flight>();
                        Itinerary itin = new Itinerary(origin, destination, name);
                        for(int x = 3; x <args.length; x++)
                        {
                            Flight flightHop = getFlight(Integer.parseInt(args[x]));
                            itin.addFlight(flightHop);
                        }
                        Reservation r = new Reservation(name, itin);
                        reservations.add(r);
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

    /**
     * This method writes the data to the csv files so that the data can persist through system startups
     */
    public void writeData()
    {
        try {


            //Write Airport File airport code,airport city
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/inputFiles/airports.txt"));
            for (Airport a : airports)
            {
                writer.write(a.getCode() + "," + a.getCity()+"\n");
            }
            writer.close();
            //Write connections File: code,time
            writer = new BufferedWriter(new FileWriter("src/inputFiles/connections.txt"));
            for(Airport a : airports)
            {
                writer.write(a.getCode() + "," + a.getConnections()+ "\n");
            }
            writer.close();
            //Write Delays File: code,delay time
            writer = new BufferedWriter(new FileWriter("src/inputFiles/delays.txt"));
            for(Airport a : airports)
            {
                writer.write(a.getCode() + "," + a.getDelays() +"\n");
            }
            writer.close();

            //Write Flights file: origin,destination,depart time, arrival time, flight num, airfare
            writer = new BufferedWriter(new FileWriter("src/inputFiles/flights.txt"));
            for(Flight f : flights)
            {
                writer.write(f.getOrigin().getCode() + "," + f.getDestination().getCode() + ","
                        + convertTime(f.getDeparture()) + "," + convertTime(f.getArrival()) + ","
                        + f.getFlightNumber() + "," + (int)f.getAirfare() + "\n");
            }
            writer.close();
            //Write Weather File: airport, weather, temp...
            writer = new BufferedWriter(new FileWriter("src/inputFiles/weather.txt"));
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


            writer = new BufferedWriter(new FileWriter("src/inputFiles/reservations.txt"));
            for(Reservation r : reservations)
            {
                writer.write(r.getPassenger());
                Itinerary i = r.getItinerary();
                writer.write("," + i.getOrigin().getCode() + "," + i.getDestination().getCode()+",");
                for(Flight f : i.getFlights())
                {
                    writer.write(getFlightnum(f) + ",");
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

    public void storeReservation(Reservation r)
    {
        reservations.add(r);
    }

    public void deleteReservation(Reservation r)
    {
        for(Reservation db : reservations)
        {
            if (db.equals(r))
            {
                reservations.remove(db);
            }
        }
    }


    /**
     * Converts a LocalTime to a 12 hr readable time format
     * @param t: LocalTime that contains a military time
     * @return String of 12 hour human readable time format
     */
    public static String convertTime(LocalTime t)
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

    /**
     * Adds an airport into the array of airports. If the airport already exists in the system, it will overwrite
     * it's non null data to the airport and then not be stored itself. If no airport already exists, it is stored
     *
     * @param a: Airport
     */
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

    /**
     * Returns the matching airport object with the passed in airport code
     * @param code: String that represents the 3 letter unique airport code
     * @return Airport  that matches the 3 letter airport code, unless one does not exist in which case it will return
     * null
     */
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

    private static int getFlightnum(Flight f)
    {
        return f.getFlightNumber();
    }
    public Flight getFlight(int flightNum)
    {

        for(Flight f : flights)
        {
            if(f.getFlightNumber() == flightNum)
            {
                return f;
            }
        }
        return null;
    }

    /***
     * Takes in a string and makes a LocalTime out of it
     * @param time: String - time string read in through the files
     * @return LocalTime: formatted so it can be compared easily to other LocalTimes
     */
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
    
    public void storeItinerary(Itinerary itin)
    {
      itineraries.add(itin);
    }

/*
    public static void main(String[] args)
    {

        RouteNetwork rn = RouteNetwork.getInstance();
        Airport a = new Airport("ATL", "Atlanta");
        rn.storeAirport(a);
        Airport b = new Airport("BOS", "BOSTON");
        rn.storeAirport(b);
        Flight f = new Flight(a, b,  LocalTime.MIDNIGHT,  LocalTime.NOON, 123, 400 );
        rn.storeFlight(f);
        Itinerary i = new Itinerary(a, b, "Joe");
        i.addFlight(f);
        Reservation r = new Reservation("Joe", i);
        rn.storeItinerary(i);
        rn.storeReservation(r);
        rn.writeData();

        Airport b = new Airport("BOS", "Boston");
        rn.storeAirport(b);
        String[] w = {"hot", "sunny", "humid"};
        int[] t = {20, 40, 50};

        a.setWeather(w);
        a.setTemperature(t);
        b.setWeather(w);
        b.setTemperature(t);
        System.out.println(a.getTemperature()+ " "+ a.getWeath());

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
    */




}


