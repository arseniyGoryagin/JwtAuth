package com.arseniy.jwt.domain;

public interface JwtService {

    String generateToken(String username);

    void validateToken(String token);

    String getUsernameFromToken(String token);

}
