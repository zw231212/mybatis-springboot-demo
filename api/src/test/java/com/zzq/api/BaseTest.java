package com.zzq.api;

import java.util.Enumeration;
import java.util.Properties;
import org.junit.Test;

public class BaseTest {

  @Test
  public void test(){
    Properties properties = System.getProperties();
    Enumeration<?> names = properties.propertyNames();
    while (names.hasMoreElements()){
      Object o = names.nextElement();
      System.out.println(o+">>>"+properties.get(o));
    }
  }

  @Test
  public void testMathCeil(){
    System.out.println(Math.ceil(5.2/2));
  }

}
