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
        System.out.println(output);
    }

    public void sendString()
    {
        sc = new Scanner(System.in);
        //while(true)
        //{
            s = sc.nextLine();
            setString(s);
        //}
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

        AFRSapi c = new AFRSapi();
        c.getFiles();
        c.loadFiles();
        Observer o = new TextUI(c);

         //sc = new Scanner(System.in);
         //c.parseInput(sc);

        //c.takeInput();

    }
}
