package com.clinicapp.clinicapp_api.dtos;
// ============================================================
// REQUEST DTO: UsuarioRequestDTO
// ============================================================

import com.clinicapp.clinicapp_api.entity.enums.RolUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private RolUsuario rol;

    /**
     * ID del médico asociado (opcional). Solo aplica normalmente cuando el rol es MEDICO.
     */
    private Long medicoId;

    /**
     * Estado del usuario. Si no se envía, se asume activo.
     */
    private Boolean activo;
}
