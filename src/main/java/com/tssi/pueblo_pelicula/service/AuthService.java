package com.tssi.pueblo_pelicula.service;

import com.tssi.pueblo_pelicula.dto.LoginDTO;
import com.tssi.pueblo_pelicula.dto.LoginResponseDTO;
import com.tssi.pueblo_pelicula.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    LoginResponseDTO login(LoginDTO loginDTO);

    User findByEmail(String email);

}
