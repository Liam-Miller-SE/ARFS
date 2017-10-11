import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

                        System.out.println(args[0] + " " + args[1] + " " + args[2] + " " +
                                args[3] + " " + args[4] + " " + args[5]);
                        break;
                    case "weather.txt":
                        //input csv not always the same number of args
                        System.out.println(args[0] + " " + args[1]);
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

    }
    public ArrayList<Itinerary> createItineraries(Airport origin, Airport destination)
    {
        return null;
    }


}
