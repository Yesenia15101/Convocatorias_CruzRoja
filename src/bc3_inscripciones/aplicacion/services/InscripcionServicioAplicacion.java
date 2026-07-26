package bc3_inscripciones.aplicacion.services;

import bc3_inscripciones.aplicacion.dto.InscripcionDTO;
import bc3_inscripciones.aplicacion.interfaces.IInscripcionServicio;
import bc3_inscripciones.dominio.entities.Inscripcion;
import bc3_inscripciones.dominio.enums.EstadoInscripcion;
import bc3_inscripciones.dominio.repositories.IInscripcionRepositorio;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import bc3_inscripciones.presentacion.requests.InscripcionRequest;
import java.time.LocalDate;

/**
 * Servicio de aplicación.
 *
 * El método actualizarEstado aplica Cookbook: valida, transforma, busca,
 * modifica, persiste y devuelve el resultado en pasos ordenados.
 */
@Service
public final class InscripcionServicioAplicacion implements IInscripcionServicio {
    private final IInscripcionRepositorio repositorio;

    public InscripcionServicioAplicacion(IInscripcionRepositorio repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("El repositorio es obligatorio");
        }
        this.repositorio = repositorio;
    }

    @Override
    public List<InscripcionDTO> listarInscripciones() throws IOException {
        return repositorio.listarTodas()
                .stream()
                .map(InscripcionDTO::desde)
                .collect(Collectors.toList());
    }

    @Override
    public InscripcionDTO actualizarEstado(long id, String nuevoEstado) throws IOException {
        validarIdentificador(id);
        EstadoInscripcion estado = EstadoInscripcion.desdeTexto(nuevoEstado);
        Inscripcion inscripcion = obtenerInscripcion(id);
        inscripcion.cambiarEstado(estado);
        repositorio.actualizar(inscripcion);
        return InscripcionDTO.desde(inscripcion);
    }

    @Override
    public InscripcionDTO registrar(InscripcionRequest request) throws IOException {
        if (request == null) {
            throw new IllegalArgumentException("Los datos son obligatorios");
        }
        List<Inscripcion> actuales = repositorio.listarTodas();
        boolean duplicada = actuales.stream().anyMatch(i ->
                i.getDni().equals(request.dni())
                        && i.getCodigoConvocatoria().equalsIgnoreCase(request.codigoConvocatoria()));
        if (duplicada) {
            throw new IllegalArgumentException("El voluntario ya está inscrito en esta convocatoria");
        }
        long id = actuales.stream().mapToLong(Inscripcion::getId).max().orElse(0) + 1;
        Inscripcion nueva = new Inscripcion(
                id, request.nombre(), request.dni(), request.correo(), request.telefono(),
                request.convocatoria(), request.codigoConvocatoria(), LocalDate.now(),
                EstadoInscripcion.PENDIENTE, request.experiencia());
        repositorio.guardar(nueva);
        return InscripcionDTO.desde(nueva);
    }

    @Override
    public void eliminar(long id) throws IOException {
        validarIdentificador(id);
        obtenerInscripcion(id);
        repositorio.eliminar(id);
    }

    private Inscripcion obtenerInscripcion(long id) throws IOException {
        return repositorio.buscarPorId(id)
                .orElseThrow(
                        () -> new NoSuchElementException(
                                "No existe una inscripción con id " + id
                        )
                );
    }

    private static void validarIdentificador(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser mayor que cero");
        }
    }
}
