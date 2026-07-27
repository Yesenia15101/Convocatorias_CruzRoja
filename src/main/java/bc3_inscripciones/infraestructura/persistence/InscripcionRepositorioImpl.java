package bc3_inscripciones.infraestructura.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import bc3_inscripciones.dominio.entities.Inscripcion;
import bc3_inscripciones.dominio.enums.EstadoInscripcion;
import bc3_inscripciones.dominio.repositories.IInscripcionRepositorio;

@Repository
public class InscripcionRepositorioImpl implements IInscripcionRepositorio {

    private final Map<Long, Inscripcion> almacen = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    @Override
    public Long siguienteId() {
        return contadorId.incrementAndGet();
    }

    @Override
    public Inscripcion guardar(Inscripcion inscripcion) {
        almacen.put(inscripcion.getId(), inscripcion);
        return inscripcion;
    }

    @Override
    public Optional<Inscripcion> buscarPorId(Long id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public List<Inscripcion> listarPorDni(String dniVoluntario) {
        return almacen.values().stream()
                .filter(inscripcion -> inscripcion.getDniVoluntario().equals(dniVoluntario))
                .toList();
    }

    @Override
    public boolean existeInscripcionActiva(String dniVoluntario, Long convocatoriaId) {
        return almacen.values().stream()
                .anyMatch(inscripcion ->
                        inscripcion.getDniVoluntario().equals(dniVoluntario)
                        && esActivaEnConvocatoria(inscripcion, convocatoriaId));
    }

    @Override
    public long contarActivasPorConvocatoria(Long convocatoriaId) {
        return almacen.values().stream()
                .filter(inscripcion -> esActivaEnConvocatoria(inscripcion, convocatoriaId))
                .count();
    }

    /**
     * Una inscripción cuenta como "activa" en una convocatoria cuando
     * pertenece a esa convocatoria y no fue rechazada. Centralizado aquí
     * para que existeInscripcionActiva y contarActivasPorConvocatoria no
     * dupliquen la misma condición (principio DRY).
     */
    private boolean esActivaEnConvocatoria(Inscripcion inscripcion, Long convocatoriaId) {
        return inscripcion.getConvocatoriaId().equals(convocatoriaId)
                && inscripcion.getEstado() != EstadoInscripcion.RECHAZADA;
    }
}