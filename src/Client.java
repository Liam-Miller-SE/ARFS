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
        String inp;
        String newin;

        while(true)
        {
            inp = S + scan.nextLine();
            String lastChar = inp.substring(inp.length() -1);
            newin = "";

            if(inp.equals("help;"))
            {
                System.out.println("Options are: info, reserve, retrieve, delete, airport, help, exit");
                System.out.println("Please note that all responses should end with a ';'");
                break;
            }

            else if (inp.equals("exit;"))
            {
                System.out.println("Goodbye!");
                return;
            }

            if (!(lastChar.equals(";")))
            {
                System.out.println("partial-request");
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
                break;
            }
            S = inp;
        }
        takeInput();
    }
    public void setInput(String str)
    {
        this.S = str;
    }
    public String getInput()
    {
        return this.S;
    }

}
