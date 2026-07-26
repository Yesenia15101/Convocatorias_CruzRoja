package com.example.demo.bc1_usuarios.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.bc1_usuarios.aplicacion.PostulacionServicioAplicacion;
import com.example.demo.bc2_convocatorias.dominio.repositories.IConvocatoriaRepositorio;

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
}