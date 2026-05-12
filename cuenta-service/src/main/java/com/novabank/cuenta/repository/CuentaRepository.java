package com.novabank.cuenta.repository;

import com.novabank.cuenta.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    List<Cuenta> findByClienteId(Long clienteId);

    boolean existsByNumeroCuenta(String numeroCuenta);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cuenta c WHERE c.id = :id")
    Optional<Cuenta> findByIdForUpdate(@Param("id") Long id);
}