package bc1_usuarios.presentacion.requests;

import java.util.List;

public record PerfilRequest(
        String nombre,
        String nivelFormacion,
        String especialidad,
        List<String> habilidades) {

    public bc1_usuarios.aplicacion.dto.ActualizarPerfilComando aComando() {
        return new bc1_usuarios.aplicacion.dto.ActualizarPerfilComando(
                nombre, nivelFormacion, especialidad, habilidades);
    }
}
