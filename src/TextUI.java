//package ARFS;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class TextUI implements Observer
{
    private AFRSapi c;
    private static Scanner sc;
    private String s;

    public TextUI(AFRSapi c)
    {
        this.c = c;
        c.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        String output = c.getInput();
        if(output.equals("exit"))
        {
            System.out.println("Goodbye!");
            return;
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

    public void sendString()
    {
        sc = new Scanner(System.in);
        while(true)
        {
            String myInput = sc.nextLine();
            String lastChar = myInput.substring(myInput.length() -1);



            if (myInput.equals("help;"))
            {
                System.out.println("Options are: info, reserve, retrieve, delete, airport, help, exit");
                System.out.println("Please note that all responses should end with a ';'");
                System.out.println("For more info about a specific option, Type 'help,[option];' ");

            }
            else if (myInput.equals("exit;"))
            {
                System.out.println("Goodbye!");
                return;
            }
            else
            {
                c.parseInput(myInput);

            }

            //s = sc.nextLine();
            //c.parseInput(myInput);
            //setString(s);
        }
    }

    private void setString(String str)
    {
        this.s = str;
    }
    public String getString()
    {
        return this.s;
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
