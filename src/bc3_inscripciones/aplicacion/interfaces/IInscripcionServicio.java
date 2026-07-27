package bc3_inscripciones.aplicacion.interfaces;

import bc3_inscripciones.aplicacion.dto.InscripcionDTO;

import java.io.IOException;
import java.util.List;
import bc3_inscripciones.aplicacion.dto.RegistrarInscripcionComando;

/** Casos de uso disponibles para gestionar inscripciones. */
public interface IInscripcionServicio {
    List<InscripcionDTO> listarInscripciones() throws IOException;

    InscripcionDTO actualizarEstado(long id, String nuevoEstado) throws IOException;

    InscripcionDTO registrar(RegistrarInscripcionComando comando) throws IOException;

    void eliminar(long id) throws IOException;
}
