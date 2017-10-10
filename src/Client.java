import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Observable;

/**
 * Created by melis on 10/5/2017.
 */
public class Client extends Observable
{
    private String S;

    public void takeInput ()
    {
        S = "";
        while(true)
        {
            System.out.println("Loop through to take in input");



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

}
