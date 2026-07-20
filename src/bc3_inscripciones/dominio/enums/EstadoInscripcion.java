package bc3_inscripciones.dominio.enums;

/**
 * Estados posibles de una inscripción de voluntario a una convocatoria.
 * Requisito HF.3.2.1 — Ver estado de inscripción.
 *
 * Convenciones aplicadas (Java Code Conventions, Oracle):
 * - Nombre de enum en PascalCase; constantes en UPPER_SNAKE_CASE.
 * - Paquete en minúsculas.
 * - Javadoc descriptivo por clase y método público.
 *
 * @author Natalie Marleny Lazo Paxi
 */
public enum EstadoInscripcion {

    PENDIENTE("Pendiente"),
    CONFIRMADA("Confirmada"),
    RECHAZADA("Rechazada");

    private final String descripcion;

    EstadoInscripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return descripción legible del estado para mostrar en la interfaz.
     */
    public String getDescripcion() {
        return descripcion;
    }
}