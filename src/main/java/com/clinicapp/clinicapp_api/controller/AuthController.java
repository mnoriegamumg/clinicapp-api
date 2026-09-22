package com.clinicapp.clinicapp_api.controller;
// ============================================================
// CONTROLLER: AuthController
// ============================================================

import com.clinicapp.clinicapp_api.dtos.AuthResponseDTO;
import com.clinicapp.clinicapp_api.dtos.CambiarPasswordDTO;
import com.clinicapp.clinicapp_api.dtos.LoginRequestDTO;
import com.clinicapp.clinicapp_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Permite al usuario AUTENTICADO cambiar su propia contraseña.
     * El usuario se toma del token (contexto de seguridad), no del body,
     * de modo que solo puede cambiar la suya.
     * Ejemplo body: {"passwordActual":"12345678","passwordNueva":"123456789"}
     */
    @PostMapping("/cambiar-password")
    public ResponseEntity<Map<String, String>> cambiarPassword(
            @Valid @RequestBody CambiarPasswordDTO request,
            Authentication authentication) {

        String username = authentication.getName();
        authService.cambiarPassword(username, request.getPasswordActual(), request.getPasswordNueva());

        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada correctamente"));
    }
}
