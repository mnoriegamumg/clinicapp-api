package com.clinicapp.clinicapp_api.controller;
// ============================================================
// CONTROLLER: MedicoController
// ============================================================

import com.clinicapp.clinicapp_api.dtos.MedicoRequestDTO;
import com.clinicapp.clinicapp_api.dtos.MedicoResponseDTO;
import com.clinicapp.clinicapp_api.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed.origins}")
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.obtenerPorId(id));
    }

    @GetMapping("/especialidad")
    public ResponseEntity<List<MedicoResponseDTO>> buscarPorEspecialidad(@RequestParam(required = false) String especialidad) {
        return ResponseEntity.ok(medicoService.buscarPorEspecialidad(especialidad));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MedicoResponseDTO>> buscarPorNombre(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(medicoService.buscarPorNombre(q));
    }

    @GetMapping("/especialidades")
    public ResponseEntity<List<String>> listarEspecialidades() {
        return ResponseEntity.ok(medicoService.listarEspecialidades());
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarTotal() {
        return ResponseEntity.ok(medicoService.contarTotal());
    }

    @PostMapping
    public ResponseEntity<MedicoResponseDTO> crear(@Valid @RequestBody MedicoRequestDTO request) {
        MedicoResponseDTO response = medicoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody MedicoRequestDTO request) {
        return ResponseEntity.ok(medicoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}