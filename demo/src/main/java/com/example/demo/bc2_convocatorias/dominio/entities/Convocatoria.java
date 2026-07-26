package com.example.demo.bc2_convocatorias.dominio.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Convocatoria {

    private final Long id;
    private final String nombre;
    private final String ubicacion;
    private final LocalDate fecha;
    private final LocalTime horaInicio;
    private final LocalTime horaFin;
    private final int cupoMaximo;
    private int registrados;

    public Convocatoria(Long id, String nombre, String ubicacion, LocalDate fecha,
                         LocalTime horaInicio, LocalTime horaFin, int cupoMaximo) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cupoMaximo = cupoMaximo;
        this.registrados = 0;
    }

    public boolean tieneCupoDisponible() {
        return registrados < cupoMaximo;
    }

    public void incrementarRegistrados() {
        if (!tieneCupoDisponible()) {
            throw new IllegalStateException("La convocatoria ya no tiene cupos disponibles.");
        }
        this.registrados++;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public int getCupoMaximo() { return cupoMaximo; }
    public int getRegistrados() { return registrados; }
}