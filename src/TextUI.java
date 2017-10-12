import java.util.Observable;
import java.util.Observer;

public class TextUI implements Observer
{


    @Override
    public void update(Observable o, Object arg)
    {

    }

    public static void main(String[] args)
    {
        System.out.println("Welcome to AFRS!");
        System.out.println("We are now in the first release of development...");
        System.out.println("What are you looking to do?");
        Client c = new Client();
        c.takeInput();

    }
}
