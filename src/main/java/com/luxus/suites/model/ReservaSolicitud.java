package com.luxus.suites.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reservas_solicitudes")
public class ReservaSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Relacion real con Huesped.
     * Se agrega para normalizar el modelo y preparar el proyecto para deploy con PostgreSQL.
     */
    @ManyToOne
    @JoinColumn(name = "huesped_id")
    private Huesped huesped;

    /*
     * Relacion real con Suite.
     * Reemplaza el uso de suite solo como texto para nuevas reservas.
     */
    @ManyToOne
    @JoinColumn(name = "suite_id")
    private Suite suiteEntidad;

    /*
     * Campos de respaldo / snapshot.
     * Se mantienen para compatibilidad con reservas antiguas ya guardadas en H2
     * y para conservar los datos históricos de contacto al momento de la reserva.
     */
    private String nombre;
    private String email;
    private String telefono;

    @Column(name = "suite")
    private String suiteNombre;

    private String checkin;
    private String checkout;
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
            Huesped huesped,
            Suite suiteEntidad,
            String checkin,
            String checkout,
            String observaciones,
            Double precioPorNoche,
            Long noches,
            Double importeEstimado
    ) {
        this.id = id;
        this.huesped = huesped;
        this.suiteEntidad = suiteEntidad;

        this.nombre = huesped != null ? huesped.getNombre() : "";
        this.email = huesped != null ? huesped.getEmail() : "";
        this.telefono = huesped != null ? huesped.getTelefono() : "";
        this.suiteNombre = suiteEntidad != null ? suiteEntidad.getNombre() : "";

        this.checkin = checkin;
        this.checkout = checkout;
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

    public Huesped getHuesped() {
        return huesped;
    }

    public Suite getSuiteEntidad() {
        return suiteEntidad;
    }

    public String getNombre() {
        if (huesped != null && huesped.getNombre() != null && !huesped.getNombre().isBlank()) {
            return huesped.getNombre();
        }

        return nombre;
    }

    public String getEmail() {
        if (huesped != null && huesped.getEmail() != null && !huesped.getEmail().isBlank()) {
            return huesped.getEmail();
        }

        return email;
    }

    public String getTelefono() {
        if (huesped != null && huesped.getTelefono() != null && !huesped.getTelefono().isBlank()) {
            return huesped.getTelefono();
        }

        return telefono;
    }

    /*
     * Se conserva este getter como String para no romper templates existentes:
     * en Thymeleaf se sigue usando ${solicitud.suite}.
     */
    public String getSuite() {
        if (suiteEntidad != null && suiteEntidad.getNombre() != null && !suiteEntidad.getNombre().isBlank()) {
            return suiteEntidad.getNombre();
        }

        return suiteNombre;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
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
            Huesped huesped,
            Suite suiteEntidad,
            String checkin,
            String checkout,
            String observaciones,
            Double precioPorNoche,
            Long noches,
            Double importeEstimado
    ) {
        completarFechasFaltantes();

        this.huesped = huesped;
        this.suiteEntidad = suiteEntidad;

        this.nombre = huesped != null ? huesped.getNombre() : "";
        this.email = huesped != null ? huesped.getEmail() : "";
        this.telefono = huesped != null ? huesped.getTelefono() : "";
        this.suiteNombre = suiteEntidad != null ? suiteEntidad.getNombre() : "";

        this.checkin = checkin;
        this.checkout = checkout;
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