package com.elitedex.api.db.dto;

import com.elitedex.api.db.entities.PokemonEntidad;
import lombok.Getter;

import java.util.List;

@Getter
public class EquipoCompletoDTO {
    private int id_equipo;
    private String nombre_equipo;
    private List<PokemonEntidad> pokemons;

    public EquipoCompletoDTO(int id_equipo, String nombre_equipo, List<PokemonEntidad> pokemons) {
        this.id_equipo = id_equipo;
        this.nombre_equipo = nombre_equipo;
        this.pokemons = pokemons;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public void setNombre_equipo(String nombre_equipo) {
        this.nombre_equipo = nombre_equipo;
    }

    public void setPokemons(List<PokemonEntidad> pokemons) {
        this.pokemons = pokemons;
    }
}