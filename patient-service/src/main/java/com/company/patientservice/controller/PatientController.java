package com.company.patientservice.controller;

import com.company.patientservice.dto.PatientResponseDTO;
import com.company.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
     List<PatientResponseDTO> patients = patientService.getPatients();
     return ResponseEntity.ok().body(patients);

    }
    @PostMapping(){

    }




}
