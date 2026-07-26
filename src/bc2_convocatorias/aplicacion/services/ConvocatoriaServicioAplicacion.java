package bc2_convocatorias.aplicacion.services;

import bc2_convocatorias.aplicacion.dto.ConvocatoriaDTO;
import bc2_convocatorias.aplicacion.interfaces.IConvocatoriaServicio;
import bc2_convocatorias.dominio.entities.Convocatoria;
import bc2_convocatorias.infraestructura.persistence.ConvocatoriaRepositorioImpl;
import bc3_inscripciones.dominio.enums.EstadoInscripcion;
import bc3_inscripciones.dominio.repositories.IInscripcionRepositorio;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import bc2_convocatorias.dominio.valueobjects.Horario;

@Service
public final class ConvocatoriaServicioAplicacion implements IConvocatoriaServicio {
    private final ConvocatoriaRepositorioImpl repositorio;
    private final IInscripcionRepositorio inscripciones;

    public ConvocatoriaServicioAplicacion(
            ConvocatoriaRepositorioImpl repositorio,
            IInscripcionRepositorio inscripciones) {
        this.repositorio = repositorio;
        this.inscripciones = inscripciones;
    }

    public List<ConvocatoriaDTO> listarVerificacionMinimos() throws IOException {
        List<ConvocatoriaDTO> resultado = new java.util.ArrayList<>();
        for (Convocatoria convocatoria : repositorio.listar()) {
            resultado.add(convertir(convocatoria));
        }
        return resultado;
    }

    public ConvocatoriaDTO definirMinimo(String codigo, int minimo) throws IOException {
        Convocatoria convocatoria = buscar(codigo);
        convocatoria.definirMinimo(minimo);
        repositorio.guardarCambios();
        return convertir(convocatoria);
    }

    public ConvocatoriaDTO obtener(String codigo) throws IOException {
        return convertir(buscar(codigo));
    }

    public ConvocatoriaDTO definirHorario(
            String codigo, String horaInicio, String horaFin) throws IOException {
        Convocatoria convocatoria = buscar(codigo);
        convocatoria.definirHorario(Horario.desdeTexto(horaInicio, horaFin));
        repositorio.guardarCambios();
        return convertir(convocatoria);
    }

    public ConvocatoriaDTO confirmar(String codigo) throws IOException {
        Convocatoria convocatoria = buscar(codigo);
        convocatoria.confirmar((int) contarInscritos(codigo));
        repositorio.guardarCambios();
        return convertir(convocatoria);
    }

    private Convocatoria buscar(String codigo) {
        return repositorio.buscar(codigo).orElseThrow(
                () -> new NoSuchElementException("No existe la convocatoria " + codigo));
    }

    private long contarInscritos(String codigo) throws IOException {
        return inscripciones.listarTodas().stream()
                .filter(i -> i.getCodigoConvocatoria().equalsIgnoreCase(codigo))
                .filter(i -> i.getEstado() != EstadoInscripcion.CANCELADA)
                .filter(i -> i.getEstado() != EstadoInscripcion.RECHAZADA)
                .count();
    }

    private ConvocatoriaDTO convertir(Convocatoria convocatoria) throws IOException {
        long actuales = contarInscritos(convocatoria.getCodigo());
        int faltantes = convocatoria.faltantesPara((int) actuales);
        return new ConvocatoriaDTO(
                convocatoria.getCodigo(), convocatoria.getNombre(),
                convocatoria.getMinimoParticipantes(), actuales, faltantes == 0,
                faltantes, convocatoria.estadoPara((int) actuales).name(),
                faltantes == 0 ? "Mínimo de participantes alcanzado"
                        : "Aún faltan " + faltantes + " participante(s)",
                faltantes == 0 && !convocatoria.isConfirmada(),
                convocatoria.getFechaServicio() == null
                        ? "Fecha por definir" : convocatoria.getFechaServicio().toString(),
                convocatoria.getUbicacion().lugar(),
                convocatoria.tieneHorarioDefinido()
                        ? convocatoria.getHorario().getHoraInicioTexto() : null,
                convocatoria.tieneHorarioDefinido()
                        ? convocatoria.getHorario().getHoraFinTexto() : null,
                convocatoria.tieneHorarioDefinido()
                        ? convocatoria.getHorario().duracionLegible() : null,
                convocatoria.tieneHorarioDefinido(),
                convocatoria.tieneHorarioDefinido()
                        ? "Horario definido" : "El horario todavía no ha sido definido");
    }
}
