package bc3_inscripciones.presentacion.requests;

public record InscripcionRequest(
        String nombre,
        String dni,
        String correo,
        String telefono,
        String convocatoria,
        String codigoConvocatoria,
        String experiencia) {
}
