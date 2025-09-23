package com.company.authservice.repository;

import com.company.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<String, UUID> {
    Optional<User> findByEmail(String email);

    //Optional<User> findByEmailAndPassword(String email, String password);
}
