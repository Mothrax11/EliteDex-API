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
    int id_equipo;

    @Column(name = "nombre_equipo", nullable = false)
    String nombre_equipo;

    @Column(name = "id_usuario", nullable = false)
    int id_usuario;

    public EquipoEntidad(String nombre_equipo, int id_usuario) {
        this.nombre_equipo = nombre_equipo;
        this.id_usuario = id_usuario;
    }

    public EquipoEntidad(){}
}
