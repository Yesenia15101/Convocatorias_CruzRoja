package bc3_inscripciones.dominio.factories;

import bc3_inscripciones.dominio.entities.Inscripcion;
import bc3_inscripciones.dominio.repositories.IInscripcionRepositorio;

/**
 * Fábrica del agregado Inscripcion.
 *
 * Encapsula las reglas de creación (incluida la asignación de
 * identificador y la validación de duplicidad) para que ni el
 * servicio de aplicación ni el controlador conozcan estos detalles
 * (Single Responsibility Principle).
 *
 * @author Natalie Marleny Lazo Paxi
 */
public class InscripcionFabrica {

    private final IInscripcionRepositorio inscripcionRepositorio;

    public InscripcionFabrica(IInscripcionRepositorio inscripcionRepositorio) {
        this.inscripcionRepositorio = inscripcionRepositorio;
    }

    /**
     * Crea una nueva inscripción para un voluntario en una convocatoria,
     * garantizando que no exista una inscripción previa activa.
     *
     * @throws InscripcionDuplicadaException si el voluntario ya está
     *         inscrito en la convocatoria indicada.
     */
    public Inscripcion crear(String dniVoluntario, Long convocatoriaId) {
        if (inscripcionRepositorio.existeInscripcionActiva(dniVoluntario, convocatoriaId)) {
            throw new InscripcionDuplicadaException(dniVoluntario, convocatoriaId);
        }
        Long nuevoId = inscripcionRepositorio.siguienteId();
        return new Inscripcion(nuevoId, dniVoluntario, convocatoriaId);
    }
}
