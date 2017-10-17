import java.util.Observable;
import java.util.Observer;

public class TextUI implements Observer
{
    private Client c;

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

    public static void main(String[] args)
    {
        System.out.println("Welcome to AFRS!");
        System.out.println("We are now in the first release of development...");
        System.out.println("What are you looking to do?");
        System.out.println("If unsure about inputs... Type 'help;'");
        Client c = new Client();
        Observer o = new TextUI(c);
        c.takeInput();

    }
}
