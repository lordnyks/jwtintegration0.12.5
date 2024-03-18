package com.example.testingspringboot.controller;

import com.example.testingspringboot.model.auth.request.AuthenticationRequest;
import com.example.testingspringboot.model.auth.request.RegisterRequest;
import com.example.testingspringboot.repository.UserRepository;
import com.example.testingspringboot.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(String.format("Username-ul %s este deja folosit de către un alt utilizator!", request.getUsername()));
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }
}
