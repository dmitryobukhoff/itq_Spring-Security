package com.example.jwtdemo.wrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MutableHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public void putHeader(String name, String value){
        customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name){
        if(customHeaders.containsKey(name))
            return customHeaders.get(name);
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = super.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            headers.put(headerName, super.getHeader(headerName));
        }
        headers.putAll(customHeaders);
        return Collections.enumeration(headers.keySet());
    }
}
