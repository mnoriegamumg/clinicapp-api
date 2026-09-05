package com.clinicapp.clinicapp_api.dtos;
// ============================================================
// RESPONSE DTO: CitaResponseDTO
// ============================================================

import com.clinicapp.clinicapp_api.entity.enums.EstadoCita;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaResponseDTO {

    private Long id;
    private Long pacienteId;
    private String pacienteNombreCompleto;
    private Long medicoId;
    private String medicoNombreCompleto;
    private String medicoEspecialidad;
    private LocalDateTime fechaHora;
    private String        motivo;
    private EstadoCita    estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String diagnostico;
    private String comentariosMedico;
    private String tratamiento;
    private LocalDateTime fechaAtencion;
}
