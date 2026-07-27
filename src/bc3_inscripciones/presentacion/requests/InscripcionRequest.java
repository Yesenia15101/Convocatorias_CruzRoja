package bc3_inscripciones.presentacion.requests;

public record InscripcionRequest(
        String nombre,
        String dni,
        String correo,
        String telefono,
        String convocatoria,
        String codigoConvocatoria,
        String experiencia) {

    public bc3_inscripciones.aplicacion.dto.RegistrarInscripcionComando aComando() {
        return new bc3_inscripciones.aplicacion.dto.RegistrarInscripcionComando(
                nombre, dni, correo, telefono, convocatoria,
                codigoConvocatoria, experiencia);
    }
}
