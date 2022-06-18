package com.tssi.pueblo_pelicula.service.impl;

import com.tssi.pueblo_pelicula.dto.LoginDTO;
import com.tssi.pueblo_pelicula.dto.LoginResponseDTO;
import com.tssi.pueblo_pelicula.exception.CredentialsException;
import com.tssi.pueblo_pelicula.exception.UserNotFoundException;
import com.tssi.pueblo_pelicula.model.User;
import com.tssi.pueblo_pelicula.repository.AuthRepository;
import com.tssi.pueblo_pelicula.security.util.JwtTokenUtil;
import com.tssi.pueblo_pelicula.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        User user = findByEmail(loginDTO.getEmail());
        UserDetails userDetails;
        if (user == null) {
            throw new UserNotFoundException("The user is not registered.");
        } else if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new CredentialsException("The data entered is invalid.");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), loginDTO.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        userDetails = (UserDetails) auth.getPrincipal();
        final String jwt = jwtTokenUtil.generateToken(userDetails, user);
        return new LoginResponseDTO(jwt);
    }

    @Override
    public User findByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.findByEmail(username);
    }
}