package com.clinicapp.clinicapp_api.service;
// ============================================================
// SERVICE: CitaService
// ============================================================


import com.clinicapp.clinicapp_api.dtos.CitaRequestDTO;
import com.clinicapp.clinicapp_api.dtos.CitaResponseDTO;
import com.clinicapp.clinicapp_api.entity.enums.EstadoCita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {

    CitaResponseDTO crear(CitaRequestDTO request);

    CitaResponseDTO actualizar(Long id, CitaRequestDTO request);

    CitaResponseDTO actualizarEstado(Long id, EstadoCita nuevoEstado);

    void eliminar(Long id);

    CitaResponseDTO obtenerPorId(Long id);

    List<CitaResponseDTO> listarTodas();

    List<CitaResponseDTO> listarPorPaciente(Long pacienteId);

    List<CitaResponseDTO> listarPorMedico(Long medicoId);

    List<CitaResponseDTO> listarPorMedicoYFecha(Long medicoId, LocalDate fecha);

    List<CitaResponseDTO> listarPorRangoFechas(LocalDate inicio, LocalDate fin);

    List<CitaResponseDTO> listarPorEstado(EstadoCita estado);

    Boolean verificarDisponibilidad(Long medicoId, LocalDateTime fechaHora);

    Long contarCitasPorEstado(EstadoCita estado);

    Long contarCitasPorMedico(Long medicoId);

    Long contarCitasHoy();

    List<Object[]> obtenerCitasDelDia();

    CitaResponseDTO actualizarDiagnostico(Long id, String diagnostico, String comentariosMedico, String tratamiento);
}