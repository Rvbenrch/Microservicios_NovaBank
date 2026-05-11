package com.novabank.cuenta.exception;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException() {
        super("La cuenta no dispone de saldo suficiente");
    }
}
