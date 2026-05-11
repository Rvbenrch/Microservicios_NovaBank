package com.novabank.cuenta.exception;

public class ClienteNoExisteException extends RuntimeException {

    public ClienteNoExisteException(Long clienteId) {
        super("No existe ningún cliente con id: " + clienteId);
    }
}
