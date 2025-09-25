package com.company.authservice.service;

import com.company.authservice.dto.LoginRequestDTO;
import com.company.authservice.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;


    public AuthService(UserService userService) {
        this.userService = userService;
    }


    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        Optional<String> token =userService
                .findByEmail(loginRequestDTO.getEmail())
                .filter(u->passwordEncoder.matches(loginRequestDTO.getPassword(),
                        u.getPassword())
                        .map(u- jwtUtil.generateToken(u.getEmail(),u.getRole()));

                return token;

    }

}
