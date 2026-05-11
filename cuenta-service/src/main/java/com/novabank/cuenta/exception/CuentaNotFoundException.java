package com.novabank.cuenta.exception;

public class CuentaNotFoundException extends RuntimeException {

    public CuentaNotFoundException(Long id) {
        super("No se ha encontrado ninguna cuenta con id: " + id);
    }

    public CuentaNotFoundException(String numeroCuenta) {
        super("No se ha encontrado ninguna cuenta con número: " + numeroCuenta);
    }
}