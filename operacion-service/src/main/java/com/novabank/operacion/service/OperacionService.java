package com.novabank.operacion.service;

import com.novabank.operacion.dto.DepositoRequest;
import com.novabank.operacion.dto.RetiroRequest;
import com.novabank.operacion.dto.TransferenciaRequest;

public interface OperacionService {

    void realizarDeposito(DepositoRequest request);

    void realizarRetiro(RetiroRequest request);

    void realizarTransferencia(TransferenciaRequest request);
}