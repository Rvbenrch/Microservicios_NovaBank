package com.novabank.operacion.client;

import com.novabank.operacion.dto.ActualizarSaldoRequest;
import com.novabank.operacion.dto.CuentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CUENTA-SERVICE")
public interface CuentaServiceClient {

    @GetMapping("/api/cuentas/{id}")
    CuentaDTO obtenerCuentaPorId(@PathVariable Long id);

    @PutMapping("/api/cuentas/{id}/ingresar")
    void ingresar(
            @PathVariable Long id,
            @RequestBody ActualizarSaldoRequest request
    );

    @PutMapping("/api/cuentas/{id}/retirar")
    void retirar(
            @PathVariable Long id,
            @RequestBody ActualizarSaldoRequest request
    );
}