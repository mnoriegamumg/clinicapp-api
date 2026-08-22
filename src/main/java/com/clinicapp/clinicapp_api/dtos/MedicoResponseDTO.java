package com.clinicapp.clinicapp_api.dtos;
// ============================================================
// RESPONSE DTO: MedicoResponseDTO
// ============================================================

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicoResponseDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String especialidad;
    private String email;
    private String telefono;
    private String horarioInicio;
    private String horarioFin;
    private LocalDateTime createdAt;
}
