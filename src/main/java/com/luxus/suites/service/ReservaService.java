package com.luxus.suites.service;

import com.luxus.suites.model.ReservaSolicitud;
import com.luxus.suites.model.Suite;
import com.luxus.suites.repository.ReservaSolicitudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaSolicitudRepository reservaSolicitudRepository;
    private final SuiteService suiteService;

    public ReservaService(
            ReservaSolicitudRepository reservaSolicitudRepository,
            SuiteService suiteService
    ) {
        this.reservaSolicitudRepository = reservaSolicitudRepository;
        this.suiteService = suiteService;
    }

    public ReservaSolicitud guardarSolicitud(
            String nombre,
            String email,
            String telefono,
            String checkin,
            String checkout,
            String suite,
            String observaciones
    ) {
        Suite suiteSeleccionada = suiteService.buscarPorNombre(suite);

        if (suiteSeleccionada == null) {
            throw new IllegalArgumentException("La suite seleccionada no existe.");
        }

        if (!Boolean.TRUE.equals(suiteSeleccionada.getDisponible())) {
            throw new IllegalArgumentException("La suite seleccionada no está disponible para reservas.");
        }

        Double precioPorNoche = suiteSeleccionada.getPrecioPorNoche();

        Long noches = calcularNoches(checkin, checkout);

        Double importeEstimado = precioPorNoche * noches;

        ReservaSolicitud reserva = new ReservaSolicitud(
                null,
                nombre,
                email,
                telefono,
                checkin,
                checkout,
                suite,
                observaciones,
                precioPorNoche,
                noches,
                importeEstimado
        );

        return reservaSolicitudRepository.save(reserva);
    }

    public List<ReservaSolicitud> listarSolicitudes() {
        return reservaSolicitudRepository.findAll();
    }

    public ReservaSolicitud buscarPorId(Long id) {
        return reservaSolicitudRepository.findById(id).orElse(null);
    }

    public void actualizarSolicitud(
            Long id,
            String nombre,
            String email,
            String telefono,
            String checkin,
            String checkout,
            String suite,
            String observaciones
    ) {
        ReservaSolicitud solicitud = buscarPorId(id);

        if (solicitud == null) {
            return;
        }

        Suite suiteSeleccionada = suiteService.buscarPorNombre(suite);

        if (suiteSeleccionada == null) {
            throw new IllegalArgumentException("La suite seleccionada no existe.");
        }

        Double precioPorNoche = suiteSeleccionada.getPrecioPorNoche();

        Long noches = calcularNoches(checkin, checkout);

        Double importeEstimado = precioPorNoche * noches;

        solicitud.actualizarDatos(
                nombre,
                email,
                telefono,
                checkin,
                checkout,
                suite,
                observaciones,
                precioPorNoche,
                noches,
                importeEstimado
        );

        reservaSolicitudRepository.save(solicitud);
    }

    public int contarSolicitudes() {
        return (int) reservaSolicitudRepository.count();
    }

    public long contarPendientes() {
        return listarSolicitudes().stream()
                .filter(solicitud -> solicitud.getEstado().equals("Pendiente"))
                .count();
    }

    public long contarConfirmadas() {
        return listarSolicitudes().stream()
                .filter(solicitud -> solicitud.getEstado().equals("Confirmada"))
                .count();
    }

    public long contarCanceladas() {
        return listarSolicitudes().stream()
                .filter(solicitud -> solicitud.getEstado().equals("Cancelada"))
                .count();
    }

    public Double calcularIngresosEstimados() {
        return listarSolicitudes().stream()
                .filter(solicitud -> !solicitud.getEstado().equals("Cancelada"))
                .mapToDouble(solicitud -> solicitud.getImporteEstimado() != null
                        ? solicitud.getImporteEstimado()
                        : 0.0)
                .sum();
    }

    public Double calcularIngresosConfirmados() {
        return listarSolicitudes().stream()
                .filter(solicitud -> solicitud.getEstado().equals("Confirmada"))
                .mapToDouble(solicitud -> solicitud.getImporteEstimado() != null
                        ? solicitud.getImporteEstimado()
                        : 0.0)
                .sum();
    }

    public void confirmarSolicitud(Long id) {
        ReservaSolicitud solicitud = buscarPorId(id);

        if (solicitud == null) {
            return;
        }

        solicitud.confirmar();
        reservaSolicitudRepository.save(solicitud);
    }

    public void cancelarSolicitud(Long id) {
        ReservaSolicitud solicitud = buscarPorId(id);

        if (solicitud == null) {
            return;
        }

        solicitud.cancelar();
        reservaSolicitudRepository.save(solicitud);
    }

    public void reabrirSolicitud(Long id) {
        ReservaSolicitud solicitud = buscarPorId(id);

        if (solicitud == null) {
            return;
        }

        solicitud.reabrir();
        reservaSolicitudRepository.save(solicitud);
    }

    private Long calcularNoches(String checkin, String checkout) {
        LocalDate fechaCheckin;
        LocalDate fechaCheckout;

        try {
            fechaCheckin = LocalDate.parse(checkin);
            fechaCheckout = LocalDate.parse(checkout);
        } catch (Exception e) {
            throw new IllegalArgumentException("Las fechas ingresadas no tienen un formato válido.");
        }

        long noches = ChronoUnit.DAYS.between(fechaCheckin, fechaCheckout);

        if (noches <= 0) {
            throw new IllegalArgumentException("El check-out debe ser posterior al check-in.");
        }

        return noches;
    }
}