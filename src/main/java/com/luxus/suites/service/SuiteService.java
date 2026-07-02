package com.luxus.suites.service;

import com.luxus.suites.model.Suite;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuiteService {

    private final List<Suite> suites = List.of(
            new Suite(
                    1L,
                    "Deluxe Suite",
                    "Suite elegante para estadías ejecutivas o escapadas urbanas, con escritorio privado, amenities premium y diseño contemporáneo.",
                    "Deluxe",
                    180000.0,
                    2,
                    true
            ),
            new Suite(
                    2L,
                    "Presidential Suite",
                    "Suite exclusiva con vista panorámica, living privado, bañera de hidromasaje y atención personalizada.",
                    "Presidential",
                    390000.0,
                    4,
                    true
            ),
            new Suite(
                    3L,
                    "Ocean View Suite",
                    "Suite luminosa con balcón privado, cama king size, minibar premium y ambientación sofisticada.",
                    "Ocean View",
                    260000.0,
                    2,
                    true
            )
    );

    public List<Suite> listarSuites() {
        return suites;
    }

    public Suite buscarPorNombre(String nombre) {
        return suites.stream()
                .filter(suite -> suite.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }
}