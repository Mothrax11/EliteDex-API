package com.elitedex.api.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "usuarios")
@Table(name = "usuarios")
@Getter
@Setter
public class UsuarioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_usuario;
    
    @Column(name = "nombre_usuario", nullable = false, unique = true)
    String nombreUsuario;
    
    @Column(name = "contrasena", nullable = false)
    String password_hash;
    
    @Column(name = "correo", nullable = false, unique = true)
    String correo;

    @Column(name = "imagen_usuario", nullable = true)
    String logoUrl;
    
    public UsuarioEntidad(String nombreUsuario, String correo, String passwordHash) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.password_hash = passwordHash;
        this.logoUrl = null;
    }

    public UsuarioEntidad() {
    }
}