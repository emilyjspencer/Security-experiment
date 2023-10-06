package com.example.securityexperiment.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultant")
@CrossOrigin("*")
public class ConsultantController {

    @GetMapping("/")
    public String consultantGreeting(){

        return "Consultant access";
    }

}