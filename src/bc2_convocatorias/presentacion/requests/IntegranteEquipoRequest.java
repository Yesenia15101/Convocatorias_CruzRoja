package bc2_convocatorias.presentacion.requests;

public record IntegranteEquipoRequest(
        String nombreVoluntario,
        String perfil,
        String habilidadEspecialidad,
        String estadoParticipacion,
        Long mentorId) {

    public bc2_convocatorias.aplicacion.dto.AgregarIntegranteComando aComando() {
        return new bc2_convocatorias.aplicacion.dto.AgregarIntegranteComando(
                nombreVoluntario, perfil, habilidadEspecialidad,
                estadoParticipacion, mentorId);
    }
}
