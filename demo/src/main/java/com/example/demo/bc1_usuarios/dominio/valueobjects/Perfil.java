package com.example.demo.bc1_usuarios.dominio.valueobjects;

import java.util.List;

public class Perfil {

    private final String nivelFormacion;
    private final String especialidad;
    private final List<String> habilidades;

    public Perfil(String nivelFormacion, String especialidad, List<String> habilidades) {
        this.nivelFormacion = nivelFormacion;
        this.especialidad = especialidad;
        this.habilidades = List.copyOf(habilidades);
    }

    public String getNivelFormacion() { return nivelFormacion; }
    public String getEspecialidad() { return especialidad; }
    public List<String> getHabilidades() { return habilidades; }
}
