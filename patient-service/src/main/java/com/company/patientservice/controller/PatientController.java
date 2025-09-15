package com.company.patientservice.controller;

import com.company.patientservice.dto.PatientRequestDTO;
import com.company.patientservice.dto.PatientResponseDTO;
import com.company.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name="Patient",description="API for Managing Patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "Get all Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
     List<PatientResponseDTO> patients = patientService.getPatients();
     return ResponseEntity.ok().body(patients);

    }
    @PostMapping
    @Operation(summary = "Create a Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO){
      PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
      return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
                                                     @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.updatingPatient(id,patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<String> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        log.info("Patient deleted successfully");

        return ResponseEntity.ok(Collections.singletonMap("message", "Patient deleted successfully").toString());

    }




}
