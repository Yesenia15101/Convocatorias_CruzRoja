package com.example.demo.bc1_usuarios.dominio.repositories;

import java.util.List;
import com.example.demo.bc1_usuarios.dominio.entities.Postulacion;

public interface IPostulacionRepositorio {
    Postulacion guardar(Postulacion postulacion);
    boolean existePostulacion(String dni, Long convocatoriaId);
    List<Postulacion> listarTodas();
    Long siguienteId();
}