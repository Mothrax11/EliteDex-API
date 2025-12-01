package com.elitedex.api.controllers;

import com.elitedex.api.db.dto.RecomendacionDTO;
import com.elitedex.api.db.services.RecomendacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipo")
public class RecomendacionController {

    private final RecomendacionService recommendationService;

    public RecomendacionController(RecomendacionService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recomendar/{id}")
    public ResponseEntity<RecomendacionDTO> recomendarTipos(@PathVariable("id") int idEquipo) {
        return ResponseEntity.ok(recommendationService.analizarEquipo(idEquipo));
    }
}