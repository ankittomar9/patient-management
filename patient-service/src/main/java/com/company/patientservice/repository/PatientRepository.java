package com.company.patientservice.repository;

import com.company.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByEmail(String email);
    // You can add custom query methods here if needed
    boolean existsByEmailAndIdNot(String email, UUID id);



}
