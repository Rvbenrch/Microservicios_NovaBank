package com.novabank.operacion.dto;

import java.math.BigDecimal;

public class CuentaDTO {

    private Long id;
    private String numeroCuenta;
    private Long clienteId;
    private BigDecimal saldo;

    public CuentaDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}