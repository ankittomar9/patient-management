package com.company.patientservice;

import com.company.patientservice.controller.PatientController;
import com.company.patientservice.dto.PatientRequestDTO;
import com.company.patientservice.dto.PatientResponseDTO;
import com.company.patientservice.exception.EmailAlreadyExistsException;
import com.company.patientservice.exception.PatientNotFoundException;
import com.company.patientservice.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientRequestDTO patientRequestDTO;
    private PatientResponseDTO patientResponseDTO;
    private UUID patientId;

    @BeforeEach
    void setUp() {
        patientId = UUID.randomUUID();

        patientRequestDTO = new PatientRequestDTO();
        patientRequestDTO.setName("John Doe");
        patientRequestDTO.setEmail("john.doe@example.com");
        patientRequestDTO.setAddress("123 Main St");
        patientRequestDTO.setDateOfBirth(String.valueOf(LocalDate.of(1990, 1, 1)));
        patientRequestDTO.setRegisteredDate(String.valueOf(LocalDate.now()));

        patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(String.valueOf(patientId));
        patientResponseDTO.setName("John Doe");
        patientResponseDTO.setEmail("john.doe@example.com");
        patientResponseDTO.setAddress("123 Main St");
        patientResponseDTO.setDateOfBirth(String.valueOf(LocalDate.of(1990, 1, 1)));

    }

    @Test
    void getPatients_shouldReturnListOfPatients() throws Exception {
        List<PatientResponseDTO> patients = Collections.singletonList(patientResponseDTO);
        when(patientService.getPatients()).thenReturn(patients);

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(patientId.toString()))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void createPatient_shouldCreatePatientSuccessfully() throws Exception {
        when(patientService.createPatient(any(PatientRequestDTO.class))).thenReturn(patientResponseDTO);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientId.toString()))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void createPatient_whenEmailExists_shouldReturnBadRequest() throws Exception {
        when(patientService.createPatient(any(PatientRequestDTO.class)))
                .thenThrow(new EmailAlreadyExistsException("Email already exists"));

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email address already exists"));
    }

    @Test
    void createPatient_withInvalidData_shouldReturnBadRequest() throws Exception {
        // Assuming you have validation annotations like @NotBlank on your DTO fields
        PatientRequestDTO invalidRequest = new PatientRequestDTO(); // empty DTO

        // This test relies on @Valid in the controller and validation annotations in PatientRequestDTO.
        // The response will be handled by your GlobalExceptionHandler.
        // For this to work, PatientRequestDTO needs annotations like @NotBlank, @Email, etc.
        // Example: @NotBlank(message = "Name is mandatory") private String name;
        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
        // You can add more specific assertions on the error messages if you have validations
        // .andExpect(jsonPath("$.name").value("Name is mandatory"));
    }


    @Test
    void updatePatient_shouldUpdatePatientSuccessfully() throws Exception {
        when(patientService.updatingPatient(eq(patientId), any(PatientRequestDTO.class))).thenReturn(patientResponseDTO);

        mockMvc.perform(put("/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void updatePatient_whenPatientNotFound_shouldReturnBadRequest() throws Exception {
        when(patientService.updatingPatient(eq(patientId), any(PatientRequestDTO.class)))
                .thenThrow(new PatientNotFoundException("Patient not found"));

        mockMvc.perform(put("/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Patient not found "));
    }

    @Test
    void updatePatient_whenEmailExistsForAnotherPatient_shouldReturnBadRequest() throws Exception {
        when(patientService.updatingPatient(eq(patientId), any(PatientRequestDTO.class)))
                .thenThrow(new EmailAlreadyExistsException("Email already exists"));

        mockMvc.perform(put("/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email address already exists"));
    }

    @Test
    void updatePatient_withInvalidData_shouldReturnBadRequest() throws Exception {
        // Create a DTO with invalid data to trigger validation
        PatientRequestDTO invalidRequest = new PatientRequestDTO();
        invalidRequest.setName(""); // Assuming @NotBlank
        invalidRequest.setEmail("not-an-email"); // Assuming @Email

        // The @RequestBody is not annotated with @Valid in your updatePatient method.
        // To make this test work as intended, you should add @Valid.
        // public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, @Valid @RequestBody PatientRequestDTO patientRequestDTO)

        mockMvc.perform(put("/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
        // If you add @Valid, you can assert the validation messages:
        // .andExpect(jsonPath("$.name").isNotEmpty())
        // .andExpect(jsonPath("$.email").isNotEmpty());
    }
}