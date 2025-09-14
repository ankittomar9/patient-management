package com.company.patientservice.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String patientAlreadyExists) {
        super(patientAlreadyExists);
    }
}
