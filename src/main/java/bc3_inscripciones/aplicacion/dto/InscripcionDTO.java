package bc3_inscripciones.aplicacion.dto;

import java.time.format.DateTimeFormatter;

import bc3_inscripciones.dominio.entities.Inscripcion;

/**
 * Data Transfer Object de salida: expone únicamente los datos que la
 * capa de presentación (InscripcionController -> inscripcion.html)
 * necesita mostrar. Es una estructura de datos plana, sin lógica de
 * negocio, tal como recomienda el patrón DTO.
 *
 * @author Natalie Marleny Lazo Paxi
 */
public class InscripcionDTO {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Long id;
    private final String dniVoluntario;
    private final Long convocatoriaId;
    private final String fechaInscripcion;
    private final String estado;
    private final String estadoDescripcion;

    private InscripcionDTO(Long id, String dniVoluntario, Long convocatoriaId,
                            String fechaInscripcion, String estado, String estadoDescripcion) {
        this.id = id;
        this.dniVoluntario = dniVoluntario;
        this.convocatoriaId = convocatoriaId;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
        this.estadoDescripcion = estadoDescripcion;
    }

    /**
     * Método de mapeo Inscripcion (dominio) -> InscripcionDTO (aplicación).
     */
    public static InscripcionDTO desde(Inscripcion inscripcion) {
        return new InscripcionDTO(
                inscripcion.getId(),
                inscripcion.getDniVoluntario(),
                inscripcion.getConvocatoriaId(),
                inscripcion.getFechaInscripcion().format(FORMATO_FECHA),
                inscripcion.getEstado().name(),
                inscripcion.getEstado().getDescripcion());
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

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public String getEstadoDescripcion() {
        return estadoDescripcion;
    }
}
