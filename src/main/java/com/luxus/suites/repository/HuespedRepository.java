package com.luxus.suites.repository;

import com.luxus.suites.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    Optional<Huesped> findByEmailIgnoreCase(String email);

    Optional<Huesped> findByTelefono(String telefono);
}