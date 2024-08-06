package com.example.jwtdemo.repository;

import java.util.Optional;

public interface UserTokenRepository{
    Optional<String> findTokenByUsername(String username);
    void putToken(String username, String token);
}
