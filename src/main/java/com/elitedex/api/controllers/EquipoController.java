package com.elitedex.api.controllers;

// Importamos el DTO que creamos
import com.elitedex.api.db.dto.EquipoDTO;
import com.elitedex.api.db.entities.EquipoEntidad;
import com.elitedex.api.db.services.EquipoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/equipo")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService){
        this.equipoService = equipoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<EquipoEntidad> crearEquipo(@RequestBody EquipoDTO dto) {
        EquipoEntidad equipoCreado = equipoService.crear(
                dto.nombre_equipo(),
                dto.id_usuario()
        );
        // Devolvemos el objeto creado y un código 201 (Created)
        return new ResponseEntity<>(equipoCreado, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EquipoEntidad>> listarEquipos() {
        List<EquipoEntidad> equipos = equipoService.listarTodos();
        return ResponseEntity.ok(equipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoEntidad> obtenerEquipoPorId(@PathVariable("id") int idEquipo) {
        EquipoEntidad equipo = equipoService.obtenerPorId(idEquipo);
        return ResponseEntity.ok(equipo);
    }

    // --- UPDATE ---
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EquipoEntidad> actualizarEquipo(
            @PathVariable("id") int idEquipo,
            @RequestBody EquipoDTO dto) {

        EquipoEntidad equipoActualizado = equipoService.actualizar(
                idEquipo,
                dto.nombre_equipo(),
                dto.id_usuario()
        );
        return ResponseEntity.ok(equipoActualizado);
    }

    // --- DELETE ---
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, String>> eliminarEquipo(@PathVariable("id") int idEquipo) {
        equipoService.eliminar(idEquipo);

        // Creamos una respuesta JSON simple
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Equipo eliminado con éxito");
        respuesta.put("id_eliminado", String.valueOf(idEquipo));

        return ResponseEntity.ok(respuesta);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarErrorNotFound(EntityNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}