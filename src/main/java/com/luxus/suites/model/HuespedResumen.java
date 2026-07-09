package com.luxus.suites.model;

public class HuespedResumen {

    private String nombre;
    private String email;
    private String telefono;
    private Long totalReservas;
    private Long reservasConfirmadas;
    private Double importeAsociado;
    private String ultimaReserva;

    public HuespedResumen(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.totalReservas = 0L;
        this.reservasConfirmadas = 0L;
        this.importeAsociado = 0.0;
        this.ultimaReserva = "";
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Long getTotalReservas() {
        return totalReservas;
    }

    public Long getReservasConfirmadas() {
        return reservasConfirmadas;
    }

    public Double getImporteAsociado() {
        return importeAsociado;
    }

    public String getUltimaReserva() {
        return ultimaReserva;
    }

    public void registrarReserva(ReservaSolicitud reserva) {
        this.totalReservas++;

        if ("Confirmada".equalsIgnoreCase(reserva.getEstado())) {
            this.reservasConfirmadas++;
        }

        if (!"Cancelada".equalsIgnoreCase(reserva.getEstado()) && reserva.getImporteEstimado() != null) {
            this.importeAsociado += reserva.getImporteEstimado();
        }

        if (reserva.getCheckin() != null && !reserva.getCheckin().isBlank()) {
            actualizarUltimaReserva(reserva.getCheckin());
        }
    }

    private void actualizarUltimaReserva(String checkin) {
        if (this.ultimaReserva == null || this.ultimaReserva.isBlank()) {
            this.ultimaReserva = checkin;
            return;
        }

        if (checkin.compareTo(this.ultimaReserva) > 0) {
            this.ultimaReserva = checkin;
        }
    }
}