package com.clinicapp.clinicapp_api.service;
// ============================================================
// SERVICE: PacienteService
// ============================================================

import com.clinicapp.clinicapp_api.dtos.PacienteRequestDTO;
import com.clinicapp.clinicapp_api.dtos.PacienteResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface PacienteService {

    PacienteResponseDTO crear(PacienteRequestDTO request);

    PacienteResponseDTO actualizar(Long id, PacienteRequestDTO request);

    void eliminar(Long id);

    PacienteResponseDTO obtenerPorId(Long id);

    List<PacienteResponseDTO> listarTodos();

    List<PacienteResponseDTO> buscarPorNombre(String busqueda);

    Long contarTotal();


    PacienteResponseDTO obtenerPorDpi(String dpi);

    boolean existeDpi(String dpi);

    /**
     * Buscar pacientes creados entre dos fechas (solo fecha, sin hora)
     */
    List<PacienteResponseDTO> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Buscar pacientes creados en una fecha específica
     */
    List<PacienteResponseDTO> buscarPorFecha(LocalDate fecha);
}