package com.clinicapp.clinicapp_api.repository;
// ============================================================
// REPOSITORIO: PacienteRepository
// ============================================================

import com.clinicapp.clinicapp_api.entity.Paciente;
import com.clinicapp.clinicapp_api.utils.Queries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByEmail(String email);

    @Query(value = Queries.BUSCAR_PACIENTES_POR_NOMBRE, nativeQuery = true)
    List<Paciente> buscarPorNombreCompleto(@Param("busqueda") String busqueda);

    @Query(value = Queries.CONTAR_PACIENTES_TOTAL, nativeQuery = true)
    Long contarTotalPacientes();

    List<Paciente> findByNombreContainingOrApellidoContaining(String nombre, String apellido);
}
