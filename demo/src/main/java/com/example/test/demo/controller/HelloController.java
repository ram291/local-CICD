package com.example.test.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class HelloController {
   // a get method that returns a greeting message with a name parameter and mapped to /hello


   @GetMapping("/hello")
   public ResponseEntity<String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return ResponseEntity.ok(String.format("Hello, %s!", name));
   }
}


