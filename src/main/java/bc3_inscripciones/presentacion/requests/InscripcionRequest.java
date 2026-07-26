package bc3_inscripciones.presentacion.requests;

/**
 * Request de entrada: representa el cuerpo JSON que envía
 * inscripcion.html al invocar POST /api/inscripciones.
 *
 * Solo transporta datos primitivos desde HTTP hacia el controlador;
 * la validación de negocio ocurre en dominio/aplicación, no aquí.
 *
 * @author Natalie Marleny Lazo Paxi
 */
public class InscripcionRequest {

    private String dniVoluntario;
    private Long convocatoriaId;
    private String observaciones;

    public InscripcionRequest() {
        // Constructor requerido por el framework para deserializar JSON.
    }

    public InscripcionRequest(String dniVoluntario, Long convocatoriaId, String observaciones) {
        this.dniVoluntario = dniVoluntario;
        this.convocatoriaId = convocatoriaId;
        this.observaciones = observaciones;
    }

    public String getDniVoluntario() {
        return dniVoluntario;
    }

    public void setDniVoluntario(String dniVoluntario) {
        this.dniVoluntario = dniVoluntario;
    }

    public Long getConvocatoriaId() {
        return convocatoriaId;
    }

    public void setConvocatoriaId(Long convocatoriaId) {
        this.convocatoriaId = convocatoriaId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
