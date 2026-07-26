package bc1_usuarios.aplicacion.services;

import bc1_usuarios.aplicacion.dto.PostulacionDTO;
import bc1_usuarios.aplicacion.interfaces.IPostulacionServicio;
import bc1_usuarios.dominio.entities.Postulacion;
import bc1_usuarios.dominio.entities.Voluntario;
import bc1_usuarios.dominio.enums.TipoVoluntario;
import bc1_usuarios.dominio.repositories.IPostulacionRepositorio;
import bc1_usuarios.dominio.valueobjects.Perfil;
import bc1_usuarios.presentacion.requests.PostulacionRequest;
import bc2_convocatorias.infraestructura.persistence.ConvocatoriaRepositorioImpl;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public final class PostulacionServicioAplicacion implements IPostulacionServicio {
    private final IPostulacionRepositorio repositorio;
    private final ConvocatoriaRepositorioImpl convocatorias;

    public PostulacionServicioAplicacion(
            IPostulacionRepositorio repositorio,
            ConvocatoriaRepositorioImpl convocatorias) {
        this.repositorio = repositorio;
        this.convocatorias = convocatorias;
    }

    @Override
    public List<PostulacionDTO> listar() throws IOException {
        return repositorio.listarTodas().stream().map(PostulacionDTO::desde).toList();
    }

    @Override
    public PostulacionDTO registrar(PostulacionRequest request) throws IOException {
        validarRequest(request);
        convocatorias.buscar(request.codigoConvocatoria()).orElseThrow(() ->
                new IllegalArgumentException("La convocatoria seleccionada no existe"));
        if (repositorio.existe(request.dni().trim(), request.codigoConvocatoria().trim())) {
            throw new IllegalStateException(
                    "Este voluntario ya está postulando a esta convocatoria");
        }
        List<String> habilidades = Arrays.stream(request.habilidades().split(","))
                .map(String::trim).filter(h -> !h.isBlank()).toList();
        Perfil perfil = new Perfil(
                TipoVoluntario.desdeTexto(request.nivelFormacion()),
                request.especialidad(), habilidades);
        Voluntario voluntario = new Voluntario(
                request.dni(), request.nombreCompleto(), perfil, request.disponibilidad());
        Postulacion postulacion = new Postulacion(
                repositorio.siguienteId(), voluntario, request.codigoConvocatoria());
        repositorio.guardar(postulacion);
        return PostulacionDTO.desde(postulacion);
    }

    private static void validarRequest(PostulacionRequest request) {
        if (request == null) throw new IllegalArgumentException("La solicitud es obligatoria");
        if (request.habilidades() == null) {
            throw new IllegalArgumentException("Las habilidades son obligatorias");
        }
    }
}
