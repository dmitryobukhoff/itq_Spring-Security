package com.example.jwtdemo.auth;

import com.example.jwtdemo.model.dto.request.UserAuthenticationRequest;
import com.example.jwtdemo.model.dto.request.UserRegistrationRequest;
import com.example.jwtdemo.model.dto.response.UserAuthenticationResponse;
import com.example.jwtdemo.model.dto.response.UserRegistrationResponse;
import com.example.jwtdemo.service.AuthenticationService;
import com.example.jwtdemo.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CookieService cookieService;

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(
            @RequestBody UserAuthenticationRequest authenticateInformation,
            HttpServletResponse response
            ){
        UserAuthenticationResponse userAuthenticationResponse = authenticationService.authenticate(authenticateInformation);
        cookieService.addCookie(response, userAuthenticationResponse.getUsername());
        return ResponseEntity.ok(userAuthenticationResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> register(
            @RequestBody UserRegistrationRequest registrationInformation,
            HttpServletResponse response
    ){
        UserRegistrationResponse userRegistrationResponse = authenticationService.register(registrationInformation);
        cookieService.addCookie(response, userRegistrationResponse.getUsername());
        return ResponseEntity.ok(userRegistrationResponse);
    }
}
