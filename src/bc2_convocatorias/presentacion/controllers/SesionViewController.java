package bc2_convocatorias.presentacion.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SesionViewController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "forward:/login.html"; // Carga el archivo HTML directamente
    }
}