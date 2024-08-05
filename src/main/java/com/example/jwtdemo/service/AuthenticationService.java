package com.example.jwtdemo.service;

import com.example.jwtdemo.model.dto.request.UserAuthenticationRequest;
import com.example.jwtdemo.model.dto.request.UserRegistrationRequest;
import com.example.jwtdemo.model.dto.response.UserAuthenticationResponse;
import com.example.jwtdemo.model.dto.response.UserRegistrationResponse;

public interface AuthenticationService {
    UserRegistrationResponse register(UserRegistrationRequest request);
    UserAuthenticationResponse authenticate(UserAuthenticationRequest request);
}
