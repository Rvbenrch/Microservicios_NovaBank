package com.novabank.operacion.controller;

import com.novabank.operacion.dto.DepositoRequest;
import com.novabank.operacion.dto.RetiroRequest;
import com.novabank.operacion.dto.TransferenciaRequest;
import com.novabank.operacion.service.OperacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operaciones")
public class OperacionController {

    private final OperacionService operacionService;

    public OperacionController(OperacionService operacionService) {
        this.operacionService = operacionService;
    }

    @PostMapping("/deposito")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void realizarDeposito(@Valid @RequestBody DepositoRequest request) {
        operacionService.realizarDeposito(request);
    }

    @PostMapping("/retiro")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void realizarRetiro(@Valid @RequestBody RetiroRequest request) {
        operacionService.realizarRetiro(request);
    }

    @PostMapping("/transferencia")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void realizarTransferencia(@Valid @RequestBody TransferenciaRequest request) {
        operacionService.realizarTransferencia(request);
    }
}