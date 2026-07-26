package bc3_inscripciones.dominio.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import bc3_inscripciones.dominio.enums.EstadoInscripcion;

/**
 * Entidad del dominio que representa la inscripción de un voluntario
 * a una convocatoria (BC3 - Inscripciones).
 *
 * Convenciones aplicadas (Java Code Conventions, Oracle):
 * - Clase en PascalCase, atributos y métodos en camelCase.
 * - Atributos privados con acceso por getters (encapsulamiento).
 * - Un import por línea; sin imports comodín.
 * - Identidad de entidad definida por su id (equals/hashCode).
 *
 * @author Natalie Marleny Lazo Paxi
 */
public class Inscripcion {

    private Long id;
    private String dniVoluntario;
    private Long convocatoriaId;
    private LocalDateTime fechaInscripcion;
    private EstadoInscripcion estado;

    public Inscripcion(Long id, String dniVoluntario, Long convocatoriaId) {
        this.id = Objects.requireNonNull(id, "El id de la inscripción no puede ser nulo");
        this.dniVoluntario = validarDni(dniVoluntario);
        this.convocatoriaId = Objects.requireNonNull(convocatoriaId, "La convocatoria es obligatoria");
        this.fechaInscripcion = LocalDateTime.now();
        this.estado = EstadoInscripcion.PENDIENTE;
    }

    private static String validarDni(String dniVoluntario) {
        if (dniVoluntario == null || dniVoluntario.isBlank()) {
            throw new IllegalArgumentException("El DNI del voluntario es obligatorio");
        }
        return dniVoluntario;
    }

    /**
     * Confirma la inscripción cuando la convocatoria alcanza
     * el mínimo de participantes (regla de negocio HF.2.3.1).
     */
    public void confirmar() {
        this.estado = EstadoInscripcion.CONFIRMADA;
    }

    /**
     * Rechaza la inscripción cuando el voluntario no cumple
     * los requisitos de la convocatoria.
     */
    public void rechazar() {
        this.estado = EstadoInscripcion.RECHAZADA;
    }

    /**
     * Actualiza el estado de la inscripción de forma genérica.
     * Requisito HF.3.2.2 — Actualizar el estado de inscripción.
     *
     * @param nuevoEstado estado destino, nunca nulo.
     */
    public void actualizarEstado(EstadoInscripcion nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "El nuevo estado es obligatorio");
    }

    public Long getId() {
        return id;
    }

    public String getDniVoluntario() {
        return dniVoluntario;
    }

    public Long getConvocatoriaId() {
        return convocatoriaId;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Inscripcion)) {
            return false;
        }
        Inscripcion otraInscripcion = (Inscripcion) otro;
        return Objects.equals(id, otraInscripcion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}