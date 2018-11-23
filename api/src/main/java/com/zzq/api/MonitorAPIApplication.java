package com.zzq.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.zzq.dao.mapper"})
public class MonitorAPIApplication {

  public static void main(String[] args) {
    SpringApplication.run(MonitorAPIApplication.class, args);
  }
}
