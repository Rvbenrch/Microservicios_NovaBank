package com.novabank.auth.dto;

public class ValidateResponse {

    private boolean valido;
    private String username;
    private String rol;
    private Long clienteId;

    public ValidateResponse() {
    }

    public ValidateResponse(boolean valido, String username, String rol, Long clienteId) {
        this.valido = valido;
        this.username = username;
        this.rol = rol;
        this.clienteId = clienteId;
    }

    public boolean isValido() {
        return valido;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }

    public Long getClienteId() {
        return clienteId;
    }
}