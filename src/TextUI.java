import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class TextUI implements Observer
{
    private Client c;
    private ArrayList<String> Files;
    private File f;



    public TextUI(Client c)
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
    /**
    public void getFiles()
    {
        Files = new ArrayList<String>();
        Files.add("weather.txt");
        Files.add("airports.txt");
        Files.add("connections.txt");
        Files.add("delays.txt");
        Files.add("flights.txt");
    }

    public void loadFiles()
    {
        for(int i = 0; i < Files.size(); i ++)
        {
            File f = new File("src/inputFiles/" + Files.get(i) );
            readInfo(f);
        }
    }
    **/
    public static void main(String[] args)
    {
        RouteNetwork rn = RouteNetwork.getInstance();
        //rn.getFiles();
        //rn.loadFiles();
        System.out.println("Welcome to AFRS!");
        System.out.println("We are now in the first release of development...");
        System.out.println("What are you looking to do?");
        System.out.println("If unsure about inputs... Type 'help;'");

        Client c = new Client();
        Observer o = new TextUI(c);

        c.takeInput();

    }
}
