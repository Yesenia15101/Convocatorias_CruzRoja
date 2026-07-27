package com.example.demo.bc1_usuarios.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.bc1_usuarios.aplicacion.PostulacionServicioAplicacion;
import com.example.demo.bc1_usuarios.dominio.entities.Postulacion;
import com.example.demo.bc1_usuarios.dominio.valueobjects.Perfil;
import com.example.demo.bc2_convocatorias.dominio.repositories.IConvocatoriaRepositorio;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/postulantes")
public class PostulacionController {

    private final PostulacionServicioAplicacion postulacionServicio;
    private final IConvocatoriaRepositorio convocatoriaRepositorio;

    public PostulacionController(PostulacionServicioAplicacion postulacionServicio,
                                  IConvocatoriaRepositorio convocatoriaRepositorio) {
        this.postulacionServicio = postulacionServicio;
        this.convocatoriaRepositorio = convocatoriaRepositorio;
    }

    @GetMapping("/nuevo")
    public String formularioRegistro(Model model) {
        model.addAttribute("convocatorias", convocatoriaRepositorio.listarActivas());
        return "registrar_postulante";
    }

    @PostMapping
    public String registrar(@RequestParam String dni,
                            @RequestParam String nombreCompleto,
                            @RequestParam String nivelFormacion,
                            @RequestParam String especialidad,
                            @RequestParam String habilidades,
                            @RequestParam String disponibilidad,
                            @RequestParam Long convocatoriaId,
                            RedirectAttributes redirectAttributes) {
        try {
            postulacionServicio.registrarPostulante(dni, nombreCompleto, nivelFormacion,
                    especialidad, habilidades, disponibilidad, convocatoriaId);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Postulante registrado con éxito!");
        } catch (IllegalStateException | IllegalArgumentException excepcion) {
            redirectAttributes.addFlashAttribute("mensajeError", excepcion.getMessage());
        }
        return "redirect:/postulantes";
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("postulantes", postulacionServicio.listarPostulantes());
        model.addAttribute("convocatorias", convocatoriaRepositorio.listarActivas());
        return "postulantes";
    }

    // ==========================================
    // REQUERIMIENTO EF.4.1: VER Y EDITAR PERFIL
    // ==========================================

    @GetMapping("/{id}/perfil")
    public String verPerfil(@PathVariable Long id, Model model) {
        Postulacion postulacion = buscarPostulacion(id);
        if (postulacion == null) {
            return "redirect:/postulantes";
        }

        model.addAttribute("postulacion", postulacion);
        model.addAttribute("voluntario", postulacion.getVoluntario());

        if (postulacion.getVoluntario().getPerfil() != null && postulacion.getVoluntario().getPerfil().getHabilidades() != null) {
            String habs = String.join(", ", postulacion.getVoluntario().getPerfil().getHabilidades());
            model.addAttribute("habilidadesString", habs);
        } else {
            model.addAttribute("habilidadesString", "");
        }

        return "perfil";
    }

    @PostMapping("/{id}/perfil")
    public String guardarPerfil(@PathVariable Long id,
                                @RequestParam(required = false, defaultValue = "") String correo,
                                @RequestParam(required = false, defaultValue = "") String telefono,
                                @RequestParam(required = false, defaultValue = "") String direccion,
                                @RequestParam String disponibilidad,
                                @RequestParam String nivelFormacion,
                                @RequestParam String especialidad,
                                @RequestParam String habilidades,
                                RedirectAttributes redirectAttributes) {

        Postulacion postulacion = buscarPostulacion(id);

        if (postulacion != null) {
            List<String> listaHabilidades = Arrays.stream(habilidades.split(","))
                    .map(String::trim)
                    .filter(h -> !h.isEmpty())
                    .collect(Collectors.toList());

            Perfil nuevoPerfil = new Perfil(nivelFormacion, especialidad, listaHabilidades);
            postulacion.getVoluntario().actualizarPerfil(correo, telefono, direccion, disponibilidad, nuevoPerfil);

            redirectAttributes.addFlashAttribute("mensajeExito", "Perfil actualizado correctamente.");
        }

        return "redirect:/postulantes/" + id + "/perfil";
    }

    private Postulacion buscarPostulacion(Long id) {
        return postulacionServicio.listarPostulantes().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}