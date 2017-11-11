package test;

import main.Itinerary;
import main.ItineraryQuery;
import main.RouteNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by liam on 11/11/17.
 */
class TItineraryQuery
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
        files.add("flights.txt");
        for (int i = 0; i < files.size(); i++)
        {
            File f = new File("src/tests/testFiles/" + files.get(i));
            rn.readInfo(f);
        }
    }

    @Test
    void processDataFromROCtoBOS()
    {
        ItineraryQuery iq = new ItineraryQuery();
        String[] input = {"ROC", "BOS", "3", "AIRFARE"};
        ArrayList<Object> itins = iq.processData(input);
        assertEquals(2, itins.size(), "There should be 2 routes from ROC to BOS");
        Itinerary i0 = (Itinerary)itins.get(0);
        Itinerary i1 = (Itinerary)itins.get(1);

        assertEquals((Integer)84, i0.getAirfare(), "Itinerary 0: airfare should be $84");
        assertEquals("ROC", i0.getOrigin().getCode(), "Itinerary 0: Origin airport should be ROC");
        assertEquals("BOS", i0.getDestination().getCode(), "Itinerary 0: Destination airport should be BOS");

        assertEquals((Integer)100, i1.getAirfare(), "Itinerary 1: airfare should be $100");
        assertEquals("ROC", i1.getOrigin().getCode(), "Itinerary 1: Origin airport should be ROC");
        assertEquals("BOS", i1.getDestination().getCode(), "Itinerary 1: Destination airport should be BOS");
    }

    @Test
    void processDataFromBOStoROC()
    {
        ItineraryQuery iq = new ItineraryQuery();
        String[] input = {"BOS", "ROC"};
        ArrayList<Object> itins = iq.processData(input);
        assertEquals(1, itins.size(), "There should be 1 route from BOS to ROC");
        Itinerary i0 = (Itinerary)itins.get(0);

        assertEquals((Integer)97, i0.getAirfare(), "Itinerary 0: airfare should be $97");
        assertEquals("BOS", i0.getOrigin().getCode(), "Itinerary 0: Origin airport should be BOS");
        assertEquals("ROC", i0.getDestination().getCode(), "Itinerary 0: Destination airport should be ROC");
    }

    @Test
    void processDataDepartureSort()
    {
        ItineraryQuery iq = new ItineraryQuery();
        String[] input = {"ROC", "BOS", "3", "DEPARTURE"};
        ArrayList<Object> itins = iq.processData(input);
        assertEquals(2, itins.size(), "There should be 2 routes from ROC to BOS");
        Itinerary i0 = (Itinerary)itins.get(0);
        Itinerary i1 = (Itinerary)itins.get(1);

        assertEquals(102, i0.getFlights().get(0).getFlightNumber(), "Itinerary 0: Flight 0: Number should be 102");
        assertEquals(120, i0.getFlights().get(1).getFlightNumber(), "Itinerary 0: Flight 1: Number should be 120");

        assertEquals(101, i0.getFlights().get(0).getFlightNumber(), "Itinerary 1: Flight 0: Number should be 101");
        assertEquals(120, i0.getFlights().get(1).getFlightNumber(), "Itinerary 1: Flight 1: Number should be 120");
   }
}