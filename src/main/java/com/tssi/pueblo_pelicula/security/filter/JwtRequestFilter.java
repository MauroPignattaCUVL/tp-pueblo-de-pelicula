package com.tssi.pueblo_pelicula.security.filter;

import com.tssi.pueblo_pelicula.model.User;
import com.tssi.pueblo_pelicula.security.util.JwtTokenUtil;
import com.tssi.pueblo_pelicula.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        if (isHeaderAuthorizationExists(authorizationHeader)) {

            String jwt = authorizationHeader.substring(7);
            String email = jwtTokenUtil.getUsernameFromToken(jwt);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = authService.findByEmail(email);

                if (jwtTokenUtil.validateToken(jwt, user)) {
                    Collection<GrantedAuthority> permissions = new ArrayList<>();
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, permissions);
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isHeaderAuthorizationExists(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }

}
