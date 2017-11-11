package test;

import main.Airport;
import main.AirportQuery;
import main.RouteNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by liam on 11/11/17.
 */
class TAirportQuery
{
    RouteNetwork rn;

    @BeforeEach
    void setUp()
    {
        rn = RouteNetwork.getInstance();
        rn.flushData();
        ArrayList<String> files = new ArrayList<String>();
        files.add("airports.txt");
        files.add("weather.txt");
        files.add("connections.txt");
        files.add("delays.txt");
        for (int i = 0; i < files.size(); i++)
        {
            File f = new File("src/tests/testFiles/" + files.get(i));
            rn.readInfo(f);
        }
    }

    @Test
    void processDataROC()
    {
        AirportQuery aq = new AirportQuery();
        String[] input = {"ROC", "local"};
        Airport port = (Airport)aq.processData(input).get(0);
        assertEquals("ROC", port.getCode(), "Airport code should be ROC");
        assertEquals("Rochester", port.getCity(), "Airport city should be Rochester");
    }

    @Test
    void processDataATL()
    {
        AirportQuery aq = new AirportQuery();
        String[] input = {"ATL", "local"};
        Airport port = (Airport)aq.processData(input).get(0);
        assertEquals("ATL", port.getCode(), "Airport code should be ATL");
        assertEquals("Atlanta", port.getCity(), "Airport city should be Atlanta");
    }

    @Test
    void processDataBOS()
    {
        AirportQuery aq = new AirportQuery();
        String[] input = {"BOS", "local"};
        Airport port = (Airport)aq.processData(input).get(0);
        assertEquals("BOS", port.getCode(), "Airport code should be BOS");
        assertEquals("Boston", port.getCity(), "Airport city should be Boston");
    }

}