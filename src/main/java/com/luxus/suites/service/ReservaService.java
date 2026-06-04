package com.luxus.suites.service;

import com.luxus.suites.model.ReservaSolicitud;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReservaService {

    private final List<ReservaSolicitud> solicitudes = new ArrayList<>();

    public void guardarSolicitud(ReservaSolicitud reserva) {
        solicitudes.add(reserva);
    }

    public List<ReservaSolicitud> listarSolicitudes() {
        return Collections.unmodifiableList(solicitudes);
    }

    public int contarSolicitudes() {
        return solicitudes.size();
    }
}