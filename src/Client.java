import java.util.Observable;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Created by melis on 10/5/2017.
 * Implementation done by Josh
 */
public class Client extends Observable
{
    private String S;
    private String UpdateStr;
    private String response;


    public void takeInput ()
    {

        S = "";
        Scanner scan = new Scanner(System.in);
        String inp;
        String newin;

        while(true)
        {
            inp = S + scan.nextLine();
            String lastChar = inp.substring(inp.length() -1);
            newin = "";

            if(inp.equals("help;"))
            {
                setInput("Options are: info, reserve, retrieve, delete, airport, help, exit");
                setInput("Please note that all responses should end with a ';'");
                setInput("For more info about a specific option, Type 'help,[option];' ");
                break;
            }

            else if (inp.equals("exit;"))
            {
                setInput("Goodbye!");
                return;
            }

            if (!(lastChar.equals(";")))
            {
                setInput("partial-request");
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
                        System.out.println("info for flights");
                        getFlightInfo(inp);
                        break;
                    case "reserve":
                        System.out.println("making a reservation");
                        makeReservation(inp);
                        break;
                    case "retrieve":
                        System.out.println("finding a reservation");
                        getReservationInfo(inp);
                        break;
                    case "delete":
                        System.out.println("deleting a reservation");
                        deleteReservation(inp);
                        break;
                    case "airport":
                        System.out.println("info for an airport");
                        getAirportInfo(inp);
                        break;
                    case "help":
                        help(inp);
                        break;
                    default:
                        setInput("error,unknown request: " + newin);
                }
                break;
            }
            S = inp;
        }
        takeInput();
    }
    private String getFlightInfo(String str)
    {
        String[] params = parseInput(str);
        if(params.length < 2)
        {
            System.out.println("expected at least 2 parameters (max 4), got " + params.length);
            return "";
        }
        else if(params.length > 4)
        {
            System.out.println("Too many parameters, got " + params.length);
        }
        else
        {
         response = query(params);
         setInput(response);
        }
        return"";
    }
    private String getReservationInfo(String str)
    {
        String[] params = parseInput(str);
        if(params.length < 2)
        {
            System.out.println("expected at least 2 parameters (max 4), got " + params.length);
            return "";
        }
        else if(params.length > 4)
        {
            System.out.println("Too many parameters, got " + params.length);
        }
        else
        {
            response = query(params);
            setInput(response);
        }

        return "";
    }
    private String makeReservation(String str)
    {
        String[] params = parseInput(str);
        if (params.length != 3)
        {
            System.out.println("error, unknown Something");
            return "";
        }
        else
        {

            System.out.println("Retrieving Some data ");
            Scheduler sc = new Scheduler();
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
            System.out.println("error, Incorrect number of parameters, expected 4, got " + params.length);
            return "";
        }
        else
        {
            if (params[2].length() != 3)
            {
                System.out.println("Unknown origin airport ");
                return "";
            }
            if (params[params.length-1].length() != 3)
            {
                System.out.println("Unknown destination airport");
                return "";
            }
            System.out.println("Retrieving Reservation data ");
            response = query(params);
            setInput(response);
            //Insert method to retrieve data here
            return "";
        }
    }
    private String getAirportInfo(String str)
    {
        String[] params = parseInput(str);
        if (params.length != 2)
        {
            System.out.println("error, unknown airport");
            return "";
        }
        else
        {
            if (params[1].length() == 3)
            {
                System.out.println("Retrieving airport data with code " + params[1] );
                response = query(params);
                setInput(response);
            }
            else
            {
                System.out.println("Unknown airport code");
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
    private void help(String str)
    {
        String[] list = parseInput(str);
        if (list.length < 2)
        {
            System.out.println("error incorrect command usage.");
        }
        else
        {
            //System.out.println(list[1]);
            if (list[1].equals("info"))
            {
                setInput("info,origin,destination[,connections[,sort-order]];");
            }
            else if (list[1].equals("reserve"))
            {
                setInput("reserve,id,passenger;");
            }
            else if (list[1].equals("retrieve"))
            {
                setInput("retrieve,passenger[,origin[,destination]];");
            }
            else if (list[1].equals("delete"))
            {
                setInput("delete,passenger,origin,destination;");
            }
            else if (list[1].equals("airport"))
            {
                setInput("airport,airport;");
            }
            else if (list[1].equals("help"))
            {
                setInput("Only usage is 'help;'");
            }
            else if (list[1].equals("exit"))
            {
                setInput("Only usage is 'exit;'");
            }
            else
            {
                setInput("Unknown help option, please try again.");
            }
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
}
