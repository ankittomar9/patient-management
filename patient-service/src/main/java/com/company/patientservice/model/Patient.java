package com.company.patientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Patient {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @NotNull
        private String name;

        @NotNull
        @Email
        @Column(unique = true)
        private String email;

        @NotNull
        private String address;

        @NotNull
        private LocalDate dateOfBirth;
        @NotNull
        private LocalDate registeredDate;



}
