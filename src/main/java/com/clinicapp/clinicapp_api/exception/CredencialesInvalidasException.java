package com.clinicapp.clinicapp_api.exception;
// ============================================================
// EXCEPCIÓN: CredencialesInvalidasException
// ============================================================

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException(String message) {
        super(message);
    }
}