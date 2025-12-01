package com.elitedex.api.db.services;

import com.elitedex.api.db.entities.UsuarioEntidad;
import com.elitedex.api.db.repositories.UsuarioRepository;
import com.elitedex.api.utils.PasswordManagement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
        if (usuarioRepository.existsByNombreUsuario(nombreUsuario)) {
            throw new RegistroException("El nombre de usuario ya está en uso.");
        }
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new RegistroException("El correo electrónico ya está registrado.");
        }

        String hashedPassword = passwordManagement.hashPassword(contrasena);

        UsuarioEntidad nuevoUsuario = new UsuarioEntidad(nombreUsuario, correo, hashedPassword);
        return usuarioRepository.save(nuevoUsuario);
    }

    public UsuarioEntidad login(String usernameOrEmail, String contrasena) {

        UsuarioEntidad usuario = usuarioRepository.findByNombreUsuarioOrCorreo(usernameOrEmail, usernameOrEmail);

        if (usuario == null) {
            return null;
        }

        boolean esValido = passwordManagement.verifyPassword(contrasena, usuario.getPassword_hash());

        if (esValido) {
            return usuario;
        } else {
            return null;
        }
    }
    @Value("${app.server.ip}")
    private String serverIp;

    @Transactional
    public String subirLogo(int idUsuario, MultipartFile archivo) throws IOException {
        UsuarioEntidad usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        Path directorioImagenes = Paths.get("uploads");
        if (!Files.exists(directorioImagenes)) {
            Files.createDirectories(directorioImagenes);
        }

        String nombreArchivo = "user_" + idUsuario + "_" + archivo.getOriginalFilename();
        Path rutaArchivo = directorioImagenes.resolve(nombreArchivo);
        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
        String urlPublica = "http://"+ serverIp +"/uploads/" + nombreArchivo;

        usuario.setLogoUrl(urlPublica);
        usuarioRepository.saveAndFlush(usuario);

        return urlPublica;
    }
}