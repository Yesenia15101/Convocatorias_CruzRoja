package com.example.demo.bc1_usuarios.aplicacion;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.bc1_usuarios.dominio.entities.Postulacion;
import com.example.demo.bc1_usuarios.dominio.entities.Voluntario;
import com.example.demo.bc1_usuarios.dominio.repositories.IPostulacionRepositorio;
import com.example.demo.bc1_usuarios.dominio.valueobjects.Perfil;
import com.example.demo.bc2_convocatorias.dominio.repositories.IConvocatoriaRepositorio;

@Service
public class PostulacionServicioAplicacion {

    private final IPostulacionRepositorio postulacionRepositorio;
    private final IConvocatoriaRepositorio convocatoriaRepositorio;

    public PostulacionServicioAplicacion(IPostulacionRepositorio postulacionRepositorio,
                                          IConvocatoriaRepositorio convocatoriaRepositorio) {
        this.postulacionRepositorio = postulacionRepositorio;
        this.convocatoriaRepositorio = convocatoriaRepositorio;
    }

    /**
     * Caso de uso HF.2.1.1 - Registrar postulantes.
     * Verifica que la convocatoria exista y evita postulaciones
     * duplicadas del mismo voluntario a la misma convocatoria.
     */

    public Postulacion registrarPostulante(String dni, String nombreCompleto,
                                            String nivelFormacion, String especialidad,
                                            String habilidadesTexto, String disponibilidad,
                                            Long convocatoriaId) {

        convocatoriaRepositorio.buscarPorId(convocatoriaId)
                .orElseThrow(() -> new IllegalArgumentException("La convocatoria seleccionada no existe."));

        if (postulacionRepositorio.existePostulacion(dni, convocatoriaId)) {
            throw new IllegalStateException("Este voluntario ya está postulando a esta convocatoria.");
        }

        List<String> habilidades = Arrays.stream(habilidadesTexto.split(","))
                .map(String::trim)
                .filter(h -> !h.isEmpty())
                .toList();

        Perfil perfil = new Perfil(nivelFormacion, especialidad, habilidades);
        Voluntario voluntario = new Voluntario(dni, nombreCompleto, perfil, disponibilidad);
        Postulacion postulacion = new Postulacion(
                postulacionRepositorio.siguienteId(), voluntario, convocatoriaId);

        return postulacionRepositorio.guardar(postulacion);
    }

    public List<Postulacion> listarPostulantes() {
        return postulacionRepositorio.listarTodas();
    }
}