package com.clinicapp.clinicapp_api.service;
// ============================================================
// SERVICE: AuthService
// ============================================================

import com.clinicapp.clinicapp_api.dtos.AuthResponseDTO;
import com.clinicapp.clinicapp_api.dtos.LoginRequestDTO;

public interface AuthService {

    AuthResponseDTO login(LoginRequestDTO loginRequest);

    Boolean verificarCredenciales(String username, String password);

    String obtenerRol(String username);
}