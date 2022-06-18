package com.tssi.pueblo_pelicula.security.util;

import io.jsonwebtoken.SignatureAlgorithm;
import com.tssi.pueblo_pelicula.model.User;
import com.tssi.pueblo_pelicula.repository.AuthRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    AuthRepository authRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Integer jwtExpiration;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public Long extractId(String token) {
        return Long.valueOf(getClaimFromToken(token, Claims::getId));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails, User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims).setSubject(userDetails.getUsername())
                .setId(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    public Boolean validateToken(String token, User user) {
        final Long id = Long.valueOf(extractId(token));
        return id == user.getId() && !isTokenExpired(token);
    }

    @Autowired
    public void setAuthRepository(final AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
}
