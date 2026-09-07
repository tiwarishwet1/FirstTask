package com.example.first_assignment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.first_assignment.entity.Employee;
import com.example.first_assignment.security.JwtService;
import com.example.first_assignment.security.LoginRequest;
import com.example.first_assignment.security.RegisterRequest;
import com.example.first_assignment.service.RegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RegistrationService registrationService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RegistrationService registrationService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()));

        String username = authentication.getName();

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        return jwtService.generateToken(username, role);
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> register(
            @Valid @RequestBody RegisterRequest request) {

        Employee employee =
                registrationService.registerEmployee(request);

        return new ResponseEntity<>(
                employee,
                HttpStatus.CREATED);
    }
}