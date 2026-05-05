package com.novabank.cliente.mapper;

import com.novabank.cliente.dto.ClienteDTO;
import com.novabank.cliente.dto.CrearClienteRequest;
import com.novabank.cliente.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellidos(cliente.getApellidos());
        dto.setDni(cliente.getDni());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setFechaCreacion(cliente.getFechaCreacion());

        return dto;
    }

    public Cliente toEntity(CrearClienteRequest request) {
        if (request == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setApellidos(request.getApellidos());
        cliente.setDni(request.getDni());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());

        return cliente;
    }
}