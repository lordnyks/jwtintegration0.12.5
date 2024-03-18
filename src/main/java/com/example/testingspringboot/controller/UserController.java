package com.example.testingspringboot.controller;

import com.example.testingspringboot.model.User;
import com.example.testingspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/test")
    public String works() {
        return "This api works, dawg!";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



}
