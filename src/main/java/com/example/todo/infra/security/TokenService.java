package com.example.todo.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.todo.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken (User user){
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
              .withIssuer("auth0")
              .withSubject(user.getEmail()) /// payload
              .sign(algorithm);
    } catch (JWTCreationException exception){
        throw new RuntimeException("Error while generating token", exception);
    }
  }

  public String validateToken(String token) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
              .withIssuer("auth0")
              .build()
              .verify(token)
              .getSubject();
    } catch (JWTVerificationException exception){
        return "";
    }
  }
}
