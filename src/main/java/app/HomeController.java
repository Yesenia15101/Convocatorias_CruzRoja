package app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller de infraestructura web: no pertenece a ningún Bounded Context
 * de negocio (bc1_usuarios, bc2_convocatorias, bc3_inscripciones), solo
 * resuelve la navegación inicial de la aplicación.
 *
 * Usa "redirect:" (no un nombre de vista Thymeleaf) porque este proyecto
 * no tiene motor de plantillas configurado (no hay spring-boot-starter-
 * thymeleaf en el pom.xml); main.html es un archivo estático servido
 * directamente desde src/main/resources/static.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio() {
        return "redirect:/main.html";
    }
}
