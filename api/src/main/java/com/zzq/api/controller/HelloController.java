package com.zzq.api.controller;


import com.zzq.api.model.response.APIResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "")
public class HelloController {

  @RequestMapping(value = "/greet",name = "Hello")
  public APIResponse index() {
    return APIResponse.ok("Greetings from Spring Boot!");
  }

}
