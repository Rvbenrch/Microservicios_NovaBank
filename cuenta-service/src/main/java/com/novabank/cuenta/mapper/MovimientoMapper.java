package com.novabank.cuenta.mapper;

import com.novabank.cuenta.dto.MovimientoDTO;
import com.novabank.cuenta.model.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {

    public MovimientoDTO toDTO(Movimiento movimiento) {

        if (movimiento == null) {
            return null;
        }

        MovimientoDTO dto = new MovimientoDTO();

        dto.setId(movimiento.getId());
        dto.setCuentaId(movimiento.getCuentaId());
        dto.setTipo(movimiento.getTipo());
        dto.setImporte(movimiento.getImporte());
        dto.setFecha(movimiento.getFecha());

        return dto;
    }
}