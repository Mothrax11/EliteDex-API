package com.elitedex.api.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "favoritos")
@Getter
@Setter
public class FavoritoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_favorito;

    @Column(name = "nombre_pokemon", nullable = false)
    private String nombrePokemon;

    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    @Column(name = "id_pokemon", nullable = false)
    private int idPokemon;

    public FavoritoEntidad(int idUsuario, int idPokemon, String nombrePokemon) {
        this.idUsuario = idUsuario;
        this.idPokemon = idPokemon;
        this.nombrePokemon = nombrePokemon;
    }

    public FavoritoEntidad() {}
}