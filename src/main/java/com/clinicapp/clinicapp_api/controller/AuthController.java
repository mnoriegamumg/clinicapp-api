package com.clinicapp.clinicapp_api.controller;
// ============================================================
// CONTROLLER: AuthController
// ============================================================

import com.clinicapp.clinicapp_api.dtos.AuthResponseDTO;
import com.clinicapp.clinicapp_api.dtos.LoginRequestDTO;
import com.clinicapp.clinicapp_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
