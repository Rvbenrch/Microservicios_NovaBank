package com.novabank.cuenta.dto;

import jakarta.validation.constraints.NotNull;

public class CrearCuentaRequest {

    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;

    public CrearCuentaRequest() {
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
