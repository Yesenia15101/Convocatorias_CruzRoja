package bc1_usuarios.presentacion.requests;

public record PostulacionRequest(
        String dni,
        String nombreCompleto,
        String nivelFormacion,
        String especialidad,
        String habilidades,
        String disponibilidad,
        String codigoConvocatoria) {

    public bc1_usuarios.aplicacion.dto.RegistrarPostulacionComando aComando() {
        return new bc1_usuarios.aplicacion.dto.RegistrarPostulacionComando(
                dni, nombreCompleto, nivelFormacion, especialidad,
                habilidades, disponibilidad, codigoConvocatoria);
    }
}
