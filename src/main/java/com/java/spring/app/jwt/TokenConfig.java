package com.java.spring.app.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {
    @Value("${jwt.secret}")
    String secret;

    @Value("${jwt.validity}")
    int validity;

    public byte[] getSecret() {
        return secret.getBytes();
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }
}
