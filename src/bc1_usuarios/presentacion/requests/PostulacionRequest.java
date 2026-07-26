package bc1_usuarios.presentacion.requests;

public record PostulacionRequest(
        String dni,
        String nombreCompleto,
        String nivelFormacion,
        String especialidad,
        String habilidades,
        String disponibilidad,
        String codigoConvocatoria) {
}
