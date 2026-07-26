package bc2_convocatorias.presentacion.requests;

public record IntegranteEquipoRequest(
        String nombreVoluntario,
        String perfil,
        String habilidadEspecialidad,
        String estadoParticipacion,
        Long mentorId) {
}
