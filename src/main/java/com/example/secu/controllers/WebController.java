package com.example.secu.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebController {
    @GetMapping
    public ResponseEntity<String> bonjour(){
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/user")
    public ResponseEntity<?>bonjourUser(){
        return ResponseEntity.ok("hello i'm user");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String>bonjourAdmin(){
        return ResponseEntity.ok("hello i'm admin");
    }
}
