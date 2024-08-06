package com.example.jwtdemo.service.impl;

import com.example.jwtdemo.repository.UserTokenRepository;
import com.example.jwtdemo.service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private final UserTokenRepository userTokenRepository;

    @Value("${security.http.cookies.token}")
    private String COOKIE_NAME;
    @Override
    public void addCookie(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(COOKIE_NAME, username);
        response.addCookie(cookie);
    }
}
