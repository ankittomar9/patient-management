package com.company.authservice.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true,nullable = false)
    private String email;







}
