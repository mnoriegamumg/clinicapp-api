package com.clinicapp.clinicapp_api.controller;
// ============================================================
// CONTROLLER: UsuarioController (administración de usuarios)
// ============================================================

import com.clinicapp.clinicapp_api.dtos.UsuarioRequestDTO;
import com.clinicapp.clinicapp_api.dtos.UsuarioResponseDTO;
import com.clinicapp.clinicapp_api.dtos.UsuarioUpdateDTO;
import com.clinicapp.clinicapp_api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed.origins}")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    /**
     * Buscar usuarios por coincidencia parcial de username.
     * Ejemplo: GET /api/usuarios/buscar?q=admin
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorUsername(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(usuarioService.buscarPorUsername(q));
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarTotal() {
        return ResponseEntity.ok(usuarioService.contarTotal());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO response = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody UsuarioUpdateDTO request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    /**
     * Activar o desactivar un usuario (baja lógica).
     * Ejemplo: PATCH /api/usuarios/5/estado?activo=false
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<UsuarioResponseDTO> cambiarEstado(@PathVariable Long id,
                                                            @RequestParam boolean activo) {
        return ResponseEntity.ok(usuarioService.cambiarEstado(id, activo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
