package com.company.patientservice.service;
import com.company.patientservice.dto.PatientRequestDTO;
import com.company.patientservice.dto.PatientResponseDTO;
import com.company.patientservice.exception.EmailAlreadyExistsException;
import com.company.patientservice.exception.PatientNotFoundException;
import com.company.patientservice.grpc.BillingServiceGrpcClient;
import com.company.patientservice.mapper.PatientMapper;
import com.company.patientservice.model.Patient;
import com.company.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class PatientService {
  private final PatientRepository patientRepository;
  private final BillingServiceGrpcClient billingServiceGrpcClient;


  public List<PatientResponseDTO> getPatients() {
    List<Patient> patients = patientRepository.findAll();

    List<PatientResponseDTO> patientResponseDTOs =
            patients.stream().map(PatientMapper::toDTO).toList();
    return patientResponseDTOs;
  }

  public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
    if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
      throw new EmailAlreadyExistsException("Patient already exists" + " with this email " + patientRequestDTO.getEmail());
    }

    Patient newpatient=patientRepository.save(PatientMapper.toModel(patientRequestDTO));

    billingServiceGrpcClient.createBillingAccount
            (newpatient.getId().toString(),newpatient.getName(),newpatient.getEmail());
    return PatientMapper.toDTO(newpatient);
  }

  public PatientResponseDTO updatingPatient(UUID id, PatientRequestDTO patientRequestDTO){
      Patient patient = patientRepository.findById(id)
              .orElseThrow(() -> new PatientNotFoundException("Patient not found" +
              " with this id " + id));

    if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
      throw new EmailAlreadyExistsException("Patient already exists" + " with this email " + patientRequestDTO.getEmail());

    }
    patient.setName(patientRequestDTO.getName());
    patient.setAddress(patientRequestDTO.getAddress());
    patient.setEmail(patientRequestDTO.getEmail());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

      Patient updatedPatient = patientRepository.save(patient);
      return PatientMapper.toDTO(updatedPatient);
  }

  public void deletePatient(UUID id) {
      patientRepository.deleteById(id);
  }




}
