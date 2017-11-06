import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Parser extends Observable {

    private String s;
    private ArrayList<String> inputRequest;

    public Parser()
    {
        inputRequest = new ArrayList<>();

    }

    public String getString()
    {
        return this.s;
    }

    public void setInput(String str)
    {
            this.s = str;
            setChanged();
            notifyObservers();
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
                int i = 0;

                while (i < (inp.length()))
                {
                    char in = inp.charAt(i);
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

                switch (newin)
                {
                    case "info":
                        //System.out.println("info for flights");
                        getFlightInfo(inp);
                        break;
                    case "reserve":
                        //System.out.println("making a reservation");
                        makeReservation(inp);
                        break;
                    case "retrieve":
                        //System.out.println("finding a reservation");
                        getReservationInfo(inp);
                        break;
                    case "delete":
                        //System.out.println("deleting a reservation");
                        deleteReservation(inp);
                        break;
                    case "airport":
                        //System.out.println("info for an airport");
                        getAirportInfo(inp);
                        break;
                    default:
                        setInput("error,unknown request: " + newin);
                }
                break;
            }
            //S = inp;
            //takeInput(S);
        }



    }

    private String getFlightInfo(String str)
    {
        String[] params = parseInput(str);
        if(params.length < 3)
        {
            setInput("expected at least 2 parameters (max 4), got " + params.length);
            return "";
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
        return"";
    }
    private String getReservationInfo(String str)
    {
        String[] params = parseInput(str);
        if(params.length < 2)
        {
            setInput("expected at least 2 parameters (max 4), got " + params.length);
            return "";
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

        return "";
    }
    private String makeReservation(String str)
    {
        String[] params = parseInput(str);
        if (params.length != 3)
        {
            setInput("error, unknown Something");
            return "";
        }
        else
        {

            setInput("Retrieving Some data ");
            Scheduler sc = new Scheduler();

            //sc.storeItinerary();
            //Insert method from Scheduler to retrieve data here
            return "";
        }
    }
    private String deleteReservation(String str)
    {
        String[] params = parseInput(str);
        System.out.println(params.length);
        if (params.length != 4)
        {
            setInput("error, Incorrect number of parameters, expected 4, got " + params.length);
            return "";
        }
        else
        {
            if (params[2].length() != 3)
            {
                setInput("Unknown origin airport ");
                return "";
            }
            if (params[params.length-1].length() != 3)
            {
                setInput("Unknown destination airport");
                return "";
            }
            setInput("Retrieving Reservation data ");
            //response = query(params);
            //setInput(response);
            //Insert method to retrieve data here
            return "";
        }
    }
    private String getAirportInfo(String str)
    {
        String[] params = parseInput(str);
        if (params.length != 2)
        {
            setInput("error, unknown airport");
            return "";
        }
        else
        {
            if (params[1].length() == 3)
            {
                setInput("Retrieving airport data with code " + params[1] );
                //response = query(params);
                //setInput(response);
            }
            else
            {
                setInput("Unknown airport code");
            }

            //Insert method to retrieve data here
            return "";
        }
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
