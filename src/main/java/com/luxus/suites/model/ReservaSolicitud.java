package com.luxus.suites.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reservas_solicitudes")
public class ReservaSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String telefono;
    private String checkin;
    private String checkout;
    private String suite;
    private String observaciones;
    private Double precioPorNoche;
    private Long noches;
    private Double importeEstimado;
    private String estado;
    private LocalDateTime fechaCreacion;

    public ReservaSolicitud() {
    }

    public ReservaSolicitud(
            Long id,
            String nombre,
            String email,
            String telefono,
            String checkin,
            String checkout,
            String suite,
            String observaciones,
            Double precioPorNoche,
            Long noches,
            Double importeEstimado
    ) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.checkin = checkin;
        this.checkout = checkout;
        this.suite = suite;
        this.observaciones = observaciones;
        this.precioPorNoche = precioPorNoche;
        this.noches = noches;
        this.importeEstimado = importeEstimado;
        this.estado = "Pendiente";
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
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

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getSuite() {
        return suite;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public Long getNoches() {
        return noches;
    }

    public Double getImporteEstimado() {
        return importeEstimado;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaCreacionFormateada() {
        if (fechaCreacion == null) {
            return "Sin fecha registrada";
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return fechaCreacion.format(formato);
    }

    public void confirmar() {
        this.estado = "Confirmada";
    }

    public void cancelar() {
        this.estado = "Cancelada";
    }

    public void reabrir() {
        this.estado = "Pendiente";
    }

    public void actualizarDatos(
            String nombre,
            String email,
            String telefono,
            String checkin,
            String checkout,
            String suite,
            String observaciones,
            Double precioPorNoche,
            Long noches,
            Double importeEstimado
    ) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.checkin = checkin;
        this.checkout = checkout;
        this.suite = suite;
        this.observaciones = observaciones;
        this.precioPorNoche = precioPorNoche;
        this.noches = noches;
        this.importeEstimado = importeEstimado;
    }
}