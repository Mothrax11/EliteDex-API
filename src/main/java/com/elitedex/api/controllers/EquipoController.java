package com.elitedex.api.controllers;

import com.elitedex.api.db.dto.EquipoCompletoDTO;
import com.elitedex.api.db.dto.EquipoDTO;
import com.elitedex.api.db.dto.EquipoRankingDTO;
import com.elitedex.api.db.entities.EquipoEntidad;
import com.elitedex.api.db.services.EquipoService;
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
                dto.getNombre_equipo(),
                dto.getId_usuario(),
                dto.getPokemons()
        );
        return new ResponseEntity<>(equipoCreado, HttpStatus.CREATED);
    }

    @GetMapping("/user/{idUsuario}")
    public ResponseEntity<List<EquipoCompletoDTO>> listarEquiposUsuario(@PathVariable("idUsuario") int idUsuario) {
        return ResponseEntity.ok(equipoService.listarEquiposCompletos(idUsuario));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EquipoEntidad> actualizarEquipo(
            @PathVariable("id") int idEquipo,
            @RequestBody EquipoDTO dto) {

        EquipoEntidad equipoActualizado = equipoService.actualizar(
                idEquipo,
                dto.getNombre_equipo(),
                dto.getId_usuario(),
                dto.getPokemons()
        );
        return ResponseEntity.ok(equipoActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, String>> eliminarEquipo(@PathVariable("id") int idEquipo) {
        equipoService.eliminar(idEquipo);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Equipo eliminado con éxito");
        respuesta.put("id_eliminado", String.valueOf(idEquipo));

        return ResponseEntity.ok(respuesta);
    }
    @GetMapping("/ranking/{idUsuario}")
    public ResponseEntity<List<EquipoRankingDTO>> obtenerRanking(@PathVariable("idUsuario") int idUsuario) {
        return ResponseEntity.ok(equipoService.obtenerRanking(idUsuario));
    }

}