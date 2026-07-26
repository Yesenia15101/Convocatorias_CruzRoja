package bc3_inscripciones.dominio.valueobjects;

import java.util.Objects;

/**
 * Value Object que agrupa la información complementaria de una
 * inscripción (observaciones del voluntario y canal de inscripción).
 *
 * Al ser un Value Object es inmutable y se compara por valor, no por
 * identidad (equals/hashCode basados en sus atributos).
 *
 * Convenciones aplicadas (Java Code Conventions, Oracle):
 * - Clase final para reforzar la inmutabilidad.
 * - Sin setters; toda modificación crea una nueva instancia.
 *
 * @author Natalie Marleny Lazo Paxi
 */
public final class Detalles {

    private static final int LONGITUD_MAXIMA_OBSERVACIONES = 500;

    private final String observaciones;
    private final String canalInscripcion;

    public Detalles(String observaciones, String canalInscripcion) {
        this.observaciones = validarObservaciones(observaciones);
        this.canalInscripcion = canalInscripcion == null || canalInscripcion.isBlank()
                ? "WEB"
                : canalInscripcion.trim();
    }

    private static String validarObservaciones(String observaciones) {
        if (observaciones == null) {
            return "";
        }
        if (observaciones.length() > LONGITUD_MAXIMA_OBSERVACIONES) {
            throw new IllegalArgumentException(
                    "Las observaciones no pueden superar " + LONGITUD_MAXIMA_OBSERVACIONES + " caracteres");
        }
        return observaciones.trim();
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getCanalInscripcion() {
        return canalInscripcion;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Detalles)) {
            return false;
        }
        Detalles otrosDetalles = (Detalles) otro;
        return observaciones.equals(otrosDetalles.observaciones)
                && canalInscripcion.equals(otrosDetalles.canalInscripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observaciones, canalInscripcion);
    }
}
