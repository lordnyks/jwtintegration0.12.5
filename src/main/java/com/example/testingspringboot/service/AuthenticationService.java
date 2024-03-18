package com.example.testingspringboot.service;

import com.example.testingspringboot.model.ERole;
import com.example.testingspringboot.security.jwt.JwtService;
import com.example.testingspringboot.repository.UserRepository;
import com.example.testingspringboot.model.User;
import com.example.testingspringboot.model.auth.request.AuthenticationRequest;
import com.example.testingspringboot.model.auth.request.RegisterRequest;
import com.example.testingspringboot.model.auth.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(ERole.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.createToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername());
        var jwtToken = jwtService.createToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

}
