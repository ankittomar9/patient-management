package com.company.patientservice.controller;

import com.company.patientservice.dto.PatientRequestDTO;
import com.company.patientservice.dto.PatientResponseDTO;
import com.company.patientservice.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO){
      PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
      return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
                                                            @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.updatingPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }




}
