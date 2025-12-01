package com.elitedex.api.controllers;

import com.elitedex.api.db.dto.FavoritoDTO;
import com.elitedex.api.db.entities.FavoritoEntidad;
import com.elitedex.api.db.services.FavoritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> agregarFavorito(@RequestBody FavoritoDTO dto) {
        try {
            FavoritoEntidad nuevo = favoritoService.agregar(
                    dto.getId_usuario(),
                    dto.getId_pokemon(),
                    dto.getNombre_pokemon()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Pokémon añadido a favoritos.");
            response.put("id_favorito", nuevo.getId_favorito());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Object>> toggleFavorito(@RequestBody FavoritoDTO dto) {
        try {
            String resultado = favoritoService.toggleFavorito(
                    dto.getId_usuario(),
                    dto.getId_pokemon(),
                    dto.getNombre_pokemon()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", resultado);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/user/{idUsuario}")
    public ResponseEntity<List<FavoritoEntidad>> listarFavoritos(@PathVariable("idUsuario") int idUsuario) {
        List<FavoritoEntidad> favoritos = favoritoService.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(favoritos);
    }
}