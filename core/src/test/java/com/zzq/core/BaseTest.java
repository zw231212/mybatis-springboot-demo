package com.zzq.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
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
  public void testFormatDate() throws ParseException {
    String dateStr = "Mon, 13 Jun 2016 02:50:08 GMT";
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.UK);
    Date date = sdf.parse(dateStr);
    System.out.println(date);
  }

  @Test
  public void testDateTime(){
    Date date = new Date();
    System.out.println(date.getTime());
    System.out.println(System.currentTimeMillis());
  }

  @Test
  public void testNull(){
    System.out.println(StringUtils.isNoneBlank(""));
  }


  @Test
  public void testUserDir(){
    System.out.println(System.getProperty("user.dir"));
  }

}
