package com.clinicapp.clinicapp_api.dtos;
// ============================================================
// REQUEST DTO: MedicoRequestDTO
// ============================================================

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    private String telefono;

    private String horarioInicio;

    private String horarioFin;
}
