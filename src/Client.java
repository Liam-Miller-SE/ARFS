import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Observable;
import java.util.Scanner;

/**
 * Created by melis on 10/5/2017.
 * Implementation done by Josh
 */
public class Client extends Observable
{
    private String S;

    public void takeInput ()
    {

        S = "";
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to AFRS!");
        System.out.println("We are now in the first release of development...");
        while(true)
        {
            System.out.println("What are you looking to do?");
            String inp = scan.nextLine();
            String lastChar = inp.substring(inp.length() -1);

            int i = 0;
            String newin = "";
            while(i < (inp.length()))
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

            switch(newin)
            {
                case "info":
                    System.out.println("info for flights");
                    break;
                case "reserve":
                    System.out.println("making a reservation");
                    break;
                case "retrieve":
                    System.out.println("finding a reservation");
                    break;
                case "delete":
                    System.out.println("deleting a reservation");
                    break;
                case "airport":
                    System.out.println("info for an airport");
                    break;
                default:
                    System.out.println("Invalid request command: " + newin);
            }



            if (lastChar.equals(";"))
            {
            }
            else
            {
                System.out.println("Last character was not a ';' to terminate request");
            }
        }

    }
    public void setInput(String str)
    {
        this.S = str;
    }
    public String getInput()
    {
        return this.S;
    }

    public static void main(String[] args)
    {
        Client c = new Client();
        c.takeInput();
    }

}
