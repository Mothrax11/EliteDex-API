package com.elitedex.api.db.repositories;

import com.elitedex.api.db.entities.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntidad, Integer> {

    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByCorreo(String correo);

    UsuarioEntidad findByNombreUsuarioOrCorreo(String nombreUsuario, String correo);
    UsuarioEntidad findByNombreUsuario(String nombreUsuario);
    UsuarioEntidad findByCorreo(String correo);
}