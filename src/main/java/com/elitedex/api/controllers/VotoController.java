package com.elitedex.api.controllers;

import com.elitedex.api.db.dto.VotoDTO;
import com.elitedex.api.db.entities.VotoEntidad;
import com.elitedex.api.db.services.VotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping("/publicar")
    public ResponseEntity<Map<String, Object>> registrarVoto(@RequestBody VotoDTO dto) {
        try {
            VotoEntidad votoGuardado = votoService.registrarPublicacion(
                    dto.getId_usuario(),
                    dto.getId_equipo()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Publicado con éxito.");
            response.put("id_equipo", dto.getId_equipo());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al publicar.");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/toggle")
    public ResponseEntity<Map<String, String>> toggleVoto(@RequestBody VotoDTO dto) {
        try {
            String mensaje = votoService.toggleVoto(dto.getId_usuario(), dto.getId_equipo());
            return ResponseEntity.ok(java.util.Map.of("mensaje", mensaje));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Map.of("error", e.getMessage()));
        }
    }
}