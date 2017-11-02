//package ARFS;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by melis on 10/5/2017.
 * Implementation done by Josh
 */
public class AFRSapi extends Observable
{
    private String S;
    private String UpdateStr;
    private String response;
    private static ArrayList<String> Files;



    private void setInput(String str)
    {
        this.UpdateStr = str;
        setChanged();
        notifyObservers();

    }
    public String getInput()
    {
        return this.UpdateStr;
    }


    public void getFiles()
    {
        Files = new ArrayList<String>();
        Files.add("airports.txt");
        Files.add("weather.txt");
        Files.add("connections.txt");
        Files.add("delays.txt");
        Files.add("flights.txt");
    }

    public void loadFiles()
    {
        RouteNetwork rn = RouteNetwork.getInstance();

        for(int i = 0; i < Files.size(); i ++)
        {
            File f = new File("src/inputFiles/" + Files.get(i) );
            rn.readInfo(f);
        }
    }

    public String query(String[] query)
    {
        if( query[0].equals("info"))
        {
            ItineraryQuery iq = new ItineraryQuery() ;
            return iq.processData(Arrays.copyOfRange(query, 1, query.length)) ;
        }
        else if( query[0].equals("retrieve"))
        {
            ReservationQuery rq = new ReservationQuery() ;
            return rq.processData(Arrays.copyOfRange(query, 1, query.length)) ;
        }
        else if( query[0].equals("airport"))
        {
            AirportQuery aq = new AirportQuery() ;
            return aq.processData(Arrays.copyOfRange(query, 1, query.length)) ;
        }
        else
        {
            return null;
        }
    }
    public void parseInput(Scanner sc)
    {
        Parser par = new Parser();
        par.takeInput(sc);
    }


}
