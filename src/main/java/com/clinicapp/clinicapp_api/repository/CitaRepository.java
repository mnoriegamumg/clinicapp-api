package com.clinicapp.clinicapp_api.repository;
// ============================================================
// REPOSITORIO: CitaRepository
// ============================================================

import com.clinicapp.clinicapp_api.entity.Cita;
import com.clinicapp.clinicapp_api.entity.enums.EstadoCita;
import com.clinicapp.clinicapp_api.utils.Queries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMedicoIdAndFechaHoraBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fin);

    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByMedicoId(Long medicoId);

    List<Cita> findByEstado(EstadoCita estado);

    Optional<Cita> findByMedicoIdAndFechaHora(Long medicoId, LocalDateTime fechaHora);

    @Query(value = Queries.VERIFICAR_DISPONIBILIDAD, nativeQuery = true)
    Long verificarDisponibilidad(@Param("medicoId") Long medicoId, @Param("fechaHora") LocalDateTime fechaHora);

    @Query(value = Queries.LISTAR_CITAS_POR_MEDICO_Y_FECHA, nativeQuery = true)
    List<Cita> listarCitasPorMedicoYFecha(@Param("medicoId") Long medicoId, @Param("fecha") String fecha);

    @Query(value = Queries.LISTAR_CITAS_POR_RANGO_FECHAS, nativeQuery = true)
    List<Cita> listarCitasPorRangoFechas(@Param("inicio") String inicio, @Param("fin") String fin);

    @Query(value = Queries.CONTAR_CITAS_POR_ESTADO, nativeQuery = true)
    Long contarCitasPorEstado(@Param("estado") String estado);

    @Query(value = Queries.CONTAR_CITAS_POR_MEDICO, nativeQuery = true)
    Long contarCitasPorMedico(@Param("medicoId") Long medicoId);

    @Query(value = Queries.CONTAR_CITAS_HOY, nativeQuery = true)
    Long contarCitasHoy();

    @Query(value = Queries.CITAS_DEL_DIA, nativeQuery = true)
    List<Object[]> citasDelDia();
}
