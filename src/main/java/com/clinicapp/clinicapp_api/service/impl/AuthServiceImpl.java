package com.clinicapp.clinicapp_api.service.impl;
// ============================================================
// SERVICE IMPL: AuthServiceImpl
// ============================================================

import com.clinicapp.clinicapp_api.dtos.AuthResponseDTO;
import com.clinicapp.clinicapp_api.dtos.LoginRequestDTO;
import com.clinicapp.clinicapp_api.entity.Usuario;
import com.clinicapp.clinicapp_api.exception.CredencialesInvalidasException;
import com.clinicapp.clinicapp_api.repository.UsuarioRepository;
import com.clinicapp.clinicapp_api.security.JwtTokenProvider;
import com.clinicapp.clinicapp_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtTokenProvider  jwtTokenProvider;
    private final PasswordEncoder   passwordEncoder;

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Buscar usuario por username
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new CredencialesInvalidasException("Usuario o contraseña incorrectos"));

        log.info(usuario.toString());
        // Verificar si el usuario está activo
        if (!usuario.getActivo()) {
            throw new CredencialesInvalidasException("Usuario inactivo, contacte al administrador");
        }

        // Verificar contraseña (usando el password encoder)
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos");
        }

        // Generar token JWT
        String token = jwtTokenProvider.generateToken(usuario.getUsername(), usuario.getRol().name());

        // Construir nombre completo (si es médico, tomar de la entidad Medico)
        String nombreCompleto = "";
        if (usuario.getMedico() != null) {
            nombreCompleto = usuario.getMedico().getNombreCompleto();
        } else {
            nombreCompleto = usuario.getUsername();
        }

        return AuthResponseDTO.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .nombreCompleto(nombreCompleto)
                .userId(usuario.getId())
                .build();
    }

    @Override
    public Boolean verificarCredenciales(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .map(usuario -> passwordEncoder.matches(password, usuario.getPassword()) && usuario.getActivo())
                .orElse(false);
    }

    @Override
    public String obtenerRol(String username) {
        return usuarioRepository.obtenerRolUsuario(username);
    }
}