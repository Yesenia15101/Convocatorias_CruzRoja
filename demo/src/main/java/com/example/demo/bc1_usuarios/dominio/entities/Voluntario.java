package com.example.demo.bc1_usuarios.dominio.entities;

import com.example.demo.bc1_usuarios.dominio.valueobjects.Perfil;

public class Voluntario {

    private final String dni;
    private final String nombreCompleto;
    private final Perfil perfil;
    private final String disponibilidad;

    public Voluntario(String dni, String nombreCompleto, Perfil perfil, String disponibilidad) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.perfil = perfil;
        this.disponibilidad = disponibilidad;
    }

    public String getDni() { return dni; }
    public String getNombreCompleto() { return nombreCompleto; }
    public Perfil getPerfil() { return perfil; }
    public String getDisponibilidad() { return disponibilidad; }
}   