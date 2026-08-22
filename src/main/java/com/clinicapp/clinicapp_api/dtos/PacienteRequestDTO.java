package com.clinicapp.clinicapp_api.dtos;

// ============================================================
// REQUEST DTO: PacienteRequestDTO
// ============================================================

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    @Pattern(regexp = "^[0-9\\-+() ]*$", message = "El teléfono debe contener solo números")
    private String telefono;

    private LocalDate fechaNacimiento;

    private String direccion;
}
