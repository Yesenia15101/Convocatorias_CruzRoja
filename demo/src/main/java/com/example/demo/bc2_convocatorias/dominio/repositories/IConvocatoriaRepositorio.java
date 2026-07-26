package com.example.demo.bc2_convocatorias.dominio.repositories;

import java.util.List;
import java.util.Optional;
import com.example.demo.bc2_convocatorias.dominio.entities.Convocatoria;

public interface IConvocatoriaRepositorio {
    List<Convocatoria> listarActivas();
    Optional<Convocatoria> buscarPorId(Long id);
    void actualizar(Convocatoria convocatoria);
}