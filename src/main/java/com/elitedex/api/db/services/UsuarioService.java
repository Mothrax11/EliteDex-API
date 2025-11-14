package com.elitedex.api.db.services;

import com.elitedex.api.db.entities.UsuarioEntidad;
import com.elitedex.api.db.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    public UsuarioEntidad crear(String nombre_usuario, String contrasena, String correo){
        return usuarioRepository.save(new UsuarioEntidad(nombre_usuario, contrasena, correo));
    }
}
