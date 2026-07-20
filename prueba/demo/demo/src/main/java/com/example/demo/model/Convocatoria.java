package com.example.demo.model;

public class Convocatoria {

    private Integer id;
    private String nombre;
    private String lugar;
    private String fecha;
    private String horario;
    private Integer registrados;

    private static final int MAX_CUPOS = 80;

    public Convocatoria(Integer id,
                        String nombre,
                        String lugar,
                        String fecha,
                        String horario,
                        Integer registrados) {

        this.id = id;
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.horario = horario;
        this.registrados = registrados;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHorario() {
        return horario;
    }

    public Integer getRegistrados() {
        return registrados;
    }

    public boolean tieneCupos() {
        return registrados < MAX_CUPOS;
    }

    public String getEstado() {

        if (tieneCupos())
            return "Disponible";

        return "Cupos completos";
    }

}