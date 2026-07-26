package com.example.demo.bc1_usuarios.infraestructura;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import com.example.demo.bc1_usuarios.dominio.entities.Postulacion;
import com.example.demo.bc1_usuarios.dominio.repositories.IPostulacionRepositorio;

@Repository
public class PostulacionRepositorioImpl implements IPostulacionRepositorio {

    private final List<Postulacion> postulaciones = new CopyOnWriteArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    @Override
    public Postulacion guardar(Postulacion postulacion) {
        postulaciones.add(postulacion);
        return postulacion;
    }

    @Override
    public boolean existePostulacion(String dni, Long convocatoriaId) {
        return postulaciones.stream()
                .anyMatch(p -> p.getVoluntario().getDni().equals(dni)
                        && p.getConvocatoriaId().equals(convocatoriaId));
    }

    @Override
    public List<Postulacion> listarTodas() {
        return List.copyOf(postulaciones);
    }

    @Override
    public Long siguienteId() {
        return contadorId.getAndIncrement();
    }
}