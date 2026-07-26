package bc3_inscripciones.aplicacion.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.stereotype.Service;

import bc3_inscripciones.aplicacion.dto.InscripcionDTO;
import bc3_inscripciones.aplicacion.interfaces.IInscripcionServicio;
import bc3_inscripciones.dominio.entities.Inscripcion;
import bc3_inscripciones.dominio.factories.InscripcionFabrica;
import bc3_inscripciones.dominio.repositories.IInscripcionRepositorio;
import bc3_inscripciones.presentacion.requests.InscripcionRequest;


@Service
public class InscripcionServicioAplicacion implements IInscripcionServicio {

    private final IInscripcionRepositorio inscripcionRepositorio;
    private final InscripcionFabrica inscripcionFabrica;

    public InscripcionServicioAplicacion(IInscripcionRepositorio inscripcionRepositorio) {
        this.inscripcionRepositorio = inscripcionRepositorio;
        this.inscripcionFabrica = new InscripcionFabrica(inscripcionRepositorio);
    }

    @Override
    public InscripcionDTO registrarInscripcion(InscripcionRequest solicitud) {
        Objects.requireNonNull(solicitud, "La solicitud de inscripción es obligatoria");

        Inscripcion inscripcion = inscripcionFabrica.crear(
                solicitud.getDniVoluntario(),
                solicitud.getConvocatoriaId());

        Inscripcion inscripcionGuardada = inscripcionRepositorio.guardar(inscripcion);
        return InscripcionDTO.desde(inscripcionGuardada);
    }

    @Override
    public InscripcionDTO obtenerPorId(Long id) {
        return InscripcionDTO.desde(buscarInscripcionOLanzarError(id));
    }

    @Override
    public List<InscripcionDTO> listarPorVoluntario(String dniVoluntario) {
        return inscripcionRepositorio.listarPorDni(dniVoluntario).stream()
                .map(InscripcionDTO::desde)
                .toList();
    }

    @Override
    public InscripcionDTO confirmarInscripcion(Long id) {
        Inscripcion inscripcion = buscarInscripcionOLanzarError(id);
        inscripcion.confirmar();
        return InscripcionDTO.desde(inscripcionRepositorio.guardar(inscripcion));
    }

    @Override
    public InscripcionDTO rechazarInscripcion(Long id) {
        Inscripcion inscripcion = buscarInscripcionOLanzarError(id);
        inscripcion.rechazar();
        return InscripcionDTO.desde(inscripcionRepositorio.guardar(inscripcion));
    }

    @Override
    public long contarActivasPorConvocatoria(Long convocatoriaId) {
        return inscripcionRepositorio.contarActivasPorConvocatoria(convocatoriaId);
    }

    private Inscripcion buscarInscripcionOLanzarError(Long id) {
        return inscripcionRepositorio.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("No existe la inscripción con id " + id));
    }
}