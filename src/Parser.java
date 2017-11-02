import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Parser extends Observable {

    private String s;

    public Parser()
    {

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

    public void takeInput (Scanner scan)
    {

        String S = "";
        //Scanner scan = new Scanner(System.in);
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

    private void help(String str)
    {
        String[] list = parseInput(str);
        if (list.length < 2)
        {
            setInput("error incorrect command usage.");
        }
        else
        {
            //System.out.println(list[1]);
            if (list[1].equals("info"))
            {
                setInput("info,origin,destination[,connections[,sort-order]];");
                setInput("origin/destination are 3-letter airport codes, All CAPS");
                setInput("connections is a number from 0-2");
                setInput("sort-order options: departure, arrival, airfare");
            }
            else if (list[1].equals("reserve"))
            {
                setInput("reserve,id,passenger;");
                setInput("id is the number identifier ");
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


}
