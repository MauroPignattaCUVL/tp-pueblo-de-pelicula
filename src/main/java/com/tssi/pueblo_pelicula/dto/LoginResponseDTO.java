package com.tssi.pueblo_pelicula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginResponseDTO implements Serializable {
    private String accessToken;

    private String tokenType;

    private Integer expiresIn;
}