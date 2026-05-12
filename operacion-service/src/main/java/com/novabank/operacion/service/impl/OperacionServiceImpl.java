package com.novabank.operacion.service.impl;

import com.novabank.operacion.client.CuentaServiceClient;
import com.novabank.operacion.dto.ActualizarSaldoRequest;
import com.novabank.operacion.dto.CuentaDTO;
import com.novabank.operacion.dto.DepositoRequest;
import com.novabank.operacion.dto.RetiroRequest;
import com.novabank.operacion.dto.TransferenciaRequest;
import com.novabank.operacion.exception.OperacionException;
import com.novabank.operacion.service.OperacionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class OperacionServiceImpl implements OperacionService {

    private final CuentaServiceClient cuentaServiceClient;

    public OperacionServiceImpl(CuentaServiceClient cuentaServiceClient) {
        this.cuentaServiceClient = cuentaServiceClient;
    }

    @Override
    @Retry(name = "cuentaService", fallbackMethod = "fallbackDeposito")
    @CircuitBreaker(name = "cuentaService", fallbackMethod = "fallbackDeposito")
    public void realizarDeposito(DepositoRequest request) {
        cuentaServiceClient.obtenerCuentaPorId(request.getCuentaId());
        cuentaServiceClient.ingresar(
                request.getCuentaId(),
                new ActualizarSaldoRequest(request.getImporte())
        );
    }

    @Override
    @Retry(name = "cuentaService", fallbackMethod = "fallbackRetiro")
    @CircuitBreaker(name = "cuentaService", fallbackMethod = "fallbackRetiro")
    public void realizarRetiro(RetiroRequest request) {
        cuentaServiceClient.obtenerCuentaPorId(request.getCuentaId());
        cuentaServiceClient.retirar(
                request.getCuentaId(),
                new ActualizarSaldoRequest(request.getImporte())
        );
    }

    @Override
    @Retry(name = "cuentaService", fallbackMethod = "fallbackTransferencia")
    @CircuitBreaker(name = "cuentaService", fallbackMethod = "fallbackTransferencia")
    public void realizarTransferencia(TransferenciaRequest request) {
        if (request.getCuentaOrigenId().equals(request.getCuentaDestinoId())) {
            throw new OperacionException("La cuenta origen y destino no pueden ser la misma");
        }

        CuentaDTO cuentaOrigen = cuentaServiceClient.obtenerCuentaPorId(request.getCuentaOrigenId());
        CuentaDTO cuentaDestino = cuentaServiceClient.obtenerCuentaPorId(request.getCuentaDestinoId());

        if (cuentaOrigen.getSaldo().compareTo(request.getImporte()) < 0) {
            throw new OperacionException("La cuenta origen no dispone de saldo suficiente");
        }

        cuentaServiceClient.retirar(
                cuentaOrigen.getId(),
                new ActualizarSaldoRequest(request.getImporte())
        );

        cuentaServiceClient.ingresar(
                cuentaDestino.getId(),
                new ActualizarSaldoRequest(request.getImporte())
        );
    }

    private void fallbackDeposito(DepositoRequest request, Throwable ex) {
        throw new OperacionException("No se ha podido realizar el depósito porque cuenta-service no está disponible");
    }

    private void fallbackRetiro(RetiroRequest request, Throwable ex) {
        throw new OperacionException("No se ha podido realizar la retirada porque cuenta-service no está disponible");
    }

    private void fallbackTransferencia(TransferenciaRequest request, Throwable ex) {
        throw new OperacionException("No se ha podido realizar la transferencia porque cuenta-service no está disponible");
    }
}
