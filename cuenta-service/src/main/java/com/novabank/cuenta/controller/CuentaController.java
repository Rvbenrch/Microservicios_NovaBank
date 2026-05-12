package com.novabank.cuenta.controller;

import com.novabank.cuenta.dto.ActualizarSaldoRequest;
import com.novabank.cuenta.dto.CuentaDTO;
import com.novabank.cuenta.dto.CrearCuentaRequest;
import com.novabank.cuenta.dto.MovimientoDTO;
import com.novabank.cuenta.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/cliente/{clienteId}")
    public List<CuentaDTO> listarCuentasPorCliente(
            @PathVariable Long clienteId
    ) {

        return cuentaService.listarCuentasPorCliente(clienteId);
    }

    @GetMapping("/{id}")
    public CuentaDTO obtenerCuentaPorId(
            @PathVariable Long id
    ) {

        return cuentaService.obtenerCuentaPorId(id);
    }

    @GetMapping("/numero/{numeroCuenta}")
    public CuentaDTO obtenerCuentaPorNumero(
            @PathVariable String numeroCuenta
    ) {

        return cuentaService.obtenerCuentaPorNumero(numeroCuenta);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaDTO crearCuenta(
            @Valid @RequestBody CrearCuentaRequest request
    ) {

        return cuentaService.crearCuenta(request);
    }

    @GetMapping("/{id}/movimientos")
    public List<MovimientoDTO> obtenerMovimientos(
            @PathVariable Long id
    ) {

        return cuentaService.obtenerMovimientos(id);
    }

    @PutMapping("/{id}/ingresar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ingresar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarSaldoRequest request
    ) {

        cuentaService.ingresar(id, request);
    }

    @PutMapping("/{id}/retirar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retirar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarSaldoRequest request
    ) {

        cuentaService.retirar(id, request);
    }
    @GetMapping
    public List<CuentaDTO> listarCuentas() {
        return cuentaService.listarCuentas();
    }
}