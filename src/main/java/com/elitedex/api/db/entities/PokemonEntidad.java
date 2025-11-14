package com.elitedex.api.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "pokemons")
@Table(name = "pokemons")
@Getter
@Setter
public class PokemonEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_pokemon;

    @Column(name = "nombre_pokemon", nullable = false)
    String nombre_pokemon;

    @Column(name = "tipo1", nullable = false)
    String tipo1;

    @Column(name = "tipo2", nullable = false)
    String tipo2;

    @Column(name = "movimiento1", nullable = false)
    String movimiento1;

    @Column(name = "movimiento2", nullable = false)
    String movimiento2;

    @Column(name = "movimiento3", nullable = false)
    String movimiento3;

    @Column(name = "movimiento4", nullable = false)
    String movimiento4;

    @Column(name = "id_equipo", nullable = false)
    int id_equipo;

    @Column(name = "habilidad", nullable = false)
    String habilidad;

    public PokemonEntidad(String nombre_pokemon,
                          String tipo1,
                          String tipo2,
                          String movimiento1,
                          String movimiento2,
                          String movimiento3,
                          String movimiento4,
                          int id_equipo,
                          String habilidad){

        this.nombre_pokemon = nombre_pokemon;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.movimiento1 = movimiento1;
        this.movimiento2 = movimiento2;
        this.movimiento3 = movimiento3;
        this.movimiento4 = movimiento4;
        this.id_equipo = id_equipo;
        this.habilidad = habilidad;
    }

    public PokemonEntidad(){}

}