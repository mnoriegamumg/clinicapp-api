package com.clinicapp.clinicapp_api.service.impl;
// ============================================================
// SERVICE IMPL: CitaServiceImpl
// ============================================================

import com.clinicapp.clinicapp_api.dtos.CitaRequestDTO;
import com.clinicapp.clinicapp_api.dtos.CitaResponseDTO;
import com.clinicapp.clinicapp_api.dtos.PacienteRequestDTO;
import com.clinicapp.clinicapp_api.entity.Cita;
import com.clinicapp.clinicapp_api.entity.Medico;
import com.clinicapp.clinicapp_api.entity.Paciente;
import com.clinicapp.clinicapp_api.entity.enums.EstadoCita;
import com.clinicapp.clinicapp_api.exception.HorarioOcupadoException;
import com.clinicapp.clinicapp_api.exception.ResourceNotFoundException;
import com.clinicapp.clinicapp_api.repository.CitaRepository;
import com.clinicapp.clinicapp_api.repository.MedicoRepository;
import com.clinicapp.clinicapp_api.repository.PacienteRepository;
import com.clinicapp.clinicapp_api.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CitaServiceImpl implements CitaService {

    private final CitaRepository     citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository   medicoRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private PacienteServiceImpl pacienteService;

    @Override
    public CitaResponseDTO crear(CitaRequestDTO request) {
        // Verificar que el paciente existe
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + request.getPacienteId()));

        // Verificar que el médico existe
        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con ID: " + request.getMedicoId()));

        // Verificar disponibilidad del médico (regla de negocio)
        if (!verificarDisponibilidad(request.getMedicoId(), request.getFechaHora())) {
            throw new HorarioOcupadoException("El médico ya tiene una cita programada en esa fecha y hora");
        }

        Cita cita = Cita.builder()
                .paciente(paciente)
                .medico(medico)
                .fechaHora(request.getFechaHora())
                .motivo(request.getMotivo())
                .estado(EstadoCita.PENDIENTE)
                .build();

        Cita guardada = citaRepository.save(cita);
        return convertirAResponse(guardada);
    }

    @Override
    public CitaResponseDTO actualizar(Long id, CitaRequestDTO request) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));

        // Si se cambia la fecha/hora, verificar disponibilidad
        if (!cita.getFechaHora().equals(request.getFechaHora())) {
            if (!verificarDisponibilidad(request.getMedicoId(), request.getFechaHora())) {
                throw new HorarioOcupadoException("El médico ya tiene una cita programada en esa fecha y hora");
            }
        }

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado"));

        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFechaHora(request.getFechaHora());
        cita.setMotivo(request.getMotivo());

        Cita actualizada = citaRepository.save(cita);
        return convertirAResponse(actualizada);
    }

    @Override
    public CitaResponseDTO actualizarEstado(Long id, EstadoCita nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));

        // No se puede cancelar una cita ya atendida
        if (cita.getEstado() == EstadoCita.ATENDIDA && nuevoEstado == EstadoCita.CANCELADA) {
            throw new RuntimeException("No se puede cancelar una cita que ya fue atendida");
        }

        cita.setEstado(nuevoEstado);
        Cita actualizada = citaRepository.save(cita);
        return convertirAResponse(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cita no encontrada con ID: " + id);
        }
        citaRepository.deleteById(id);
    }

    @Override
    public CitaResponseDTO obtenerPorId(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        return convertirAResponse(cita);
    }

    @Override
    public List<CitaResponseDTO> listarTodas() {
        return citaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponseDTO> listarPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponseDTO> listarPorMedico(Long medicoId) {
        return citaRepository.findByMedicoId(medicoId)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponseDTO> listarPorMedicoYFecha(Long medicoId, LocalDate fecha) {
        String fechaStr = fecha.format(DATE_FORMATTER);
        return citaRepository.listarCitasPorMedicoYFecha(medicoId, fechaStr)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponseDTO> listarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        String inicioStr = inicio.format(DATE_FORMATTER);
        String finStr = fin.format(DATE_FORMATTER);
        return citaRepository.listarCitasPorRangoFechas(inicioStr, finStr)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponseDTO> listarPorEstado(EstadoCita estado) {
        return citaRepository.findByEstado(estado)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean verificarDisponibilidad(Long medicoId, LocalDateTime fechaHora) {
        return citaRepository.verificarDisponibilidad(medicoId, fechaHora) == 0;
    }

    @Override
    public Long contarCitasPorEstado(EstadoCita estado) {
        return citaRepository.contarCitasPorEstado(estado.name());
    }

    @Override
    public Long contarCitasPorMedico(Long medicoId) {
        return citaRepository.contarCitasPorMedico(medicoId);
    }

    @Override
    public Long contarCitasHoy() {
        return citaRepository.contarCitasHoy();
    }

    @Override
    public List<Object[]> obtenerCitasDelDia() {
        return citaRepository.citasDelDia();
    }

    private CitaResponseDTO convertirAResponse(Cita cita) {
        return CitaResponseDTO.builder()
                .id(cita.getId())
                .pacienteId(cita.getPaciente().getId())
                .pacienteNombreCompleto(cita.getPaciente().getNombreCompleto())
                .medicoId(cita.getMedico().getId())
                .medicoNombreCompleto(cita.getMedico().getNombreCompleto())
                .medicoEspecialidad(cita.getMedico().getEspecialidad())
                .fechaHora(cita.getFechaHora())
                .motivo(cita.getMotivo())
                .estado(cita.getEstado())
                .createdAt(cita.getCreatedAt())
                .updatedAt(cita.getUpdatedAt())
                .build();
    }
}