package com.novabank.cuenta.service;

import com.novabank.cuenta.dto.ActualizarSaldoRequest;
import com.novabank.cuenta.dto.CuentaDTO;
import com.novabank.cuenta.dto.CrearCuentaRequest;
import com.novabank.cuenta.dto.MovimientoDTO;

import java.util.List;

public interface CuentaService {

    List<CuentaDTO> listarCuentasPorCliente(Long clienteId);

    CuentaDTO obtenerCuentaPorId(Long id);

    CuentaDTO obtenerCuentaPorNumero(String numeroCuenta);

    CuentaDTO crearCuenta(CrearCuentaRequest request);

    List<MovimientoDTO> obtenerMovimientos(Long cuentaId);

    void ingresar(Long cuentaId, ActualizarSaldoRequest request);

    void retirar(Long cuentaId, ActualizarSaldoRequest request);
}
