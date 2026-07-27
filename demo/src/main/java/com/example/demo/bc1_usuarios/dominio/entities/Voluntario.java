package com.example.demo.bc1_usuarios.dominio.entities;

import com.example.demo.bc1_usuarios.dominio.valueobjects.Perfil;

public class Voluntario {

    private final String dni;
    private String nombreCompleto;
    private Perfil perfil;
    private String disponibilidad;
    private String correo;
    private String telefono;
    private String direccion;

    // Constructor original: Mantiene compatibilidad con el código previo
    public Voluntario(String dni, String nombreCompleto, Perfil perfil, String disponibilidad) {
        this(dni, nombreCompleto, perfil, disponibilidad, "", "", "");
    }

    // Constructor completo
    public Voluntario(String dni, String nombreCompleto, Perfil perfil, String disponibilidad, 
                      String correo, String telefono, String direccion) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.perfil = perfil;
        this.disponibilidad = disponibilidad;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Método de dominio (DDD) para actualizar los datos del perfil en memoria
    public void actualizarPerfil(String correo, String telefono, String direccion, 
                                 String disponibilidad, Perfil nuevoPerfil) {
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.disponibilidad = disponibilidad;
        if (nuevoPerfil != null) {
            this.perfil = nuevoPerfil;
        }
    }

    public String getDni() { return dni; }
    public String getNombreCompleto() { return nombreCompleto; }
    public Perfil getPerfil() { return perfil; }
    public String getDisponibilidad() { return disponibilidad; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
}