package com.clinicapp.clinicapp_api.dtos;
// ============================================================
// REQUEST DTO: UsuarioUpdateDTO (actualización de usuario)
// ============================================================

import com.clinicapp.clinicapp_api.entity.enums.RolUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar un usuario existente.
 * La contraseña es opcional: si viene vacía o null, se conserva la actual.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDTO {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;

    /**
     * Nueva contraseña (opcional). Si se envía, debe tener al menos 8 caracteres.
     */
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private RolUsuario rol;

    private Long medicoId;

    private Boolean activo;
}
