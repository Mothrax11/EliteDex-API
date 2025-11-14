package com.elitedex.api.db.dto;


public class EquipoDTO {

    public String nombre_equipo;
    public int id_usuario;

    public EquipoDTO() {
    }

    public EquipoDTO(String nombre_equipo, int id_usuario) {
        this.nombre_equipo = nombre_equipo;
        this.id_usuario = id_usuario;
    }

    public String getNombre_equipo() {
        return nombre_equipo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setNombre_equipo(String nombre_equipo) {
        this.nombre_equipo = nombre_equipo;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}