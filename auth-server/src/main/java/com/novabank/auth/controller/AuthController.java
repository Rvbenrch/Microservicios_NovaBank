package com.novabank.auth.controller;

import com.novabank.auth.dto.AuthResponse;
import com.novabank.auth.dto.LoginRequest;
import com.novabank.auth.dto.RegisterRequest;
import com.novabank.auth.dto.ValidateResponse;
import com.novabank.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    @GetMapping("/validate")
    public ValidateResponse validate(
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        String token = authorizationHeader.replace("Bearer ", "");

        return authService.validate(token);
    }
}
