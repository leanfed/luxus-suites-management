package com.luxus.suites.service;

import com.luxus.suites.model.Suite;
import com.luxus.suites.repository.SuiteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuiteService {

    private final SuiteRepository suiteRepository;

    public SuiteService(SuiteRepository suiteRepository) {
        this.suiteRepository = suiteRepository;
    }

    @PostConstruct
    public void cargarSuitesIniciales() {
        if (suiteRepository.count() > 0) {
            return;
        }

        List<Suite> suitesIniciales = List.of(
                new Suite(
                        null,
                        "Deluxe Suite",
                        "Suite elegante para estadías ejecutivas o escapadas urbanas, con escritorio privado, amenities premium y diseño contemporáneo.",
                        "Deluxe",
                        180000.0,
                        2,
                        true
                ),
                new Suite(
                        null,
                        "Presidential Suite",
                        "Suite exclusiva con vista panorámica, living privado, bañera de hidromasaje y atención personalizada.",
                        "Presidential",
                        390000.0,
                        4,
                        true
                ),
                new Suite(
                        null,
                        "Ocean View Suite",
                        "Suite luminosa con balcón privado, cama king size, minibar premium y ambientación sofisticada.",
                        "Ocean View",
                        260000.0,
                        2,
                        true
                )
        );

        suiteRepository.saveAll(suitesIniciales);
    }

    public List<Suite> listarSuites() {
        return suiteRepository.findAll();
    }

    public List<Suite> listarSuitesDisponibles() {
        return suiteRepository.findByDisponibleTrue();
    }

    public Suite buscarPorId(Long id) {
        return suiteRepository.findById(id).orElse(null);
    }

    public Suite buscarPorNombre(String nombre) {
        return suiteRepository.findByNombre(nombre);
    }

    public void crearSuite(
            String nombre,
            String descripcion,
            String categoria,
            Double precioPorNoche,
            Integer capacidad,
            Boolean disponible
    ) {
        validarDatosSuite(nombre, descripcion, categoria, precioPorNoche, capacidad);

        Suite suite = new Suite(
                null,
                nombre.trim(),
                descripcion.trim(),
                categoria.trim(),
                precioPorNoche,
                capacidad,
                disponible
        );

        suiteRepository.save(suite);
    }

    public void actualizarSuite(
            Long id,
            String nombre,
            String descripcion,
            String categoria,
            Double precioPorNoche,
            Integer capacidad,
            Boolean disponible
    ) {
        Suite suite = buscarPorId(id);

        if (suite == null) {
            return;
        }

        validarDatosSuite(nombre, descripcion, categoria, precioPorNoche, capacidad);

        suite.actualizarDatos(
                nombre.trim(),
                descripcion.trim(),
                categoria.trim(),
                precioPorNoche,
                capacidad,
                disponible
        );

        suiteRepository.save(suite);
    }

    private void validarDatosSuite(
            String nombre,
            String descripcion,
            String categoria,
            Double precioPorNoche,
            Integer capacidad
    ) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la suite es obligatorio.");
        }

        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("La categoría de la suite es obligatoria.");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción de la suite es obligatoria.");
        }

        if (precioPorNoche == null || precioPorNoche <= 0) {
            throw new IllegalArgumentException("El precio por noche debe ser mayor a cero.");
        }

        if (capacidad == null || capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a cero.");
        }
    }
}