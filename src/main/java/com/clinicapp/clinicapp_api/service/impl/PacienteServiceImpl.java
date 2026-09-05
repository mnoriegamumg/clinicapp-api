package com.clinicapp.clinicapp_api.service.impl;
// ============================================================
// SERVICE IMPL: PacienteServiceImpl
// ============================================================

import com.clinicapp.clinicapp_api.dtos.PacienteRequestDTO;
import com.clinicapp.clinicapp_api.dtos.PacienteResponseDTO;
import com.clinicapp.clinicapp_api.entity.Paciente;
import com.clinicapp.clinicapp_api.exception.ResourceNotFoundException;
import com.clinicapp.clinicapp_api.repository.PacienteRepository;
import com.clinicapp.clinicapp_api.service.PacienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final PacienteRepository pacienteRepository;

    @Override
    public PacienteResponseDTO crear(PacienteRequestDTO request) {
        // Verificar si el email ya existe
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            pacienteRepository.findByEmail(request.getEmail())
                    .ifPresent(p -> {
                        throw new RuntimeException("Ya existe un paciente con ese email");
                    });
        }

        Paciente paciente = Paciente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .fechaNacimiento(request.getFechaNacimiento())
                .direccion(request.getDireccion())
                .dpi(request.getDpi())
                .build();

        Paciente guardado = pacienteRepository.save(paciente);
        return convertirAResponse(guardado);
    }

    @Override
    public PacienteResponseDTO actualizar(Long id, PacienteRequestDTO request) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));

        paciente.setNombre(request.getNombre());
        paciente.setApellido(request.getApellido());
        paciente.setEmail(request.getEmail());
        paciente.setTelefono(request.getTelefono());
        paciente.setFechaNacimiento(request.getFechaNacimiento());
        paciente.setDireccion(request.getDireccion());
        paciente.setDpi(request.getDpi());

        Paciente actualizado = pacienteRepository.save(paciente);
        return convertirAResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    @Override
    public PacienteResponseDTO obtenerPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        return convertirAResponse(paciente);
    }

    @Override
    public List<PacienteResponseDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PacienteResponseDTO> buscarPorNombre(String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return listarTodos();
        }
        return pacienteRepository.buscarPorNombreCompleto(busqueda.trim())
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }


    @Override
    public Long contarTotal() {
        return pacienteRepository.contarTotalPacientes();
    }

    @Override
    public PacienteResponseDTO obtenerPorDpi(String dpi) {

        if (dpi == null || dpi.trim().isEmpty()) {
            throw new IllegalArgumentException("El DPI no puede estar vacío");
        }

        Paciente paciente = pacienteRepository.findByDpi(dpi)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con DPI: " + dpi));


        return convertirAResponse(paciente);
    }

    @Override
    public boolean existeDpi(String dpi) {
        return pacienteRepository.existsByDpi(dpi);
    }


    private PacienteResponseDTO convertirAResponse(Paciente paciente) {
        return PacienteResponseDTO.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .dpi(paciente.getDpi())
                .email(paciente.getEmail())
                .telefono(paciente.getTelefono())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .direccion(paciente.getDireccion())
                .createdAt(paciente.getCreatedAt())
                .build();
    }

    @Override
    public List<PacienteResponseDTO> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Buscando pacientes entre {} y {}", fechaInicio, fechaFin);

        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        }

        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha fin");
        }

        String fechaInicioStr = fechaInicio.format(DATE_FORMATTER);
        String fechaFinStr = fechaFin.format(DATE_FORMATTER);

        List<Paciente> pacientes = pacienteRepository.buscarPorRangoFechas(fechaInicioStr, fechaFinStr);
        log.info("Se encontraron {} pacientes en el rango de fechas", pacientes.size());

        return pacientes.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PacienteResponseDTO> buscarPorFecha(LocalDate fecha) {
        log.info("Buscando pacientes creados en la fecha: {}", fecha);

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        String fechaStr = fecha.format(DATE_FORMATTER);
        List<Paciente> pacientes = pacienteRepository.buscarPorFecha(fechaStr);
        log.info("Se encontraron {} pacientes en la fecha {}", pacientes.size(), fecha);

        return pacientes.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }
}
