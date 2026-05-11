package com.novabank.operacion.dto;

import java.math.BigDecimal;

public class ActualizarSaldoRequest {

    private BigDecimal importe;

    public ActualizarSaldoRequest() {
    }

    public ActualizarSaldoRequest(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
}