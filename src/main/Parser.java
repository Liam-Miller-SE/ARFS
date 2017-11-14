package main;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Parser extends Observable {

    private String s;
    private ArrayList<String> inputRequest;
    private String[] query;

    public Parser()
    {
        inputRequest = new ArrayList<>();
        s = "";

    }

    public String getString()
    {
        return this.s;
    }

    private void setInput(String str)
    {
            this.s = str;
            setChanged();
            notifyObservers();
    }

    private void setQuery(String[] query)
    {
        this.query = query;
        setChanged();
        notifyObservers();
    }
    public String[] getQuery()
    {
        return this.query;
    }



    public void takeInput (String myInput)
    {

        String S = "";
        //Scanner scan = new Scanner(System.in);
        String inp;
        String newin;


        while(true)
        {
            inp = S + myInput;
            String lastChar = inp.substring(inp.length() -1);
            newin = "";

            if (!(lastChar.equals(";"))) //Shouldn't need this check anymore -> moved to textui
            {
                setInput("partial-request");
                break;
            }
            else
            {
                String id = "";
                String parsedInp = "";
                for(int i = 0 ; i < myInput.length() ; i ++ )
                {
                    if (myInput.charAt(i) == ',')
                    {
                        break;
                    }
                    else
                    {
                        id += myInput.charAt(i);
                    }
                    //System.out.println(id);

                }
                parsedInp = myInput.substring(id.length()+1);
                //System.out.println("Input will be " + parsedInp);

                int i = 0;
                while (i < (parsedInp.length()))
                {
                    char in = parsedInp.charAt(i);
                    if (in != ',')
                    {
                        newin = newin + in;
                        i++;
                    }
                    else
                    {
                        break;

                    }
                }
                //System.out.println(newin);

                switch (newin)
                {
                    case "info":
                        //System.out.println("info for flights");
                        setQuery(getFlightInfo(inp));
                        break;
                    case "reserve":
                        //System.out.println("making a reservation");
                        setQuery(makeReservation(inp));
                        break;
                    case "retrieve":
                        //System.out.println("finding a reservation");
                        setQuery(getReservationInfo(inp));
                        break;
                    case "delete":
                        //System.out.println("deleting a reservation");
                        setQuery(deleteReservation(inp));
                        break;
                    case "airport":
                        //System.out.println("info for an airport");
                        setQuery(getAirportInfo(inp));
                        break;
                    case "connect":
                    case "disconnect":
                    case "server":
                    case "undo":
                    case "redo":

                    default:
                        setInput("error,unknown request: " + newin);
                }

                break;
            }
            //S = inp;
            //takeInput(S);
        }
    }

    private String[] getFlightInfo(String str)
    {
        String[] params = parseInput(str);
        if(params.length < 3)
        {
            setInput("expected at least 2 parameters (max 4), got " + params.length);
        }
        else if(params.length > 5)
        {
            setInput("Too many parameters, got " + params.length);
        }
        else
        {

            //response = query(params);
            //setInput(response);
        }
        return params;
    }
    private String[] getReservationInfo(String str)
    {
        String[] params = parseInput(str);
        if(params.length < 2)
        {
            setInput("expected at least 2 parameters (max 4), got " + params.length);
        }
        else if(params.length > 4)
        {
            setInput("Too many parameters, got " + params.length);
        }
        else
        {
            //response = query(params);
            //setInput(response);
        }
        return params;
    }
    private String[] makeReservation(String str)
    {
        String[] params = parseInput(str);
        if (params.length != 4)
        {
            setInput("error, Incorrect Parameters");
        }
        else
        {

            //setInput("Retrieving Some data ");
            return params;

            //sc.storeItinerary();
            //Insert method from Scheduler to retrieve data here
        }
        return params;
    }
    private String[] deleteReservation(String str)
    {
        String[] params = parseInput(str);
        //System.out.println(params.length);
        if (params.length != 4)
        {
            setInput("error, Incorrect number of parameters, expected 4, got " + params.length);
        }
        else
        {
            if (params[2].length() != 3)
            {
                setInput("Unknown origin airport ");
            }
            if (params[params.length-1].length() != 3)
            {
                setInput("Unknown destination airport");
            }
            setInput("Retrieving Reservation data ");
            //response = query(params);
            //setInput(response);
            //Insert method to retrieve data here
        }
        return params;
    }
    private String[] getAirportInfo(String str)
    {
        String[] params = parseInput(str);
        if (params.length != 3)
        {
            setInput("error, unknown airport");
        }
        else
        {
            if (params[2].length() == 3)
            {
                //setInput("Retrieving airport data with code " + params[1] );
                //response = query(params);
                //setInput(response);
                return params;
            }
            else
            {
                setInput("Unknown airport code");
            }

            //Insert method to retrieve data here
        }
        return null;
    }

    private String[] parseInput(String str)
    {
        Scanner sc = new Scanner(str);
        String input;
        String[] lines;

        input = sc.nextLine();
        input = input.replace(";","");
        lines = input.split(",");

        return lines;

    }




}
