package bc3_inscripciones.infraestructura.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import bc3_inscripciones.dominio.entities.Inscripcion;
import bc3_inscripciones.dominio.enums.EstadoInscripcion;
import bc3_inscripciones.dominio.repositories.IInscripcionRepositorio;

/**
 * Implementación de infraestructura del repositorio de inscripciones.
 *
 * Por ahora persiste en memoria (Map) para no acoplar el dominio a un
 * motor de base de datos concreto todavía; cuando se conecte JPA/JDBC
 * (ver resources/database/schema.sql) solo se reemplaza esta clase,
 * sin tocar dominio ni aplicación (Open/Closed Principle).
 *
 * @author Natalie Marleny Lazo Paxi
 */
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
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeInscripcionActiva(String dniVoluntario, Long convocatoriaId) {
        return almacen.values().stream()
                .anyMatch(inscripcion ->
                        inscripcion.getDniVoluntario().equals(dniVoluntario)
                        && inscripcion.getConvocatoriaId().equals(convocatoriaId)
                        && inscripcion.getEstado() != EstadoInscripcion.RECHAZADA);
    }

    @Override
    public long contarActivasPorConvocatoria(Long convocatoriaId) {
        return almacen.values().stream()
                .filter(inscripcion ->
                        inscripcion.getConvocatoriaId().equals(convocatoriaId)
                        && inscripcion.getEstado() != EstadoInscripcion.RECHAZADA)
                .count();
    }
}