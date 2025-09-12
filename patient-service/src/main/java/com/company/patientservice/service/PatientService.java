package com.company.patientservice.service;

import com.company.patientservice.dto.PatientResponseDTO;
import com.company.patientservice.mapper.PatientMapper;
import com.company.patientservice.model.Patient;
import com.company.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

  private final PatientRepository patientRepository;

  public List<PatientResponseDTO> getPatients() {
    List<Patient> patients = patientRepository.findAll();

    List<PatientResponseDTO> patientResponseDTOs =
            patients.stream().map(PatientMapper::toDTO).toList();
    return patientResponseDTOs;
  }
}
