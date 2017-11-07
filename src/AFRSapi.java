//package ARFS;
import java.io.File;
import java.util.*;

/**
 * Created by melis on 10/5/2017.
 * Implementation done by Josh
 */
public class AFRSapi extends Observable implements Observer
{
    private String S;
    private String UpdateStr;
    private String input;
    private String response;
    private static ArrayList<String> Files;
    private static ArrayList<Integer> IDs = new ArrayList<Integer>();
    private Parser p;
    private boolean ready;
    public int ID;

    public AFRSapi(Parser p)
    {
        this.p = p;
        p.addObserver(this);
        this.ready  = false;
        this.UpdateStr = "";

        Random r = new Random();
        while(true)
        {
            int tempID = r.nextInt(100);
            if (!IDs.contains(tempID))
            {
                IDs.add(this.ID);
                this.ID = tempID;
                break;            }
        }


        //System.out.println(this.ID);

    }



    private void setInput(String str)
    {
        if (ready)
        {
            this.UpdateStr = str;
        }
        else
        {
            this.S = str;
        }
        setChanged();
        notifyObservers();


    }
    public String getInput()
    {
        if (!ready)
        {
            return this.S;
        }
        return this.UpdateStr;
    }


    @Override
    public void update(Observable o, Object arg) {
        String fullString = p.getString();
        setInput(fullString);

        String[] query = p.getQuery();
        setInput(query(query));
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

    public void updateString(String s )
    {
        this.UpdateStr = UpdateStr + s;
        if (UpdateStr.substring(UpdateStr.length()-1).equals(";"))
        {
            this.ready = true;
        }
        else
        {
            this.S = "partial-request";
            setInput(S);
        }
        parseInput(this.UpdateStr);
    }

    public void parseInput(String myInput)
    {
        if (ready)
        {
            p.takeInput(this.UpdateStr);
            ready = false;
            UpdateStr = "";
        }

    }


}
