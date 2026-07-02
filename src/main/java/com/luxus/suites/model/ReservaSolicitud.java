package com.luxus.suites.model;

public class ReservaSolicitud {

    private Long id;
    private String nombre;
    private String email;
    private String checkin;
    private String checkout;
    private String suite;
    private Double precioPorNoche;
    private Long noches;
    private Double importeEstimado;
    private String estado;

    public ReservaSolicitud(
            Long id,
            String nombre,
            String email,
            String checkin,
            String checkout,
            String suite,
            Double precioPorNoche,
            Long noches,
            Double importeEstimado
    ) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.checkin = checkin;
        this.checkout = checkout;
        this.suite = suite;
        this.precioPorNoche = precioPorNoche;
        this.noches = noches;
        this.importeEstimado = importeEstimado;
        this.estado = "Pendiente";
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

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getSuite() {
        return suite;
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

    public void confirmar() {
        this.estado = "Confirmada";
    }

    public void cancelar() {
        this.estado = "Cancelada";
    }

    public void reabrir() {
        this.estado = "Pendiente";
    }
}