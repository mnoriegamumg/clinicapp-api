package com.clinicapp.clinicapp_api.dtos;
// ============================================================
// RESPONSE DTO: UsuarioResponseDTO
// ============================================================
// NOTA: nunca expone la contraseña (ni siquiera el hash).

import com.clinicapp.clinicapp_api.entity.enums.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String username;
    private RolUsuario rol;
    private Boolean activo;
    private Long medicoId;
    private String medicoNombreCompleto;
    private LocalDateTime createdAt;
}
