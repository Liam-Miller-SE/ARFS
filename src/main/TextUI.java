package main;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class TextUI implements Observer
{
    private AFRSapi c;
    private static Scanner sc;
    private String s;
    private String id;
    private ArrayList<Itinerary> itins = new ArrayList<>();

    public TextUI(AFRSapi c)
    {
        this.c = c;
        c.addObserver(this);
        this.id = "0";
    }

    @Override
    public void update(Observable o, Object arg)
    {
        String output = c.getInput();
        itins = c.getItineraries();
        if(output.equals("exit"))
        {
            c.quit();
            System.out.println("Goodbye!");

        }
        else if(output.equals("help"))
        {
            System.out.println("Options are: info, reserve, retrieve, delete, airport, help, exit");
            System.out.println("Please note that all responses should end with a ';'");
            System.out.println("For more info about a specific option, Type 'help,[option];' ");
        }
        else
        {
            System.out.println(output);
        }


    }

    private void makeRes(String s)
    {
        ArrayList<Itinerary> it = this.itins;
        //s = "id,reserve,name"
    }

    public void sendItins()
    {
        c.updateItin(this.itins);
    }

    public void sendString()
    {
        if (sc == null)
        {
            sc = new Scanner(System.in);
        }
        {
            String myInput = sc.nextLine();
            String lastChar = myInput.substring(myInput.length() -1);
            String command = myInput.substring(0,myInput.length()-1);
            String isHelp = "";
            String isReserve = "";
            if (myInput.length() >= 7) {
                isReserve = myInput.substring(0, 7);
            }
            //System.out.println(isReserve);
            if(myInput.length() >= 5) {
                isHelp = myInput.substring(0, 4);
            }

            //System.out.println(isHelp);

            if (isHelp.equals("help"))
            {
                if (command.length() > 4) //Either there's a ',' meaning an extra command arg
                {
                    String getHelp = myInput.substring(5,myInput.length()-1);
                    help(getHelp);
                }
                else //Command was only 'help;' print general help
                {
                    System.out.println("Options are: info, reserve, retrieve, delete, airport, help, exit");
                    System.out.println("Please note that all responses should end with a ';'");
                    System.out.println("For more info about a specific option, Type 'help,[option];' ");
                }
            }
            else if (command.equals("exit"))
            {
                System.out.println("Goodbye!");
                c.quit();
                return;
            }
            else if (isReserve.equals("reserve"))
            {
                sendItins();
                c.updateString(id + "," + myInput);
            }
            else
            {
                c.updateString(id + "," + myInput);


            }
            sendString();

        }

    }

    private void help(String str)
    {
            //System.out.println(list[1]);
            if (str.equals("info"))
            {
                System.out.println("info,origin,destination[,connections[,sort-order]];");
                System.out.println("origin/destination are 3-letter airport codes, All CAPS");
                System.out.println("connections is a number from 0-2");
                System.out.println("sort-order options: departure, arrival, airfare");
            }
            else if (str.equals("reserve"))
            {
                System.out.println("reserve,id,passenger;");
                System.out.println("id is the number identifier ");
            }
            else if (str.equals("retrieve"))
            {
                System.out.println("retrieve,passenger[,origin[,destination]];");
            }
            else if (str.equals("delete"))
            {
                System.out.println("delete,passenger,origin,destination;");
            }
            else if (str.equals("airport"))
            {
                System.out.println("airport,airport;");
            }
            else if (str.equals("help"))
            {
                System.out.println("Only usage is 'help;'");
            }
            else if (str.equals("exit"))
            {
                System.out.println("Only usage is 'exit;'");
            }
            else
            {
                System.out.println("Unknown help option, please try again.");
            }

    }

    public static void main(String[] args)
    {
        System.out.println("Welcome to AFRS!");
        System.out.println("We are now in the first release of development...");
        System.out.println("What are you looking to do?");
        System.out.println("If unsure about inputs... Type 'help;'");
        Parser p = new Parser();
        AFRSapi c = new AFRSapi(p);
        c.getFiles();
        c.loadFiles();
        //Observer o = new TextUI(c);
        //Observer oapi = new AFRSapi(p);
        TextUI t = new TextUI(c);
        t.sendString();



         //sc = new Scanner(System.in);
         //c.parseInput(sc);

        //c.takeInput();

    }
}
