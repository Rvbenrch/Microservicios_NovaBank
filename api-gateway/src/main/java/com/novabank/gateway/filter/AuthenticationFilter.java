package com.novabank.gateway.filter;

import com.novabank.gateway.client.AuthClient;
import com.novabank.gateway.dto.ValidateResponse;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter {

    private final AuthClient authClient;

    public AuthenticationFilter(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (path.startsWith("/api/auth")) {
            return chain.filter(exchange);
        }

        String authorizationHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authorizationHeader.replace("Bearer ", "");

        return authClient.validarToken(token)
                .flatMap(validateResponse -> procesarValidacion(
                        validateResponse,
                        exchange,
                        chain,
                        path
                ))
                .onErrorResume(ex -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    private Mono<Void> procesarValidacion(
            ValidateResponse validateResponse,
            ServerWebExchange exchange,
            GatewayFilterChain chain,
            String path
    ) {

        if (validateResponse == null || !validateResponse.isValido()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        if (!tienePermiso(path, validateResponse)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder -> builder
                        .header("X-User-Name", validateResponse.getUsername())
                        .header("X-User-Role", validateResponse.getRol())
                        .header(
                                "X-Cliente-Id",
                                validateResponse.getClienteId() != null
                                        ? validateResponse.getClienteId().toString()
                                        : ""
                        )
                )
                .build();

        return chain.filter(mutatedExchange);
    }

    private boolean tienePermiso(String path, ValidateResponse usuario) {

        if ("ADMIN".equals(usuario.getRol())) {
            return true;
        }

        if ("CLIENTE".equals(usuario.getRol())) {

            if (path.equals("/api/clientes")) {
                return false;
            }

            if (path.equals("/api/cuentas")) {
                return false;
            }

            if (path.startsWith("/api/cuentas/cliente/")) {
                String clienteIdPath = path.replace("/api/cuentas/cliente/", "");
                return clienteIdPath.equals(String.valueOf(usuario.getClienteId()));
            }

            return true;
        }

        return false;
    }
}