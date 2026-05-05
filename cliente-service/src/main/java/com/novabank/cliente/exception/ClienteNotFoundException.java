package com.novabank.cliente.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(Long id) {
        super("No se ha encontrado ningún cliente con id: " + id);
    }

    public ClienteNotFoundException(String dni) {
        super("No se ha encontrado ningún cliente con dni: " + dni);
    }
}