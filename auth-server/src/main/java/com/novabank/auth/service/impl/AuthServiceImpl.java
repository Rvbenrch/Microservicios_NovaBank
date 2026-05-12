package com.novabank.auth.service.impl;

import com.novabank.auth.dto.AuthResponse;
import com.novabank.auth.dto.LoginRequest;
import com.novabank.auth.dto.RegisterRequest;
import com.novabank.auth.dto.ValidateResponse;
import com.novabank.auth.model.Rol;
import com.novabank.auth.model.Usuario;
import com.novabank.auth.repository.UsuarioRepository;
import com.novabank.auth.service.AuthService;
import com.novabank.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UsuarioRepository usuarioRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con ese username");
        }

        Rol rol = Rol.valueOf(request.getRol().toUpperCase());

        if (rol == Rol.CLIENTE && request.getClienteId() == null) {
            throw new RuntimeException("Un usuario CLIENTE debe tener clienteId");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol.name());
        usuario.setClienteId(request.getClienteId());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        String token = jwtService.generarToken(usuarioGuardado);

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        boolean passwordCorrecta = passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword()
        );

        if (!passwordCorrecta) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtService.generarToken(usuario);

        return new AuthResponse(token);
    }

    @Override
    public ValidateResponse validate(String token) {

        boolean valido = jwtService.tokenValido(token);

        if (!valido) {
            return new ValidateResponse(false, null, null, null);
        }

        Claims claims = jwtService.obtenerClaims(token);

        String username = claims.getSubject();
        String rol = claims.get("rol", String.class);

        Long clienteId = null;

        Object clienteIdClaim = claims.get("clienteId");

        if (clienteIdClaim != null) {
            clienteId = Long.valueOf(clienteIdClaim.toString());
        }

        return new ValidateResponse(
                true,
                username,
                rol,
                clienteId
        );
    }
}
