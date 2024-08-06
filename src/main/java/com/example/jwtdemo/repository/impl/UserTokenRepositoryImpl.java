package com.example.jwtdemo.repository.impl;

import com.example.jwtdemo.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserTokenRepositoryImpl implements UserTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Optional<String> findTokenByUsername(String username) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(username));
    }

    @Override
    public void putToken(String username, String token) {
        redisTemplate.opsForValue().set(username, token);
    }
}
