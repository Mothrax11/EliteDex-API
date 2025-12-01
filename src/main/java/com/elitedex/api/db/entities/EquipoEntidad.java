package com.elitedex.api.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "equipos")
@Table(name = "equipos")
@Getter
@Setter
public class EquipoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_equipo;

    @Column(name = "nombre_equipo", nullable = false)
    private String nombre_equipo;

    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    public EquipoEntidad(String nombre_equipo, int idUsuario) {
        this.nombre_equipo = nombre_equipo;
        this.idUsuario = idUsuario;
    }

    public EquipoEntidad(){}
}