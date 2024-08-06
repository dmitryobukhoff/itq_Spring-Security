package com.example.jwtdemo.service.impl;

import com.example.jwtdemo.model.User;
import com.example.jwtdemo.model.dto.request.UserAuthenticationRequest;
import com.example.jwtdemo.model.dto.request.UserRegistrationRequest;
import com.example.jwtdemo.model.dto.response.UserAuthenticationResponse;
import com.example.jwtdemo.model.dto.response.UserRegistrationResponse;
import com.example.jwtdemo.repository.UserRepository;
import com.example.jwtdemo.service.AuthenticationService;
import com.example.jwtdemo.service.JwtService;
import com.example.jwtdemo.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserTokenService userTokenService;
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
        return UserRegistrationResponse.builder()
                .username(user.getEmail())
                .token(generateToken(user))
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
        return UserAuthenticationResponse.builder()
                .username(user.getEmail())
                .token(generateToken(user))
                .build();
    }

    private String generateToken(User user){
        final String token = jwtService.generateToken(user);
        userTokenService.addToken(user.getEmail(), token);
        return token;
    }

}
