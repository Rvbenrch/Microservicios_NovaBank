package com.novabank.gateway.client;

import com.novabank.gateway.dto.ValidateResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:9001")
                .build();
    }

    public ValidateResponse validarToken(String token) {
        return webClient.get()
                .uri("/api/auth/validate")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ValidateResponse.class)
                .block();
    }
}