package com.clinicapp.clinicapp_api.service;
// ============================================================
// SERVICE: UsuarioService
// ============================================================

import com.clinicapp.clinicapp_api.dtos.UsuarioRequestDTO;
import com.clinicapp.clinicapp_api.dtos.UsuarioResponseDTO;
import com.clinicapp.clinicapp_api.dtos.UsuarioUpdateDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crear(UsuarioRequestDTO request);

    UsuarioResponseDTO actualizar(Long id, UsuarioUpdateDTO request);

    void eliminar(Long id);

    UsuarioResponseDTO obtenerPorId(Long id);

    List<UsuarioResponseDTO> listarTodos();

    List<UsuarioResponseDTO> buscarPorUsername(String busqueda);

    Long contarTotal();

    /**
     * Activar o desactivar un usuario (borrado lógico / baja).
     */
    UsuarioResponseDTO cambiarEstado(Long id, boolean activo);
}
