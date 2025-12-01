package com.elitedex.api.db.dto;

import java.util.List;

public class EquipoDTO {

    private String nombre_equipo;
    private int id_usuario;
    private List<String> pokemons;

    public EquipoDTO() {}

    public EquipoDTO(String nombre_equipo, int id_usuario, List<String> pokemons) {
        this.nombre_equipo = nombre_equipo;
        this.id_usuario = id_usuario;
        this.pokemons = pokemons;
    }

    public String getNombre_equipo() {
        return nombre_equipo;
    }

    public void setNombre_equipo(String nombre_equipo) {
        this.nombre_equipo = nombre_equipo;
    }


    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<String> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<String> pokemons) {
        this.pokemons = pokemons;
    }
}