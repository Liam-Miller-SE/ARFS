package main;
import java.io.File;
import java.util.*;

/**
 * Created by melis on 10/5/2017.
 * Implementation done by Josh and Melissa
 */
public class AFRSapi extends Observable implements Observer {
    private String S;
    private String UpdateStr;
    private String input;
    private String response;
    private static ArrayList<String> Files;
    private static ArrayList<Integer> IDs = new ArrayList<Integer>();
    private Parser p;
    private boolean ready;
    public int ID;
    private Scheduler scheduler = new Scheduler();
    private ArrayList<Itinerary> tempItin = new ArrayList<Itinerary>();
    private ArrayList<Itinerary> resItin = new ArrayList<>();



    public AFRSapi(Parser p) {
        this.p = p;
        p.addObserver(this);
        this.ready = false;
        this.UpdateStr = "";

        Random r = new Random();
        while (true) {
            int tempID = r.nextInt(100);
            if (!IDs.contains(tempID)) {
                IDs.add(this.ID);
                this.ID = tempID;
                break;
            }
        }


        //System.out.println(this.ID);

    }


    private void setInput(String str, ArrayList<Itinerary> itins) {
        if (ready) {
            this.UpdateStr = str;
        } else {
            this.S = str;
        }

        tempItin = itins;
        setChanged();
        notifyObservers();


    }

    public String getInput() {
        if (!ready) {
            return this.S;
        }
        return this.UpdateStr;
    }

    public ArrayList<Itinerary> getItineraries()
    {
        return this.tempItin;
    }




    @Override
    public void update(Observable o, Object arg) {
        String fullString = p.getString();
        setInput(fullString, null);

        String[] query = p.getQuery();
        ID = Integer.parseInt(query[0]);
        query = Arrays.copyOfRange(query, 1, query.length);
        //String send = "";


        if(query[0].equals("delete") || query[0].equals("reserve") || query[0].equals("undo") || query[0].equals("redo"))
        {
            String success = reservations(ID, query, resItin);
            String finalOut = ID +"," + success;

            setInput(finalOut, null);
        }

        Object output = query(query);
        if(output instanceof ArrayList<?>)
        {
            ArrayList<?> out = (ArrayList<?>) output;
            if(out.get(0) instanceof Airport)
            {
                String outString = ID + ",";
                Airport a = (Airport)out.get(0);
                outString += a.toString();
                setInput(outString, null);
            }
            else if(out.get(0) instanceof Itinerary )
            {

                tempItin = (ArrayList<Itinerary>) out;
                String ot = ID + ",info,";
                int num = 0;
                for(Itinerary j : tempItin)
                {
                    ot +=num++ + "," +j.toOutputString() + "\n";
                }
                setInput(ot, tempItin);
            }
            else if(out.get(0) instanceof Reservation)
            {
                String ret =ID +",retrieve,";
                for(int j = 0; j < out.size(); j++)
                {
                    ret += out.get(j).toString();
                }
                setInput(ret, null);
            }
            else
            {
                setInput(ID + ",error, unknown request", null);
            }
        }
        else
        {
            setInput(ID + ",error,unknown request", null);
        }


    }

    public void getFiles() {
        Files = new ArrayList<String>();
        Files.add("airports.txt");
        Files.add("weather.txt");
        Files.add("connections.txt");
        Files.add("delays.txt");
        Files.add("flights.txt");
    }

    public void loadFiles() {
        RouteNetwork rn = RouteNetwork.getInstance();

        for (int i = 0; i < Files.size(); i++) {
            File f = new File("src/inputFiles/" + Files.get(i));
            rn.readInfo(f);
        }
    }


    public Object query(String[] query) {
        if (query[0].equals("info")) {
            ItineraryQuery iq = new ItineraryQuery();
            return iq.processData(Arrays.copyOfRange(query, 1, query.length));
            //returns list of itineraries
        } else if (query[0].equals("retrieve")) {
            ReservationQuery rq = new ReservationQuery();
            return rq.processData(Arrays.copyOfRange(query, 1, query.length));
            //returns a list of reservations of a specified client name
        } else if (query[0].equals("airport")) {
            AirportQuery aq = new AirportQuery();
            return aq.processData(Arrays.copyOfRange(query, 1, query.length));
            //returns a single airport in an array list of airports
        } else {
            return null;
        }
    }

    private String reservations(int cid, String[] query, ArrayList<Itinerary> itins)
    {
        switch (query[0]){
            case ("reserve"):

                Itinerary i;
                String name = query[2];
                try
                {
                    i = itins.get(Integer.parseInt(query[1]));
                }
                catch(Exception e)
                {
                    return "error,invalid id";
                }
                if(i == null)
                {
                    return "error,invalid id";
                }

                int success = scheduler.makeReservation(i, name);
                if(success == 1)
                {
                    return "error,duplicate reservation";
                }
                else
                {
                    Reservation r = new Reservation(name, i);
                    scheduler.addElementUndo(cid, r);
                    return "reserve,successful";
                }

            case ("delete"):
                Reservation reserv = scheduler.deleteReservation(query[1], RouteNetwork.getInstance().getAirport(query[2]),
                        RouteNetwork.getInstance().getAirport(query[3]));
                if(reserv != null)
                {
                    scheduler.addElementUndo(cid, reserv);

                    return "delete,success";
                }
                else
                {
                   return "error,reservation not found";
                }

            case ("undo"):
                String opo = scheduler.undo(cid);
                Reservation ele = scheduler.getElement();
                if (!opo.equals(""))
                {

                    return "undo," + opo + "," + ele.getPassenger() + "," + ele.getItinerary().toString();
                }
                else
                {
                    return "error,no request available";
                }


            case ("redo"):
                String operation = scheduler.redo(cid);
                Reservation elem = scheduler.getElement();

             if(!operation.equals(""))
             {
                 return "redo," + operation + "," + elem.getPassenger() + "," + elem.getItinerary();
             }
             else
             {
                return "error,no request available";
             }
            default:
                return "error,unknown request";
        }



    }
    public void updateItin(ArrayList<Itinerary> itin)
    {
        this.resItin = itin;
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
            setInput(S, tempItin);
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
    public void quit()
    {
        RouteNetwork rn = RouteNetwork.getInstance();
        rn.writeData();
    }



}
