package com.elitedex.api.db.services;

import com.elitedex.api.db.entities.UsuarioEntidad;
import com.elitedex.api.db.repositories.UsuarioRepository;
import com.elitedex.api.utils.PasswordManagement;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordManagement passwordManagement;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordManagement passwordManagement) {
        this.usuarioRepository = usuarioRepository;
        this.passwordManagement = passwordManagement;
    }

    // Método que debes crear para manejo de errores en el Controller (ej. 409)
    public static class RegistroException extends RuntimeException {
        public RegistroException(String message) {
            super(message);
        }
    }

    public UsuarioEntidad registrar(String nombreUsuario, String correo, String contrasena) {
        // 1. Validaciones de unicidad usando la excepción de negocio
        if (usuarioRepository.existsByNombreUsuario(nombreUsuario)) {
            throw new RegistroException("El nombre de usuario ya está en uso.");
        }
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new RegistroException("El correo electrónico ya está registrado.");
        }

        // 2. Hashing
        String hashedPassword = passwordManagement.hashPassword(contrasena);

        // 3. Persistencia
        UsuarioEntidad nuevoUsuario = new UsuarioEntidad(nombreUsuario, correo, hashedPassword);
        return usuarioRepository.save(nuevoUsuario);
    }

    public UsuarioEntidad login(String usernameOrEmail, String contrasena) {

        // 1. Búsqueda simplificada: Spring Data ya gestiona la búsqueda por OR
        UsuarioEntidad usuario = usuarioRepository.findByNombreUsuarioOrCorreo(usernameOrEmail, usernameOrEmail);

        if (usuario == null) {
            // Usuario no encontrado por nombre de usuario ni por correo
            return null;
        }

        // 2. Verificación de contraseña usando el hash almacenado
        boolean esValido = passwordManagement.verifyPassword(contrasena, usuario.getPassword_hash());

        if (esValido) {
            return usuario; // Login exitoso
        } else {
            return null; // Contraseña incorrecta
        }
    }
}