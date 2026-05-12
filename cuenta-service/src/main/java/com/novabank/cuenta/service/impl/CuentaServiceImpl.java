package com.novabank.cuenta.service.impl;

import com.novabank.cuenta.cliente.ClienteServiceClient;
import com.novabank.cuenta.dto.*;
import com.novabank.cuenta.exception.ClienteNoExisteException;
import com.novabank.cuenta.exception.CuentaNotFoundException;
import com.novabank.cuenta.exception.SaldoInsuficienteException;
import com.novabank.cuenta.mapper.CuentaMapper;
import com.novabank.cuenta.mapper.MovimientoMapper;
import com.novabank.cuenta.model.Cuenta;
import com.novabank.cuenta.model.Movimiento;
import com.novabank.cuenta.model.TipoMovimiento;
import com.novabank.cuenta.repository.CuentaRepository;
import com.novabank.cuenta.repository.MovimientoRepository;
import com.novabank.cuenta.service.CuentaService;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final CuentaMapper cuentaMapper;
    private final MovimientoMapper movimientoMapper;
    private final ClienteServiceClient clienteServiceClient;

    public CuentaServiceImpl(
            CuentaRepository cuentaRepository,
            MovimientoRepository movimientoRepository,
            CuentaMapper cuentaMapper,
            MovimientoMapper movimientoMapper,
            ClienteServiceClient clienteServiceClient
    ) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.cuentaMapper = cuentaMapper;
        this.movimientoMapper = movimientoMapper;
        this.clienteServiceClient = clienteServiceClient;
    }

    @Override
    public List<CuentaDTO> listarCuentasPorCliente(Long clienteId) {

        return cuentaRepository.findByClienteId(clienteId)
                .stream()
                .map(cuentaMapper::toDTO)
                .toList();
    }

    @Override
    public CuentaDTO obtenerCuentaPorId(Long id) {

        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException(id));

        return cuentaMapper.toDTO(cuenta);
    }

    @Override
    public CuentaDTO obtenerCuentaPorNumero(String numeroCuenta) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaNotFoundException(numeroCuenta));

        return cuentaMapper.toDTO(cuenta);
    }

    @Override
    public CuentaDTO crearCuenta(CrearCuentaRequest request) {

        validarClienteExiste(request.getClienteId());

        Cuenta cuenta = new Cuenta();

        cuenta.setClienteId(request.getClienteId());
        cuenta.setNumeroCuenta(generarNumeroCuenta());
        cuenta.setSaldo(BigDecimal.ZERO);
        cuenta.setFechaCreacion(LocalDateTime.now());

        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        return cuentaMapper.toDTO(cuentaGuardada);
    }

    @Override
    public List<MovimientoDTO> obtenerMovimientos(Long cuentaId) {

        obtenerCuentaEntidad(cuentaId);

        return movimientoRepository.findByCuentaId(cuentaId)
                .stream()
                .map(movimientoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void ingresar(Long cuentaId, ActualizarSaldoRequest request) {

        Cuenta cuenta = obtenerCuentaEntidadBloqueada(cuentaId);

        cuenta.setSaldo(
                cuenta.getSaldo().add(request.getImporte())
        );

        cuentaRepository.save(cuenta);

        registrarMovimiento(
                cuentaId,
                TipoMovimiento.DEPOSITO,
                request.getImporte()
        );
    }

    @Override
    @Transactional
    public void retirar(Long cuentaId, ActualizarSaldoRequest request) {

        Cuenta cuenta = obtenerCuentaEntidadBloqueada(cuentaId);

        if (cuenta.getSaldo().compareTo(request.getImporte()) < 0) {
            throw new SaldoInsuficienteException();
        }

        cuenta.setSaldo(
                cuenta.getSaldo().subtract(request.getImporte())
        );

        cuentaRepository.save(cuenta);

        registrarMovimiento(
                cuentaId,
                TipoMovimiento.RETIRO,
                request.getImporte()
        );
    }

    private Cuenta obtenerCuentaEntidad(Long cuentaId) {

        return cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaNotFoundException(cuentaId));
    }
    private Cuenta obtenerCuentaEntidadBloqueada(Long cuentaId) {

        return cuentaRepository.findByIdForUpdate(cuentaId)
                .orElseThrow(() -> new CuentaNotFoundException(cuentaId));
    }
    private void validarClienteExiste(Long clienteId) {

        try {
            clienteServiceClient.obtenerClientePorId(clienteId);
        } catch (FeignException.NotFound ex) {
            throw new ClienteNoExisteException(clienteId);
        }
    }

    private String generarNumeroCuenta() {

        Random random = new Random();

        String numeroCuenta;

        do {
            numeroCuenta = "ES" + (10000000 + random.nextInt(90000000));
        } while (cuentaRepository.existsByNumeroCuenta(numeroCuenta));

        return numeroCuenta;
    }

    private void registrarMovimiento(
            Long cuentaId,
            TipoMovimiento tipo,
            BigDecimal importe
    ) {

        Movimiento movimiento = new Movimiento();

        movimiento.setCuentaId(cuentaId);
        movimiento.setTipo(tipo);
        movimiento.setImporte(importe);
        movimiento.setFecha(LocalDateTime.now());

        movimientoRepository.save(movimiento);
    }
    @Override
    public List<CuentaDTO> listarCuentas() {
        return cuentaRepository.findAll()
                .stream()
                .map(cuentaMapper::toDTO)
                .toList();
    }
}