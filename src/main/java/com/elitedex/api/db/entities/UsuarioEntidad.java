package com.elitedex.api.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "usuarios")
@Table(name = "usuarios")
@Getter
@Setter
public class UsuarioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_usuario;

    @Column(name = "nombre_usuario", nullable = true)
    String nombre_usuario;
    @Column(name = "contrasena", nullable = true)
    String contrasena;
    @Column(name = "correo", nullable = true)
    String correo;

    public UsuarioEntidad(String nombreUsuario, String contrasena, String correo) {
        this.nombre_usuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
    }
    public UsuarioEntidad(){}
}
