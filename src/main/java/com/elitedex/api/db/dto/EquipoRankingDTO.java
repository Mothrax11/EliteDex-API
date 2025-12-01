package com.elitedex.api.db.dto;

import com.elitedex.api.db.entities.PokemonEntidad;
import java.util.List;

public class EquipoRankingDTO {
    private int id_equipo;
    private String nombre_equipo;
    private String nombre_creador;
    private List<PokemonEntidad> pokemons;
    private long total_votos;
    private boolean votado_por_mi;

    public EquipoRankingDTO(int id_equipo, String nombre_equipo, String nombre_creador, List<PokemonEntidad> pokemons, long total_votos, boolean votado_por_mi) {
        this.id_equipo = id_equipo;
        this.nombre_equipo = nombre_equipo;
        this.nombre_creador = nombre_creador;
        this.pokemons = pokemons;
        this.total_votos = total_votos;
        this.votado_por_mi = votado_por_mi;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getNombre_equipo() {
        return nombre_equipo;
    }

    public void setNombre_equipo(String nombre_equipo) {
        this.nombre_equipo = nombre_equipo;
    }

    public String getNombre_creador() {
        return nombre_creador;
    }
    
    public void setNombre_creador(String nombre_creador) {
        this.nombre_creador = nombre_creador;
    }

    public List<PokemonEntidad> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<PokemonEntidad> pokemons) {
        this.pokemons = pokemons;
    }

    public long getTotal_votos() {
        return total_votos;
    }

    public void setTotal_votos(long total_votos) {
        this.total_votos = total_votos;
    }

    public boolean isVotado_por_mi() {
        return votado_por_mi;
    }

    public void setVotado_por_mi(boolean votado_por_mi) {
        this.votado_por_mi = votado_por_mi;
    }
}