package com.luxus.suites.model;

public class ReservaSolicitud {

    private Long id;
    private String nombre;
    private String email;
    private String checkin;
    private String checkout;
    private String suite;
    private String estado;

    public ReservaSolicitud(Long id, String nombre, String email, String checkin, String checkout, String suite) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.checkin = checkin;
        this.checkout = checkout;
        this.suite = suite;
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

    public String getEstado() {
        return estado;
    }
}