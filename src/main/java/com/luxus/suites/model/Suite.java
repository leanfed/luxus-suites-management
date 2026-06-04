package com.luxus.suites.model;

public class Suite {

    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Double precioPorNoche;
    private Integer capacidad;
    private Boolean disponible;

    public Suite(Long id, String nombre, String descripcion, String categoria, Double precioPorNoche, Integer capacidad, Boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precioPorNoche = precioPorNoche;
        this.capacidad = capacidad;
        this.disponible = disponible;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public Double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public Boolean getDisponible() {
        return disponible;
    }
}