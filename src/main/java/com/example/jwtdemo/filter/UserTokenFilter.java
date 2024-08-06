package com.example.jwtdemo.filter;

import com.example.jwtdemo.service.UserTokenService;
import com.example.jwtdemo.wrapper.MutableHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserTokenFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final UserTokenService userTokenService;
    @Value("${security.http.cookies.token}")
    private String COOKIE_NAME;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            Optional<Cookie> cookie = findCookieByName(cookies, COOKIE_NAME);
            if (cookie.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            try {
                log.info("Cookie with name 'redis_user_id' detected. Value {}", cookie.get().getValue());
                String token = userTokenService.getTokenByUsername(cookie.get().getValue());
                MutableHttpServletRequest mutableHttpRequest = new MutableHttpServletRequest(request);
                mutableHttpRequest.putHeader("Authorization", "Bearer " + token);
                filterChain.doFilter(mutableHttpRequest, response);
            } catch (Exception exception) {
                log.error("Error in UserTokenFilter");
                handlerExceptionResolver.resolveException(request, response, null, exception);
            }
        }else{
            log.info("Cookie with name 'redis_user_id' not found!");
            filterChain.doFilter(request, response);
        }

    }

    private Optional<Cookie> findCookieByName(Cookie[] cookies, String name){
        for(Cookie cookie : cookies)
            if (cookie.getName().equals(name)) return Optional.of(cookie);
        return Optional.empty();
    }
}
