package com.clinicapp.clinicapp_api.exception;
// ============================================================
// EXCEPCIÓN: ResourceNotFoundException
// ============================================================

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}