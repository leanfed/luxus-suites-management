package com.luxus.suites.service;

import com.luxus.suites.model.Huesped;
import com.luxus.suites.repository.HuespedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HuespedService {

    private final HuespedRepository huespedRepository;

    public HuespedService(HuespedRepository huespedRepository) {
        this.huespedRepository = huespedRepository;
    }

    public Huesped obtenerOCrearHuesped(
            String nombre,
            String email,
            String telefono
    ) {
        String nombreLimpio = limpiarTexto(nombre);
        String emailLimpio = limpiarTexto(email).toLowerCase();
        String telefonoLimpio = limpiarTexto(telefono);

        Optional<Huesped> huespedExistente = huespedRepository.findByEmailIgnoreCase(emailLimpio);

        if (huespedExistente.isPresent()) {
            Huesped huesped = huespedExistente.get();

            huesped.actualizarDatos(
                    nombreLimpio,
                    emailLimpio,
                    telefonoLimpio
            );

            return huespedRepository.save(huesped);
        }

        Huesped nuevoHuesped = new Huesped(
                null,
                nombreLimpio,
                emailLimpio,
                telefonoLimpio
        );

        return huespedRepository.save(nuevoHuesped);
    }

    public List<Huesped> listarHuespedes() {
        return huespedRepository.findAll();
    }

    public Huesped buscarPorId(Long id) {
        return huespedRepository.findById(id).orElse(null);
    }

    private String limpiarTexto(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.trim();
    }
}