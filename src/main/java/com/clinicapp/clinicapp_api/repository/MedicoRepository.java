package com.clinicapp.clinicapp_api.repository;
// ============================================================
// REPOSITORIO: MedicoRepository
// ============================================================

import com.clinicapp.clinicapp_api.entity.Medico;
import com.clinicapp.clinicapp_api.utils.Queries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Optional<Medico> findByEmail(String email);

    List<Medico> findByEspecialidad(String especialidad);

    @Query(value = Queries.BUSCAR_MEDICOS_POR_ESPECIALIDAD, nativeQuery = true)
    List<Medico> buscarPorEspecialidad(@Param("especialidad") String especialidad);

    @Query(value = Queries.CONTAR_MEDICOS_TOTAL, nativeQuery = true)
    Long contarTotalMedicos();

    @Query(value = Queries.LISTAR_ESPECIALIDADES, nativeQuery = true)
    List<String> listarEspecialidades();

    @Query(value = Queries.BUSCAR_MEDICOS_POR_NOMBRE, nativeQuery = true)
    List<Medico> buscarPorNombreCompleto(@Param("busqueda") String busqueda);
}