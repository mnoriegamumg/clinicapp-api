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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PacienteServiceImpl implements PacienteService {

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

    private PacienteResponseDTO convertirAResponse(Paciente paciente) {
        return PacienteResponseDTO.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .nombreCompleto(paciente.getNombreCompleto())
                .email(paciente.getEmail())
                .telefono(paciente.getTelefono())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .direccion(paciente.getDireccion())
                .createdAt(paciente.getCreatedAt())
                .build();
    }
}
