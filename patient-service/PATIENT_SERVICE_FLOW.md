# Patient Service Flow Documentation

## Overview
This document outlines the flow and architecture of the Patient Service, which manages patient data through a RESTful API.

## Architecture

### Components

1. **Controller Layer** (`PatientController`)
   - Handles HTTP requests and responses
   - Maps endpoints to service methods
   - Performs input validation using `@Valid`
   - Returns appropriate HTTP responses

2. **Service Layer** (`PatientService`)
   - Contains business logic
   - Handles data validation and business rules
   - Interacts with the repository layer
   - Throws custom exceptions for business rule violations

3. **Repository Layer** (`PatientRepository`)
   - Extends `JpaRepository` for CRUD operations
   - Provides database access methods
   - Includes custom query methods (e.g., `existsByEmail`)

4. **Exception Handling** (`GlobalExceptionHandler`)
   - Global exception handler using `@ControllerAdvice`
   - Handles validation exceptions
   - Manages custom business exceptions (e.g., `EmailAlreadyExistsException`)
   - Returns structured error responses

5. **Data Transfer Objects (DTOs)**
   - `PatientRequestDTO`: For receiving patient data in API requests
   - `PatientResponseDTO`: For sending patient data in API responses

## API Endpoints

### 1. Create Patient
- **Endpoint**: `POST /patients`
- **Request Body**: `PatientRequestDTO` (JSON)
- **Flow**:
  1. Request is validated by Spring's `@Valid`
  2. Service checks if email already exists
  3. If email exists, throws `EmailAlreadyExistsException`
  4. If valid, saves patient to database
  5. Returns created patient data as `PatientResponseDTO`

### 2. Get All Patients
- **Endpoint**: `GET /patients`
- **Response**: List of `PatientResponseDTO`
- **Flow**:
  1. Retrieves all patients from database
  2. Maps entities to DTOs
  3. Returns list of patient DTOs

## Exception Handling

### 1. MethodArgumentNotValidException
- **Trigger**: When request validation fails
- **Response**: 400 Bad Request with field-level error messages

### 2. EmailAlreadyExistsException
- **Trigger**: When creating a patient with an existing email
- **Response**: 400 Bad Request with error message

## Data Flow

```
Client → Controller → Service → Repository → Database
                              ↑
                              └→ DTO Mapper
```

## Dependencies
- Spring Boot Web
- Spring Data JPA
- H2 Database (embedded)
- Lombok
- Validation API
- SLF4J for logging

## Configuration
- Server Port: 4000
- Database: H2 in-memory
- Logging: INFO level for root
