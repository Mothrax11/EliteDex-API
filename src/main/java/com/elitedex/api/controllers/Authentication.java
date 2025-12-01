package com.elitedex.api.controllers;

import com.elitedex.api.db.dto.LoginDTO;
import com.elitedex.api.db.entities.UsuarioEntidad;
import com.elitedex.api.db.services.UsuarioService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Authentication {
    private final UsuarioService usuarioService;

    public Authentication (UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public String register(@RequestBody() String body){
        JSONObject json = new JSONObject(body);

        String username = json.getString("username");
        String email = json.getString("email");
        String contrasena = json.getString("contrasena");

        System.out.println(username + email + contrasena);
        UsuarioEntidad usuario = usuarioService.registrar(username, email, contrasena);

        return new JSONObject().put("usuarioCreado", usuario != null ? "usuarioCreado" : "usuarioNoCreado").toString();

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO dto) {
        UsuarioEntidad usuarioLogeado = usuarioService.login(
                dto.getUsernameOrEmail(),
                dto.getContrasena()
        );

        if (usuarioLogeado != null) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Inicio de sesión exitoso");
            respuesta.put("id_usuario", usuarioLogeado.getId_usuario());
            respuesta.put("username", usuarioLogeado.getNombreUsuario());
            respuesta.put("logo_url", usuarioLogeado.getLogoUrl());
            return ResponseEntity.ok(respuesta);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/upload-logo/{id}")
    public ResponseEntity<Map<String, String>> subirLogo(@PathVariable("id") int idUsuario, @RequestParam("file") MultipartFile file) {
        try {
            String url = usuarioService.subirLogo(idUsuario, file);
            Map<String, String> response = new HashMap<>();
            response.put("url", url);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al subir imagen: " + e.getMessage()));
        }
    }
}