package bc3_inscripciones.dominio.factories;

import java.util.Map;

import bc3_inscripciones.dominio.entities.Inscripcion;
import bc3_inscripciones.dominio.repositories.IInscripcionRepositorio;


public class InscripcionFabrica {

    private static final Map<Long, Integer> CAPACIDAD_MAXIMA_POR_CONVOCATORIA = Map.of(
            1L, 40,
            2L, 15,
            3L, 5
    );

    private final IInscripcionRepositorio inscripcionRepositorio;

    public InscripcionFabrica(IInscripcionRepositorio inscripcionRepositorio) {
        this.inscripcionRepositorio = inscripcionRepositorio;
    }

    /**
     * Crea una nueva inscripción para un voluntario en una convocatoria,
     * garantizando que no exista una inscripción previa activa y que
     * la convocatoria aún tenga vacantes disponibles.
     *
     * @throws InscripcionDuplicadaException si el voluntario ya está
     *         inscrito en la convocatoria indicada.
     * @throws VacantesAgotadasException si la convocatoria ya alcanzó
     *         su número máximo de vacantes.
     */
    public Inscripcion crear(String dniVoluntario, Long convocatoriaId) {
        validarQueNoEsteDuplicada(dniVoluntario, convocatoriaId);
        validarQueHayaVacantesDisponibles(convocatoriaId);

        Long nuevoId = inscripcionRepositorio.siguienteId();
        return new Inscripcion(nuevoId, dniVoluntario, convocatoriaId);
    }

    private void validarQueNoEsteDuplicada(String dniVoluntario, Long convocatoriaId) {
        if (inscripcionRepositorio.existeInscripcionActiva(dniVoluntario, convocatoriaId)) {
            throw new InscripcionDuplicadaException(dniVoluntario, convocatoriaId);
        }
    }

    private void validarQueHayaVacantesDisponibles(Long convocatoriaId) {
        Integer capacidadMaxima = CAPACIDAD_MAXIMA_POR_CONVOCATORIA.get(convocatoriaId);
        if (capacidadMaxima == null) {
            return; // convocatoria sin límite conocido: no se restringe
        }

        long inscritosActuales = inscripcionRepositorio.contarActivasPorConvocatoria(convocatoriaId);
        if (inscritosActuales >= capacidadMaxima) {
            throw new VacantesAgotadasException(convocatoriaId, capacidadMaxima);
        }
    }
}