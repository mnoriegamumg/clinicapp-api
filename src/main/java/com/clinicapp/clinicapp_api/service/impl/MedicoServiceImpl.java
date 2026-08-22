package com.clinicapp.clinicapp_api.service.impl;
// ============================================================
// SERVICE IMPL: MedicoServiceImpl
// ============================================================

import com.clinicapp.clinicapp_api.dtos.MedicoRequestDTO;
import com.clinicapp.clinicapp_api.dtos.MedicoResponseDTO;
import com.clinicapp.clinicapp_api.entity.Medico;
import com.clinicapp.clinicapp_api.exception.ResourceNotFoundException;
import com.clinicapp.clinicapp_api.repository.MedicoRepository;
import com.clinicapp.clinicapp_api.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;

    @Override
    public MedicoResponseDTO crear(MedicoRequestDTO request) {
        // Verificar si el email ya existe
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            medicoRepository.findByEmail(request.getEmail())
                    .ifPresent(m -> {
                        throw new RuntimeException("Ya existe un médico con ese email");
                    });
        }

        Medico medico = Medico.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .especialidad(request.getEspecialidad())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .horarioInicio(request.getHorarioInicio())
                .horarioFin(request.getHorarioFin())
                .build();

        Medico guardado = medicoRepository.save(medico);
        return convertirAResponse(guardado);
    }

    @Override
    public MedicoResponseDTO actualizar(Long id, MedicoRequestDTO request) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con ID: " + id));

        medico.setNombre(request.getNombre());
        medico.setApellido(request.getApellido());
        medico.setEspecialidad(request.getEspecialidad());
        medico.setEmail(request.getEmail());
        medico.setTelefono(request.getTelefono());
        medico.setHorarioInicio(request.getHorarioInicio());
        medico.setHorarioFin(request.getHorarioFin());

        Medico actualizado = medicoRepository.save(medico);
        return convertirAResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Médico no encontrado con ID: " + id);
        }
        medicoRepository.deleteById(id);
    }

    @Override
    public MedicoResponseDTO obtenerPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con ID: " + id));
        return convertirAResponse(medico);
    }

    @Override
    public List<MedicoResponseDTO> listarTodos() {
        return medicoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicoResponseDTO> buscarPorEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            return listarTodos();
        }
        return medicoRepository.buscarPorEspecialidad(especialidad.trim())
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicoResponseDTO> buscarPorNombre(String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return listarTodos();
        }
        return medicoRepository.buscarPorNombreCompleto(busqueda.trim())
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listarEspecialidades() {
        return medicoRepository.listarEspecialidades();
    }

    @Override
    public Long contarTotal() {
        return medicoRepository.contarTotalMedicos();
    }

    private MedicoResponseDTO convertirAResponse(Medico medico) {
        return MedicoResponseDTO.builder()
                .id(medico.getId())
                .nombre(medico.getNombre())
                .apellido(medico.getApellido())
                .nombreCompleto(medico.getNombreCompleto())
                .especialidad(medico.getEspecialidad())
                .email(medico.getEmail())
                .telefono(medico.getTelefono())
                .horarioInicio(medico.getHorarioInicio())
                .horarioFin(medico.getHorarioFin())
                .createdAt(medico.getCreatedAt())
                .build();
    }
}