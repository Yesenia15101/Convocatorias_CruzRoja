package bc3_inscripciones.dominio.factories;

/**
 * Excepción de negocio (no verificada / unchecked) lanzada cuando un
 * voluntario intenta inscribirse dos veces en la misma convocatoria.
 *
 * Se prefiere una excepción no verificada frente a un código de error,
 * y se aporta contexto explícito (DNI y convocatoria) en el mensaje,
 * tal como recomienda Clean Code para el manejo de errores.
 *
 * @author Natalie Marleny Lazo Paxi
 */
public class InscripcionDuplicadaException extends RuntimeException {

    public InscripcionDuplicadaException(String dniVoluntario, Long convocatoriaId) {
        super(String.format(
                "El voluntario con DNI %s ya se encuentra inscrito en la convocatoria %d",
                dniVoluntario, convocatoriaId));
    }
}
