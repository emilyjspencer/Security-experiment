package com.example.securityexperiment.controllers;


import com.example.securityexperiment.DTOs.LoginResponseDTO;
import com.example.securityexperiment.DTOs.RegistrationDTO;
import com.example.securityexperiment.entities.GenericUser;
import com.example.securityexperiment.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public GenericUser registerUser(@RequestBody RegistrationDTO body, @RequestParam String roleAuthority) {
        return authenticationService.registerUser(body.getUsername(), body.getPassword(), roleAuthority);
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
