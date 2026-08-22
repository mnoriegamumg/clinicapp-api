package com.clinicapp.clinicapp_api.controller;
// ============================================================
// CONTROLLER: PacienteController
// ============================================================


import com.clinicapp.clinicapp_api.dtos.PacienteRequestDTO;
import com.clinicapp.clinicapp_api.dtos.PacienteResponseDTO;
import com.clinicapp.clinicapp_api.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed.origins}")
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PacienteResponseDTO>> buscarPorNombre(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(pacienteService.buscarPorNombre(q));
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarTotal() {
        return ResponseEntity.ok(pacienteService.contarTotal());
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crear(@Valid @RequestBody PacienteRequestDTO request) {
        PacienteResponseDTO response = pacienteService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PacienteRequestDTO request) {
        return ResponseEntity.ok(pacienteService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}