package com.clinicapp.clinicapp_api.controller;
// ============================================================
// CONTROLLER: CitaController
// ============================================================

import com.clinicapp.clinicapp_api.dtos.CitaRequestDTO;
import com.clinicapp.clinicapp_api.dtos.CitaResponseDTO;
import com.clinicapp.clinicapp_api.entity.enums.EstadoCita;
import com.clinicapp.clinicapp_api.service.CitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/citas")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed.origins}")
public class CitaController {

    private final CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(citaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaResponseDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaService.listarPorPaciente(pacienteId));
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<CitaResponseDTO>> listarPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(citaService.listarPorMedico(medicoId));
    }

    @GetMapping("/medico/{medicoId}/fecha")
    public ResponseEntity<List<CitaResponseDTO>> listarPorMedicoYFecha(
            @PathVariable Long medicoId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
        return ResponseEntity.ok(citaService.listarPorMedicoYFecha(medicoId, fecha));
    }

    @GetMapping("/rango")
    public ResponseEntity<List<CitaResponseDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fin) {
        return ResponseEntity.ok(citaService.listarPorRangoFechas(inicio, fin));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaResponseDTO>> listarPorEstado(@PathVariable EstadoCita estado) {
        return ResponseEntity.ok(citaService.listarPorEstado(estado));
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<Boolean> verificarDisponibilidad(
            @RequestParam Long medicoId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fechaHora) {
        return ResponseEntity.ok(citaService.verificarDisponibilidad(medicoId, fechaHora));
    }

    @GetMapping("/contar/estado/{estado}")
    public ResponseEntity<Long> contarPorEstado(@PathVariable EstadoCita estado) {
        return ResponseEntity.ok(citaService.contarCitasPorEstado(estado));
    }

    @GetMapping("/contar/medico/{medicoId}")
    public ResponseEntity<Long> contarPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(citaService.contarCitasPorMedico(medicoId));
    }

    @GetMapping("/contar/hoy")
    public ResponseEntity<Long> contarCitasHoy() {
        return ResponseEntity.ok(citaService.contarCitasHoy());
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<Object[]>> obtenerCitasDelDia() {
        return ResponseEntity.ok(citaService.obtenerCitasDelDia());
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> crear(@Valid @RequestBody CitaRequestDTO request) {
        CitaResponseDTO response = citaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CitaRequestDTO request) {
        return ResponseEntity.ok(citaService.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<CitaResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestBody EstadoCita nuevoEstado) {
        return ResponseEntity.ok(citaService.actualizarEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}