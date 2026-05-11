package com.novabank.cuenta.mapper;

import com.novabank.cuenta.dto.CuentaDTO;
import com.novabank.cuenta.model.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public CuentaDTO toDTO(Cuenta cuenta) {

        if (cuenta == null) {
            return null;
        }

        CuentaDTO dto = new CuentaDTO();

        dto.setId(cuenta.getId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setClienteId(cuenta.getClienteId());
        dto.setSaldo(cuenta.getSaldo());
        dto.setFechaCreacion(cuenta.getFechaCreacion());

        return dto;
    }
}