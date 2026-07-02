package com.luxus.suites.service;

import com.luxus.suites.model.ReservaSolicitud;
import com.luxus.suites.model.Suite;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReservaService {

    private final List<ReservaSolicitud> solicitudes = new ArrayList<>();
    private final SuiteService suiteService;
    private Long proximoId = 1L;

    public ReservaService(SuiteService suiteService) {
        this.suiteService = suiteService;
    }

    public ReservaSolicitud guardarSolicitud(
            String nombre,
            String email,
            String checkin,
            String checkout,
            String suite
    ) {
        Suite suiteSeleccionada = suiteService.buscarPorNombre(suite);

        Double precioPorNoche = suiteSeleccionada != null
                ? suiteSeleccionada.getPrecioPorNoche()
                : 0.0;

        Long noches = calcularNoches(checkin, checkout);

        Double importeEstimado = precioPorNoche * noches;

        ReservaSolicitud reserva = new ReservaSolicitud(
                proximoId,
                nombre,
                email,
                checkin,
                checkout,
                suite,
                precioPorNoche,
                noches,
                importeEstimado
        );

        solicitudes.add(reserva);
        proximoId++;

        return reserva;
    }

    public List<ReservaSolicitud> listarSolicitudes() {
        return Collections.unmodifiableList(solicitudes);
    }

    public ReservaSolicitud buscarPorId(Long id) {
        return solicitudes.stream()
                .filter(solicitud -> solicitud.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void actualizarSolicitud(
            Long id,
            String nombre,
            String email,
            String checkin,
            String checkout,
            String suite
    ) {
        ReservaSolicitud solicitud = buscarPorId(id);

        if (solicitud == null) {
            return;
        }

        Suite suiteSeleccionada = suiteService.buscarPorNombre(suite);

        Double precioPorNoche = suiteSeleccionada != null
                ? suiteSeleccionada.getPrecioPorNoche()
                : 0.0;

        Long noches = calcularNoches(checkin, checkout);

        Double importeEstimado = precioPorNoche * noches;

        solicitud.actualizarDatos(
                nombre,
                email,
                checkin,
                checkout,
                suite,
                precioPorNoche,
                noches,
                importeEstimado
        );
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

    public Double calcularIngresosEstimados() {
        return solicitudes.stream()
                .filter(solicitud -> !solicitud.getEstado().equals("Cancelada"))
                .mapToDouble(solicitud -> solicitud.getImporteEstimado() != null
                        ? solicitud.getImporteEstimado()
                        : 0.0)
                .sum();
    }

    public Double calcularIngresosConfirmados() {
        return solicitudes.stream()
                .filter(solicitud -> solicitud.getEstado().equals("Confirmada"))
                .mapToDouble(solicitud -> solicitud.getImporteEstimado() != null
                        ? solicitud.getImporteEstimado()
                        : 0.0)
                .sum();
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

    public void reabrirSolicitud(Long id) {
        solicitudes.stream()
                .filter(solicitud -> solicitud.getId().equals(id))
                .findFirst()
                .ifPresent(ReservaSolicitud::reabrir);
    }

    private Long calcularNoches(String checkin, String checkout) {
        try {
            LocalDate fechaCheckin = LocalDate.parse(checkin);
            LocalDate fechaCheckout = LocalDate.parse(checkout);

            long noches = ChronoUnit.DAYS.between(fechaCheckin, fechaCheckout);

            return noches > 0 ? noches : 1L;
        } catch (Exception e) {
            return 1L;
        }
    }
}