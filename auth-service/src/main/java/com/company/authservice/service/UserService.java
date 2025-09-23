package com.company.authservice.service;

import com.company.authservice.model.User;
import com.company.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


        public Optional<User> findByEmail(String email){
            return userRepository.findByEmail(email);

        }


}
