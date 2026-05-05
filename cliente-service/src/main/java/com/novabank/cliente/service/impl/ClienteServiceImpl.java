package com.novabank.cliente.service.impl;

import com.novabank.cliente.dto.ActualizarClienteRequest;
import com.novabank.cliente.dto.ClienteDTO;
import com.novabank.cliente.dto.CrearClienteRequest;
import com.novabank.cliente.exception.ClienteNotFoundException;
import com.novabank.cliente.exception.ValidacionException;
import com.novabank.cliente.mapper.ClienteMapper;
import com.novabank.cliente.model.Cliente;
import com.novabank.cliente.repository.ClienteRepository;
import com.novabank.cliente.service.ClienteService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toDTO)
                .toList();
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        return clienteMapper.toDTO(cliente);
    }

    @Override
    public ClienteDTO obtenerClientePorDni(String dni) {
        Cliente cliente = clienteRepository.findByDni(dni)
                .orElseThrow(() -> new ClienteNotFoundException(dni));

        return clienteMapper.toDTO(cliente);
    }

    @Override
    public ClienteDTO crearCliente(CrearClienteRequest request) {
        validarDuplicadosCreacion(request);

        Cliente cliente = clienteMapper.toEntity(request);
        cliente.setFechaCreacion(LocalDateTime.now());

        Cliente clienteGuardado = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteGuardado);
    }

    @Override
    public ClienteDTO actualizarCliente(Long id, ActualizarClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        validarDuplicadosActualizacion(request, id);

        cliente.setNombre(request.getNombre());
        cliente.setApellidos(request.getApellidos());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());

        Cliente clienteActualizado = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteActualizado);
    }

    private void validarDuplicadosCreacion(CrearClienteRequest request) {
        if (clienteRepository.existsByDni(request.getDni())) {
            throw new ValidacionException("Ya existe un cliente con ese DNI");
        }

        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new ValidacionException("Ya existe un cliente con ese email");
        }

        if (clienteRepository.existsByTelefono(request.getTelefono())) {
            throw new ValidacionException("Ya existe un cliente con ese teléfono");
        }
    }

    private void validarDuplicadosActualizacion(ActualizarClienteRequest request, Long id) {
        if (clienteRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new ValidacionException("Ya existe un cliente con ese email");
        }

        if (clienteRepository.existsByTelefonoAndIdNot(request.getTelefono(), id)) {
            throw new ValidacionException("Ya existe un cliente con ese teléfono");
        }
    }
}
