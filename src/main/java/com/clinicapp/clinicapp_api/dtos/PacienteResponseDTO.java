package com.clinicapp.clinicapp_api.dtos;
// ============================================================
// RESPONSE DTO: PacienteResponseDTO
// ============================================================

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponseDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String direccion;
    private LocalDateTime createdAt;
}