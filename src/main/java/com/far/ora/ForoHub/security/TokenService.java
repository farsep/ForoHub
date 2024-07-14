package com.far.ora.ForoHub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.far.ora.ForoHub.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class TokenService {
    @Value("${env.api_secret}")
    String API_secret;

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(API_secret);
            String token = JWT.create()
                    .withIssuer("FOROHUB")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(getExpirationDate(7))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error to create token");
        }
    }

    private Instant getExpirationDate(Integer days){
        return LocalDate.now().plusDays(days).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
    }

    public String getSubjectAndVerify(String token){
        if (token == null || token.isEmpty()){
            throw new RuntimeException("Token is empty");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(API_secret);
            return JWT.require(algorithm)
                    .withIssuer("FOROHUB")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e){
            throw new RuntimeException("Error to verify token");
        }
    }

}
