package com.luxus.suites.repository;

import com.luxus.suites.model.Suite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuiteRepository extends JpaRepository<Suite, Long> {

    Suite findByNombre(String nombre);

    List<Suite> findByDisponibleTrue();
}