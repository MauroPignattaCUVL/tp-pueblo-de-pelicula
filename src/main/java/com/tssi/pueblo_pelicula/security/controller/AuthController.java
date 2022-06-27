package com.tssi.pueblo_pelicula.security.controller;

import com.tssi.pueblo_pelicula.dto.LoginDTO;
import com.tssi.pueblo_pelicula.dto.LoginResponseDTO;
import com.tssi.pueblo_pelicula.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@Valid @RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(authService.login(loginDTO), HttpStatus.OK);
    }

}