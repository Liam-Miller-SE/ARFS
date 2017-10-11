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
        try
        {
            BufferedReader fr = new BufferedReader(new FileReader(f));
            String line = fr.readLine();
            while(line != null)
            {
                System.out.println(line);
                String[] args = line.split(",");
                System.out.println(args[0] + " " + args[1]);
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
    public static void main(String[] args)
    {
        RouteNetwork r = new RouteNetwork();
        File f = new File("C:\\Users\\melis\\Documents\\GitHub\\ARFS\\src\\inputFiles\\airports.txt");
        r.readInfo(f);
    }

}
