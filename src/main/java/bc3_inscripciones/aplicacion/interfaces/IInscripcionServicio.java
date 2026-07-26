package bc3_inscripciones.aplicacion.interfaces;

import java.util.List;

import bc3_inscripciones.aplicacion.dto.InscripcionDTO;
import bc3_inscripciones.presentacion.requests.InscripcionRequest;

/**
 * Puerto de entrada de la capa de aplicación (caso de uso).
 *
 * El controlador depende de esta interfaz, no de la implementación
 * concreta (InscripcionServicioAplicacion), aplicando el Principio
 * de Inversión de Dependencias (SOLID) y facilitando pruebas con dobles.
 *
 * @author Natalie Marleny Lazo Paxi
 */
public interface IInscripcionServicio {

    /**
     * Registra la inscripción de un voluntario a una convocatoria,
     * validando que no exista una inscripción previa activa.
     */
    InscripcionDTO registrarInscripcion(InscripcionRequest solicitud);

    /**
     * Obtiene el estado y detalle de una inscripción.
     */
    InscripcionDTO obtenerPorId(Long id);

    /**
     * Lista todas las inscripciones de un voluntario (útil para que el
     * frontend detecte inscripciones previas antes de mostrar el botón).
     */
    List<InscripcionDTO> listarPorVoluntario(String dniVoluntario);

    /**
     * Confirma una inscripción pendiente.
     */
    InscripcionDTO confirmarInscripcion(Long id);

    /**
     * Rechaza una inscripción pendiente.
     */
    InscripcionDTO rechazarInscripcion(Long id);

    /**
     * Cuenta cuántos voluntarios están inscritos actualmente (activos,
     * no rechazados) en una convocatoria. Usado por inscripcion.html
     * para comparar contra el mínimo de participantes requerido.
     */
    long contarActivasPorConvocatoria(Long convocatoriaId);
}