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
}
