package com.novabank.cliente.controller;

import com.novabank.cliente.dto.ActualizarClienteRequest;
import com.novabank.cliente.dto.ClienteDTO;
import com.novabank.cliente.dto.CrearClienteRequest;
import com.novabank.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteDTO> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public ClienteDTO obtenerClientePorId(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    @GetMapping("/dni/{dni}")
    public ClienteDTO obtenerClientePorDni(@PathVariable String dni) {
        return clienteService.obtenerClientePorDni(dni);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO crearCliente(@Valid @RequestBody CrearClienteRequest request) {
        return clienteService.crearCliente(request);
    }

    @PutMapping("/{id}")
    public ClienteDTO actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarClienteRequest request
    ) {
        return clienteService.actualizarCliente(id, request);
    }
}