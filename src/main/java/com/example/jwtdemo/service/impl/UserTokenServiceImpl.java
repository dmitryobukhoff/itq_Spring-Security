package com.example.jwtdemo.service.impl;

import com.example.jwtdemo.exception.UserNotFoundException;
import com.example.jwtdemo.repository.UserTokenRepository;
import com.example.jwtdemo.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    private final UserTokenRepository userTokenRepository;

    @Override
    public String getTokenByUsername(String id) {
        return userTokenRepository.findTokenByUsername(id)
                .orElseThrow(() -> new UserNotFoundException("User with id '" + id + "' not found"));
    }

    @Override
    public void addToken(String id, String token) {
        userTokenRepository.putToken(id, token);
    }
}
