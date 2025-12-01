package com.elitedex.api.db.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoritoDTO {
    private int id_usuario;
    private int id_pokemon;
    private String nombre_pokemon;
}