package com.elitedex.api.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "votos")
@Getter
@Setter
public class VotoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_voto;

    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    @Column(name = "id_equipo", nullable = false)
    private int idEquipo;

    @Column(name = "voto", nullable = false)
    private int voto;

    public VotoEntidad(int idUsuario, int idEquipo, int voto) {
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
        this.voto = voto;
    }

    public VotoEntidad() {}
}