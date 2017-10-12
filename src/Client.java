import java.util.Observable;
import java.util.Scanner;

/**
 * Created by melis on 10/5/2017.
 * Implementation done by Josh
 */
public class Client extends Observable
{
    private String S;
    private String UpdateStr;


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
                    default:
                        setInput("error,unknown request: " + newin);
                }
                break;
            }
            S = inp;
        }
        takeInput();
    }
    private void getFlightInfo(String str)
    {

    }
    private void getReservationInfo(String str)
    {

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
            //Insert method to retrieve data here
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
            System.out.println("Retrieving airport data ");
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

    
    public static void main(String[] args) {
        Client c = new Client();
        c.parseInput("test,something,one,two");

    }
}
