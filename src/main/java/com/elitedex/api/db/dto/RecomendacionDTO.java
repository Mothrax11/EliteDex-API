package com.elitedex.api.db.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RecomendacionDTO {
    private List<String> tiposRecomendados;
    private String analisis;

    public RecomendacionDTO(List<String> tiposRecomendados, String analisis) {
        this.tiposRecomendados = tiposRecomendados;
        this.analisis = analisis;
    }

}