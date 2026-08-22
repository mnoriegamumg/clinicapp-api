package com.clinicapp.clinicapp_api.dtos;
// ============================================================
// RESPONSE DTO: AuthResponseDTO
// ============================================================

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String username;
    private String rol;
    private String nombreCompleto;
    private Long userId;
}
