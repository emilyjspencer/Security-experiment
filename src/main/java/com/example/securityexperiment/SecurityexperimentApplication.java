package com.example.securityexperiment;

import com.example.securityexperiment.entities.GenericUser;
import com.example.securityexperiment.entities.Role;
import com.example.securityexperiment.repositories.RoleRepository;
import com.example.securityexperiment.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SecurityexperimentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityexperimentApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode) {
		return args -> {
		   //admin role?
			if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

			// create and save an adminrole to the database with the role of ADMIN
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			// save that role to the database
			roleRepository.save(new Role("USER"));
			// add admin role to the list of riles
			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			// create a user that is of type admin
			GenericUser admin = new GenericUser(1, "admin", passwordEncode.encode("password"), roles);
			// save that to the database
			userRepository.save(admin);
		};

	}}
