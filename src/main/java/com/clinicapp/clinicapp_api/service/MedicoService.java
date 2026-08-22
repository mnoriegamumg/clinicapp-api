package com.clinicapp.clinicapp_api.service;
// ============================================================
// SERVICE: MedicoService
// ============================================================

import com.clinicapp.clinicapp_api.dtos.MedicoRequestDTO;
import com.clinicapp.clinicapp_api.dtos.MedicoResponseDTO;

import java.util.List;

public interface MedicoService {

    MedicoResponseDTO crear(MedicoRequestDTO request);

    MedicoResponseDTO actualizar(Long id, MedicoRequestDTO request);

    void eliminar(Long id);

    MedicoResponseDTO obtenerPorId(Long id);

    List<MedicoResponseDTO> listarTodos();

    List<MedicoResponseDTO> buscarPorEspecialidad(String especialidad);

    List<MedicoResponseDTO> buscarPorNombre(String busqueda);

    List<String> listarEspecialidades();

    Long contarTotal();
}