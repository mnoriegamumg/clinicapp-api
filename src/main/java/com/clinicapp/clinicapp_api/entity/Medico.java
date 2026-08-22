package com.clinicapp.clinicapp_api.entity;
// ============================================================
// ENTIDAD: Medico
// ============================================================

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "medicos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "especialidad", nullable = false, length = 100)
    private String especialidad;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "horario_inicio")
    private String horarioInicio;

    @Column(name = "horario_fin")
    private String horarioFin;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }
}