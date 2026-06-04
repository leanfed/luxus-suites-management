package com.luxus.suites.model;

public class ReservaSolicitud {

    private String nombre;
    private String email;
    private String checkin;
    private String checkout;
    private String suite;

    public ReservaSolicitud(String nombre, String email, String checkin, String checkout, String suite) {
        this.nombre = nombre;
        this.email = email;
        this.checkin = checkin;
        this.checkout = checkout;
        this.suite = suite;
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
}