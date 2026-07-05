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
    private LocalDateTime fechaUltimaActualizacion;

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
        this.fechaUltimaActualizacion = LocalDateTime.now();
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

    public LocalDateTime getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public String getFechaCreacionFormateada() {
        if (fechaCreacion != null) {
            return formatearFecha(fechaCreacion);
        }

        if (fechaUltimaActualizacion != null) {
            return formatearFecha(fechaUltimaActualizacion);
        }

        return "Sin fecha registrada";
    }

    public String getFechaUltimaActualizacionFormateada() {
        if (fechaUltimaActualizacion != null) {
            return formatearFecha(fechaUltimaActualizacion);
        }

        if (fechaCreacion != null) {
            return formatearFecha(fechaCreacion);
        }

        return "Sin fecha registrada";
    }

    public void confirmar() {
        completarFechasFaltantes();
        this.estado = "Confirmada";
        this.fechaUltimaActualizacion = LocalDateTime.now();
    }

    public void cancelar() {
        completarFechasFaltantes();
        this.estado = "Cancelada";
        this.fechaUltimaActualizacion = LocalDateTime.now();
    }

    public void reabrir() {
        completarFechasFaltantes();
        this.estado = "Pendiente";
        this.fechaUltimaActualizacion = LocalDateTime.now();
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
        completarFechasFaltantes();

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
        this.fechaUltimaActualizacion = LocalDateTime.now();
    }

    private void completarFechasFaltantes() {
        if (this.fechaCreacion == null && this.fechaUltimaActualizacion != null) {
            this.fechaCreacion = this.fechaUltimaActualizacion;
        }

        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }

        if (this.fechaUltimaActualizacion == null) {
            this.fechaUltimaActualizacion = this.fechaCreacion;
        }
    }

    private String formatearFecha(LocalDateTime fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return fecha.format(formato);
    }
}