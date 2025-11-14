package com.elitedex.api.controllers;

import com.elitedex.api.db.entities.UsuarioEntidad;
import com.elitedex.api.db.services.UsuarioService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println(json.getString("nombre") + json.getString("correo") + json.getString("contrasena"));
        UsuarioEntidad usuario = usuarioService.crear(json.getString("nombre"), json.getString("correo"), json.getString("contrasena"));
        return new JSONObject().put("usuarioCreado", usuario != null ? "usuarioCreado" : "usuarioNoCreado").toString();

    }


}
