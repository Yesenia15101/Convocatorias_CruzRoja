package com.example.demo.bc1_usuarios.dominio.enums;

public enum EstadoPostulacion {

    REGISTRADO("Registrado"),
    ACEPTADO("Aceptado"),
    RECHAZADO("Rechazado");

    private final String descripcion;

    EstadoPostulacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() { return descripcion; }
}