package com.example.securityexperiment.services;

import com.example.securityexperiment.DTOs.LoginResponseDTO;
import com.example.securityexperiment.entities.Role;
import com.example.securityexperiment.entities.User;
import com.example.securityexperiment.repositories.RoleRepository;
import com.example.securityexperiment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User registerUser(String username, String password, String userAuthority){
        // hash the password
        String encodedPassword = passwordEncoder.encode(password);
        // assign role based on what's been passed in
        Role userRole = roleRepository.findByAuthority(userAuthority).orElse(null);

        if(userRole == null) {
            throw new IllegalArgumentException("Invalid role specified: " + userAuthority);
        }

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new User(0, username, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(String username, String password){

        try{ // try to authenticate by checking the credentials against the provider - database
            // check that the username and password is create
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            // if the username and password is correct - generate jwt
            String token = jwtService.generateJwt(auth);
            // get the user by the username and return the user and the token back to the frontend
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

}