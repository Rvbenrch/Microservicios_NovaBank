package com.novabank.cliente.service;

import com.novabank.cliente.dto.ActualizarClienteRequest;
import com.novabank.cliente.dto.ClienteDTO;
import com.novabank.cliente.dto.CrearClienteRequest;

import java.util.List;

public interface ClienteService {

    List<ClienteDTO> listarClientes();

    ClienteDTO obtenerClientePorId(Long id);

    ClienteDTO obtenerClientePorDni(String dni);

    ClienteDTO crearCliente(CrearClienteRequest request);

    ClienteDTO actualizarCliente(Long id, ActualizarClienteRequest request);
}