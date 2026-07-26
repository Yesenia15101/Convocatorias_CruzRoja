package bc3_inscripciones.dominio.repositories;

import java.util.List;
import java.util.Optional;

import bc3_inscripciones.dominio.entities.Inscripcion;

/**
 * Puerto (interfaz) del repositorio de inscripciones.
 *
 * El dominio define el contrato; la capa de infraestructura
 * (InscripcionRepositorioImpl) provee la implementación concreta.
 * Esto aplica el Principio de Inversión de Dependencias (DIP - SOLID):
 * las capas superiores (aplicación/dominio) dependen de esta abstracción,
 * nunca de un detalle de persistencia concreto.
 *
 * @author Natalie Marleny Lazo Paxi
 */
public interface IInscripcionRepositorio {

    /**
     * Genera el siguiente identificador disponible para una nueva inscripción.
     */
    Long siguienteId();

    /**
     * Persiste una inscripción nueva o actualiza una existente.
     */
    Inscripcion guardar(Inscripcion inscripcion);

    /**
     * Busca una inscripción por su identificador.
     */
    Optional<Inscripcion> buscarPorId(Long id);

    /**
     * Lista todas las inscripciones asociadas a un voluntario.
     */
    List<Inscripcion> listarPorDni(String dniVoluntario);

    /**
     * Indica si el voluntario ya cuenta con una inscripción registrada
     * para la convocatoria indicada.
     *
     * Soporta la regla de negocio: evitar que el voluntario
     * se inscriba dos veces en la misma convocatoria.
     */
    boolean existeInscripcionActiva(String dniVoluntario, Long convocatoriaId);
}
