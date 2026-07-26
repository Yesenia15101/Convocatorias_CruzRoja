package com.example.demo.bc1_usuarios.dominio.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import com.example.demo.bc1_usuarios.dominio.enums.EstadoPostulacion;

public class Postulacion {

    private final Long id;
    private final Voluntario voluntario;
    private final Long convocatoriaId;
    private final LocalDateTime fechaRegistro;
    private EstadoPostulacion estado;

    public Postulacion(Long id, Voluntario voluntario, Long convocatoriaId) {
        this.id = id;
        this.voluntario = voluntario;
        this.convocatoriaId = convocatoriaId;
        this.fechaRegistro = LocalDateTime.now(ZoneId.systemDefault());
        this.estado = EstadoPostulacion.REGISTRADO;
    }

    public void aceptar() { this.estado = EstadoPostulacion.ACEPTADO; }
    public void rechazar() { this.estado = EstadoPostulacion.RECHAZADO; }

    public Long getId() { return id; }
    public Voluntario getVoluntario() { return voluntario; }
    public Long getConvocatoriaId() { return convocatoriaId; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public EstadoPostulacion getEstado() { return estado; }
}