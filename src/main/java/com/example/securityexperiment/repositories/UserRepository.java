package com.example.securityexperiment.repositories;

import com.example.securityexperiment.entities.GenericUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<GenericUser, Integer> {
    Optional<GenericUser> findByUsername(String username);
}
