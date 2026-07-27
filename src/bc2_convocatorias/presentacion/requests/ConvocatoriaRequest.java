package bc2_convocatorias.presentacion.requests;

import java.util.List;

public record ConvocatoriaRequest(
        String codigo,
        String titulo,
        String fecha,
        String horaInicio,
        String horaFin,
        String ubicacion,
        List<String> requisitos,
        int minimoParticipantes) {

    public bc2_convocatorias.aplicacion.dto.GuardarConvocatoriaComando aComando() {
        return new bc2_convocatorias.aplicacion.dto.GuardarConvocatoriaComando(
                codigo, titulo, fecha, horaInicio, horaFin, ubicacion,
                requisitos, minimoParticipantes);
    }
}
