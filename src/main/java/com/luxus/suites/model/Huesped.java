package com.luxus.suites.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "huespedes")
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String telefono;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaUltimaActualizacion;

    public Huesped() {
    }

    public Huesped(
            Long id,
            String nombre,
            String email,
            String telefono
    ) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    @PrePersist
    public void antesDeCrear() {
        LocalDateTime ahora = LocalDateTime.now();

        if (this.fechaCreacion == null) {
            this.fechaCreacion = ahora;
        }

        this.fechaUltimaActualizacion = ahora;
    }

    @PreUpdate
    public void antesDeActualizar() {
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public String getFechaCreacionFormateada() {
        if (fechaCreacion == null) {
            return "Sin fecha registrada";
        }

        return formatearFecha(fechaCreacion);
    }

    public String getFechaUltimaActualizacionFormateada() {
        if (fechaUltimaActualizacion == null) {
            return "Sin fecha registrada";
        }

        return formatearFecha(fechaUltimaActualizacion);
    }

    public void actualizarDatos(
            String nombre,
            String email,
            String telefono
    ) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    private String formatearFecha(LocalDateTime fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formato);
    }
}