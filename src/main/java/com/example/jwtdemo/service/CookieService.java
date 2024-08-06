package com.example.jwtdemo.service;

import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void addCookie(HttpServletResponse response, String value);
}
