package com.example.demo.bc2_convocatorias.infraestructura;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.example.demo.bc2_convocatorias.dominio.entities.Convocatoria;
import com.example.demo.bc2_convocatorias.dominio.repositories.IConvocatoriaRepositorio;

@Repository
public class ConvocatoriaRepositorioImpl implements IConvocatoriaRepositorio {

    private final Map<Long, Convocatoria> convocatorias = new LinkedHashMap<>();

    public ConvocatoriaRepositorioImpl() {
        convocatorias.put(1L, new Convocatoria(1L, "Campaña de Donación de Sangre",
                "Arequipa - Cercado", LocalDate.of(2026, Month.JULY, 25),
                LocalTime.of(8, 0), LocalTime.of(14, 0), 50));
        convocatorias.put(2L, new Convocatoria(2L, "Apoyo en Emergencias",
                "Camaná", LocalDate.of(2026, Month.JULY, 28),
                LocalTime.of(7, 30), LocalTime.of(17, 0), 65));
        convocatorias.put(3L, new Convocatoria(3L, "Capacitación en Primeros Auxilios",
                "Yanahuara", LocalDate.of(2026, Month.JULY, 30),
                LocalTime.of(9, 0), LocalTime.of(13, 0), 80));
        convocatorias.put(4L, new Convocatoria(4L, "Campaña Médica Comunitaria",
                "Cerro Colorado", LocalDate.of(2026, Month.AUGUST, 5),
                LocalTime.of(8, 30), LocalTime.of(15, 0), 40));
    }

    @Override
    public List<Convocatoria> listarActivas() {
        return new ArrayList<>(convocatorias.values());
    }

    @Override
    public Optional<Convocatoria> buscarPorId(Long id) {
        return Optional.ofNullable(convocatorias.get(id));
    }

    @Override
    public void actualizar(Convocatoria convocatoria) {
        convocatorias.put(convocatoria.getId(), convocatoria);
    }
}