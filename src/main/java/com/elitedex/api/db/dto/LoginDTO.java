package com.elitedex.api.db.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {

    private String usernameOrEmail;
    private String contrasena;

}