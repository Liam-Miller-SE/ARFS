package test;

import main.Airport;
import main.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by liam on 11/11/17.
 */
class TAirport
{
    static Airport tester;
    static ArrayList<Integer> temps;
    static ArrayList<String> weather;
    @BeforeEach
    void setUp()
    {
        temps = new ArrayList<Integer>();
        temps.add(20);
        temps.add(25);
        temps.add(15);
        temps.add(31);
        weather = new ArrayList<String>();
        weather.add("Rainy");
        weather.add("Clear");
        weather.add("Snowy");
        weather.add("Sunny");
        tester = new Airport("CIT", temps, weather,"CITY_NAME");
    }

    @Test
    void getCode()
    {
        assertEquals("CIT", tester.getCode(), "Airport code incorrect");
    }

    @Test
    void getTemp()
    {

    }

    @Test
    void getSTemperature()
    {

    }

    @Test
    void getTemperature()
    {

    }

    @Test
    void getWeather()
    {

    }

    @Test
    void getWeath()
    {

    }

    @Test
    void getCity()
    {
        assertEquals("CITY_NAME", tester.getCity(), "City name is incorrect");
    }

    @Test
    void getDelays()
    {
        assertEquals("0", tester.getDelays(), "Delay not instantiated to 0");
    }

    @Test
    void getConnections()
    {
        assertEquals("0", tester.getConnections(), "Connections not instantiated to 0");
    }

    //@Test
    void setCode()
    {
        //FUNCTION NOT IMPLEMENTED
    }

    @Test
    void setTemperature()
    {

    }

    @Test
    void setWeather()
    {

    }

    @Test
    void setCity()
    {
        tester.setCity("NEW_CITY");
        assertEquals("NEW_CITY", tester.getCity(), "New city name not set correctly");
    }

    //Not a test, but used by tests that need flights to be added
    void setupWithFlights()
    {
        Airport dest = new Airport("DS0", "DESTINATION0");
        LocalTime depart = LocalTime.of(7, 30);
        LocalTime arrive = LocalTime.of(10, 45);
        int num = 140;
        double cost = 300.00;
        Flight f = new Flight(tester, dest, depart, arrive, num, cost);
        tester.addFlight(f);

        Airport dest1 = new Airport("DS1", "DESTINATION1");
        LocalTime depart1 = LocalTime.of(3, 30);
        LocalTime arrive1 = LocalTime.of(5, 00);
        int num1 = 150;
        double cost1 = 200.00;
        Flight f1 = new Flight(tester, dest1, depart1, arrive1, num1, cost1);
        tester.addFlight(f1);

        Airport dest2 = new Airport("DS2", "DESTINATION2");
        LocalTime depart2 = LocalTime.of(5, 00);
        LocalTime arrive2 = LocalTime.of(17, 45);
        int num2 = 160;
        double cost2 = 700.00;
        Flight f2 = new Flight(tester, dest2, depart2, arrive2, num2, cost2);
        tester.addFlight(f2);
    }

    @Test
    void addFlight()
    {
        Airport dest = new Airport("DST", "DESTINATION");
        LocalTime depart = LocalTime.of(7, 30);
        LocalTime arrive = LocalTime.of(10, 45);
        int num = 101;
        double cost = 200.00;
        Flight f = new Flight(tester, dest, depart, arrive, num, cost);
        tester.addFlight(f);
        assertTrue(tester.getFlights().contains(f), "Flight not added");
    }

    @Test
    void getFlights()
    {
        setupWithFlights();
        assertEquals(3, tester.getFlights().size(), "Wrong number of flights");
        Flight badF = new Flight(tester, null, null, null, 0, 0.0);
        tester.addFlight(badF);
        assertEquals(4, tester.getFlights().size(), "New flight not returned");
    }

    @Test
    void setConnection()
    {
        tester.setConnection(45);
        assertEquals("45", tester.getConnections(), "Connecting time not set");
    }

    @Test
    void setDelay()
    {
        tester.setDelay(120);
        assertEquals("120", tester.getDelays(), "Delay not set correctly");
    }

}