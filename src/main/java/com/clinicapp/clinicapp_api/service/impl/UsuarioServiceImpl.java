package com.clinicapp.clinicapp_api.service.impl;
// ============================================================
// SERVICE IMPL: UsuarioServiceImpl
// ============================================================

import com.clinicapp.clinicapp_api.dtos.UsuarioRequestDTO;
import com.clinicapp.clinicapp_api.dtos.UsuarioResponseDTO;
import com.clinicapp.clinicapp_api.dtos.UsuarioUpdateDTO;
import com.clinicapp.clinicapp_api.entity.Medico;
import com.clinicapp.clinicapp_api.entity.Usuario;
import com.clinicapp.clinicapp_api.exception.ResourceNotFoundException;
import com.clinicapp.clinicapp_api.repository.MedicoRepository;
import com.clinicapp.clinicapp_api.repository.UsuarioRepository;
import com.clinicapp.clinicapp_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {
        // Verificar que el username no exista
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con ese username");
        }

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol())
                .medico(resolverMedico(request.getMedicoId()))
                .activo(request.getActivo() == null ? Boolean.TRUE : request.getActivo())
                .build();

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirAResponse(guardado);
    }

    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioUpdateDTO request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        // Si cambia el username, verificar que no colisione con otro usuario
        if (!usuario.getUsername().equals(request.getUsername())
                && usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con ese username");
        }

        usuario.setUsername(request.getUsername());
        usuario.setRol(request.getRol());
        usuario.setMedico(resolverMedico(request.getMedicoId()));

        // La contraseña solo se actualiza si se envía una nueva
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // El estado solo se cambia si se envía explícitamente
        if (request.getActivo() != null) {
            usuario.setActivo(request.getActivo());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirAResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return convertirAResponse(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> buscarPorUsername(String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return listarTodos();
        }
        return usuarioRepository.buscarPorUsername(busqueda.trim())
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Long contarTotal() {
        return usuarioRepository.contarTotalUsuarios();
    }

    @Override
    public UsuarioResponseDTO cambiarEstado(Long id, boolean activo) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        usuario.setActivo(activo);
        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirAResponse(actualizado);
    }

    /**
     * Busca la entidad Medico por id. Devuelve null si medicoId es null.
     */
    private Medico resolverMedico(Long medicoId) {
        if (medicoId == null) {
            return null;
        }
        return medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con ID: " + medicoId));
    }

    private UsuarioResponseDTO convertirAResponse(Usuario usuario) {
        Medico medico = usuario.getMedico();
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .rol(usuario.getRol())
                .activo(usuario.getActivo())
                .medicoId(medico != null ? medico.getId() : null)
                .medicoNombreCompleto(medico != null ? medico.getNombreCompleto() : null)
                .createdAt(usuario.getCreatedAt())
                .build();
    }
}
