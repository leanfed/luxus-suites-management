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
        validarDatosReserva(nombre, email, telefono, checkin, checkout, suite);

        Suite suiteSeleccionada = suiteService.buscarPorNombre(suite.trim());

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
                nombre.trim(),
                email.trim(),
                telefono.trim(),
                checkin.trim(),
                checkout.trim(),
                suite.trim(),
                observaciones != null ? observaciones.trim() : "",
                precioPorNoche,
                noches,
                importeEstimado
        );

        return reservaSolicitudRepository.save(reserva);
    }

    public List<ReservaSolicitud> listarSolicitudes() {
        return reservaSolicitudRepository.findAllByOrderByIdDesc();
    }

    public List<ReservaSolicitud> listarSolicitudesPorEstado(String estado) {
        if (estado == null || estado.isBlank() || estado.equalsIgnoreCase("Todas")) {
            return listarSolicitudes();
        }

        return listarSolicitudes().stream()
                .filter(solicitud -> solicitud.getEstado().equalsIgnoreCase(estado))
                .toList();
    }

    public List<ReservaSolicitud> listarSolicitudesFiltradas(String estado, String busqueda) {
        return listarSolicitudesPorEstado(estado).stream()
                .filter(solicitud -> coincideConBusqueda(solicitud, busqueda))
                .toList();
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

        validarDatosReserva(nombre, email, telefono, checkin, checkout, suite);

        Suite suiteSeleccionada = suiteService.buscarPorNombre(suite.trim());

        if (suiteSeleccionada == null) {
            throw new IllegalArgumentException("La suite seleccionada no existe.");
        }

        Double precioPorNoche = suiteSeleccionada.getPrecioPorNoche();

        Long noches = calcularNoches(checkin, checkout);

        Double importeEstimado = precioPorNoche * noches;

        if ("Confirmada".equalsIgnoreCase(solicitud.getEstado())) {
            validarQueNoExistaReservaSuperpuesta(
                    solicitud.getId(),
                    suite.trim(),
                    checkin.trim(),
                    checkout.trim()
            );
        }

        solicitud.actualizarDatos(
                nombre.trim(),
                email.trim(),
                telefono.trim(),
                checkin.trim(),
                checkout.trim(),
                suite.trim(),
                observaciones != null ? observaciones.trim() : "",
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

        validarQueNoExistaReservaSuperpuesta(
                solicitud.getId(),
                solicitud.getSuite(),
                solicitud.getCheckin(),
                solicitud.getCheckout()
        );

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

    private void validarDatosReserva(
            String nombre,
            String email,
            String telefono,
            String checkin,
            String checkout,
            String suite
    ) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del huésped es obligatorio.");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email del huésped es obligatorio.");
        }

        if (!email.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("El email ingresado no tiene un formato válido.");
        }

        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono del huésped es obligatorio.");
        }

        if (checkin == null || checkin.isBlank()) {
            throw new IllegalArgumentException("La fecha de check-in es obligatoria.");
        }

        if (checkout == null || checkout.isBlank()) {
            throw new IllegalArgumentException("La fecha de check-out es obligatoria.");
        }

        if (suite == null || suite.isBlank()) {
            throw new IllegalArgumentException("La suite seleccionada es obligatoria.");
        }
    }

    private void validarQueNoExistaReservaSuperpuesta(
            Long idReservaActual,
            String suite,
            String checkin,
            String checkout
    ) {
        LocalDate nuevoCheckin = parsearFecha(checkin);
        LocalDate nuevoCheckout = parsearFecha(checkout);

        boolean existeSuperposicion = listarSolicitudes().stream()
                .filter(solicitud -> solicitud.getId() != null)
                .filter(solicitud -> !solicitud.getId().equals(idReservaActual))
                .filter(solicitud -> "Confirmada".equalsIgnoreCase(solicitud.getEstado()))
                .filter(solicitud -> solicitud.getSuite() != null)
                .filter(solicitud -> solicitud.getSuite().equalsIgnoreCase(suite))
                .anyMatch(solicitud -> haySuperposicion(
                        nuevoCheckin,
                        nuevoCheckout,
                        parsearFecha(solicitud.getCheckin()),
                        parsearFecha(solicitud.getCheckout())
                ));

        if (existeSuperposicion) {
            throw new IllegalArgumentException(
                    "No se puede confirmar la reserva porque ya existe una reserva confirmada para esa suite en fechas superpuestas."
            );
        }
    }

    private boolean haySuperposicion(
            LocalDate nuevoCheckin,
            LocalDate nuevoCheckout,
            LocalDate checkinExistente,
            LocalDate checkoutExistente
    ) {
        return nuevoCheckin.isBefore(checkoutExistente)
                && nuevoCheckout.isAfter(checkinExistente);
    }

    private LocalDate parsearFecha(String fecha) {
        try {
            return LocalDate.parse(fecha.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Las fechas ingresadas no tienen un formato válido.");
        }
    }

    private boolean coincideConBusqueda(ReservaSolicitud solicitud, String busqueda) {
        if (busqueda == null || busqueda.isBlank()) {
            return true;
        }

        String textoBuscado = busqueda.trim().toLowerCase();

        return contieneTexto(solicitud.getNombre(), textoBuscado)
                || contieneTexto(solicitud.getEmail(), textoBuscado)
                || contieneTexto(solicitud.getTelefono(), textoBuscado)
                || contieneTexto(solicitud.getSuite(), textoBuscado)
                || contieneTexto(solicitud.getEstado(), textoBuscado)
                || contieneTexto(String.valueOf(solicitud.getId()), textoBuscado);
    }

    private boolean contieneTexto(String valor, String busqueda) {
        return valor != null && valor.toLowerCase().contains(busqueda);
    }

    private Long calcularNoches(String checkin, String checkout) {
        LocalDate fechaCheckin = parsearFecha(checkin);
        LocalDate fechaCheckout = parsearFecha(checkout);

        long noches = ChronoUnit.DAYS.between(fechaCheckin, fechaCheckout);

        if (noches <= 0) {
            throw new IllegalArgumentException("El check-out debe ser posterior al check-in.");
        }

        return noches;
    }
}