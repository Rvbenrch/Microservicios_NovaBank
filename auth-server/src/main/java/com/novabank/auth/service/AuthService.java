package com.novabank.auth.service;

import com.novabank.auth.dto.AuthResponse;
import com.novabank.auth.dto.LoginRequest;
import com.novabank.auth.dto.RegisterRequest;
import com.novabank.auth.dto.ValidateResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    ValidateResponse validate(String token);
}