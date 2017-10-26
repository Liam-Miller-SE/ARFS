import src.Flight;
class Test
{
  boolean ASSERT_EQUAL(int a, int b)
  {
    return a == b;
  }
  boolean ASSERT_EQUAL(String a, String b)
  {
    return a.equals(b);
  }
  boolean ASSERT_EQUAL(double a, double b)
  {
    return a == b;
  }
  boolean ASSERT_EQUAL(Flight a, Flight b)
  {
    boolean o = a.getOrigin().equals(b.getOrigin());
    o &= a.getDestination().equals(b.getDestinatoin());
    o &= a.getArrival().equals(b.getArrival());
    o &= a.getDeparture().equals(b.getDeparture());
    o &= a.getFlightNumber().equals(b.getFlightNumber());
    o &= a.getAirfare().equals(b.getAirfare());
    return o;
  }
}
