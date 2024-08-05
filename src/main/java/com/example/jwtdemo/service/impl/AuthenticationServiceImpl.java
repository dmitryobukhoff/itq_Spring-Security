package com.example.jwtdemo.service.impl;

import com.example.jwtdemo.model.User;
import com.example.jwtdemo.model.dto.request.UserAuthenticationRequest;
import com.example.jwtdemo.model.dto.request.UserRegistrationRequest;
import com.example.jwtdemo.model.dto.response.UserAuthenticationResponse;
import com.example.jwtdemo.model.dto.response.UserRegistrationResponse;
import com.example.jwtdemo.repository.UserRepository;
import com.example.jwtdemo.service.AuthenticationService;
import com.example.jwtdemo.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public UserRegistrationResponse register(UserRegistrationRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        final String token = jwtService.generateToken(user);
        return UserRegistrationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public UserAuthenticationResponse authenticate(UserAuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), // principal - информация, идентифицирующая пользователя
                        request.getPassword() // credentials - информация, предоставляемая пользователем для аутентификации
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        final String token = jwtService.generateToken(user);
        return UserAuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
