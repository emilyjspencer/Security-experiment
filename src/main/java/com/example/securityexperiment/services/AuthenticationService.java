package com.example.securityexperiment.services;

import com.example.securityexperiment.DTOs.LoginResponseDTO;
import com.example.securityexperiment.entities.GenericUser;
import com.example.securityexperiment.entities.Role;
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
    private JwtService tokenService;

    public GenericUser registerUser(String username, String password, String roleAuthority) {

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority(roleAuthority).orElse(null);

        if (userRole == null) {
            // Handle the case where the specified role does not exist
            throw new IllegalArgumentException("Invalid role specified: " + roleAuthority);
        }

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return userRepository.save(new GenericUser(0, username, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

}
