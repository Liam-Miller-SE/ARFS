package test;

import main.Airport;
import main.Flight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by liam on 11/10/17.
 */
class TFlight
{
    static Flight tester;
    static Airport from = new Airport("FRM", "FROM_CITY");
    static Airport to = new Airport("TO_", "TO_CITY");

    @BeforeAll
    static void setup()
    {
        LocalTime start = LocalTime.of(12, 00);
        LocalTime end = LocalTime.of(17, 30);
        tester = new Flight(from, to, start, end, 123, 400.00);
    }

    @Test
    void calculateDelay()
    {

    }

    @Test
    void getOrigin()
    {
        assertEquals(tester.getOrigin(), from, "Origin airport incorrect!");
    }

    @Test
    void getDestination()
    {
        assertEquals(tester.getDestination(), to, "Destination airport incorrect!");
    }

    @Test
    void getArrival()
    {
        assertEquals(tester.getArrival().getHour(), 17, "Arrival hour incorrect");
        assertEquals(tester.getArrival().getMinute(), 30, "Arrival minute incorrect");
    }

    @Test
    void getDeparture()
    {
        assertEquals(tester.getDeparture().getHour(), 12, "Departure hour incorrect");
        assertEquals(tester.getDeparture().getMinute(), 00, "Departure minute incorrect");
    }

    @Test
    void getFlightNumber()
    {
        assertEquals(tester.getFlightNumber(), 123, "Flight number incorrect");
    }

    @Test
    void getAirfare()
    {
        assertEquals(tester.getAirfare(), 400.00, "Airfare incorrect");
    }

}