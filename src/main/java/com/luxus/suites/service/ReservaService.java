package com.luxus.suites.service;

import com.luxus.suites.model.ReservaSolicitud;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReservaService {

    private final List<ReservaSolicitud> solicitudes = new ArrayList<>();
    private Long proximoId = 1L;

    public ReservaSolicitud guardarSolicitud(
            String nombre,
            String email,
            String checkin,
            String checkout,
            String suite
    ) {
        ReservaSolicitud reserva = new ReservaSolicitud(
                proximoId,
                nombre,
                email,
                checkin,
                checkout,
                suite
        );

        solicitudes.add(reserva);
        proximoId++;

        return reserva;
    }

    public List<ReservaSolicitud> listarSolicitudes() {
        return Collections.unmodifiableList(solicitudes);
    }

    public int contarSolicitudes() {
        return solicitudes.size();
    }

    public long contarPendientes() {
        return solicitudes.stream()
                .filter(solicitud -> solicitud.getEstado().equals("Pendiente"))
                .count();
    }

    public long contarConfirmadas() {
        return solicitudes.stream()
                .filter(solicitud -> solicitud.getEstado().equals("Confirmada"))
                .count();
    }

    public long contarCanceladas() {
        return solicitudes.stream()
                .filter(solicitud -> solicitud.getEstado().equals("Cancelada"))
                .count();
    }

    public void confirmarSolicitud(Long id) {
        solicitudes.stream()
                .filter(solicitud -> solicitud.getId().equals(id))
                .findFirst()
                .ifPresent(ReservaSolicitud::confirmar);
    }

    public void cancelarSolicitud(Long id) {
        solicitudes.stream()
                .filter(solicitud -> solicitud.getId().equals(id))
                .findFirst()
                .ifPresent(ReservaSolicitud::cancelar);
    }
}