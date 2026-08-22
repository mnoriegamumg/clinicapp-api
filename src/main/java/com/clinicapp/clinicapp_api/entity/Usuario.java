package com.clinicapp.clinicapp_api.entity;
// ============================================================
// ENTIDAD: Usuario (para autenticación)
// ============================================================

import com.clinicapp.clinicapp_api.entity.enums.RolUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}