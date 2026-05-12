package com.novabank.gateway.dto;

public class ValidateResponse {

    private boolean valido;
    private String username;
    private String rol;
    private Long clienteId;

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

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}