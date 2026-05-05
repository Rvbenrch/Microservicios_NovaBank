package com.novabank.cliente.repository;

import com.novabank.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByDni(String dni);

    boolean existsByDni(String dni);

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByTelefonoAndIdNot(String telefono, Long id);
}