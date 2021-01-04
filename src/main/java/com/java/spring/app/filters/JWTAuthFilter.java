package com.java.spring.app.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.spring.app.jwt.TokenConfig;
import com.java.spring.app.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public JWTAuthFilter(AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            Authentication authenticate = authenticationManager.authenticate(authentication);

            return authenticate;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        Instant initDate = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Instant expDate = initDate.plus(tokenConfig.getValidity(), ChronoUnit.MINUTES);

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(Date.from(initDate))
                .setExpiration(Date.from(expDate))
                .signWith(Keys.hmacShaKeyFor(tokenConfig.getSecret()))
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
    }
}

