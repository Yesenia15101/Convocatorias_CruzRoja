package bc3_inscripciones.dominio.repositories;

import bc3_inscripciones.dominio.entities.Inscripcion;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** Contrato de persistencia del agregado Inscripción. */
public interface IInscripcionRepositorio {
    List<Inscripcion> listarTodas() throws IOException;

    Optional<Inscripcion> buscarPorId(long id) throws IOException;

    void actualizar(Inscripcion inscripcion) throws IOException;
}
