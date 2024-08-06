package com.example.jwtdemo.service;

import java.util.UUID;

public interface UserTokenService {
    String getTokenByUsername(String username);
    void addToken(String username, String token);
}
