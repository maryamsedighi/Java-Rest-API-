package com.example.employee_app.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

   @GetMapping("/")
    public String home(){
       return "Hello";
   }
}
