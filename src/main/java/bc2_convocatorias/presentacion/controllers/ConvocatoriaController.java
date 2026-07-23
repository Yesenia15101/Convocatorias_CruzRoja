package bc2_convocatorias.presentacion.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConvocatoriaController {

    @GetMapping("/")
    public String inicio() {
    return "main";
}
}