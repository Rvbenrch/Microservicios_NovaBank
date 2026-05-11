package com.novabank.cuenta.dto;

import com.novabank.cuenta.model.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimientoDTO {

    private Long id;
    private Long cuentaId;
    private TipoMovimiento tipo;
    private BigDecimal importe;
    private LocalDateTime fecha;

    public MovimientoDTO() {
    }

    public Long getId() {
        return id;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
