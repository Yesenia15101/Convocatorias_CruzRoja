package bc1_usuarios.aplicacion.interfaces;

import bc1_usuarios.aplicacion.dto.PostulacionDTO;
import bc1_usuarios.aplicacion.dto.RegistrarPostulacionComando;
import java.io.IOException;
import java.util.List;

public interface IPostulacionServicio {
    List<PostulacionDTO> listar() throws IOException;
    PostulacionDTO registrar(RegistrarPostulacionComando comando) throws IOException;
}
