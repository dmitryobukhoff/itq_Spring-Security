package com.example.jwtdemo.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping("/say")
    public ResponseEntity<String> say(){
        return ResponseEntity.ok("Hello from secured controller");
    }
}
