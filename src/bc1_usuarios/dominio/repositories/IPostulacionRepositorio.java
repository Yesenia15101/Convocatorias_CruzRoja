package bc1_usuarios.dominio.repositories;

import bc1_usuarios.dominio.entities.Postulacion;
import java.io.IOException;
import java.util.List;

public interface IPostulacionRepositorio {
    List<Postulacion> listarTodas() throws IOException;
    boolean existe(String dni, String codigoConvocatoria) throws IOException;
    long siguienteId() throws IOException;
    void guardar(Postulacion postulacion) throws IOException;
}
