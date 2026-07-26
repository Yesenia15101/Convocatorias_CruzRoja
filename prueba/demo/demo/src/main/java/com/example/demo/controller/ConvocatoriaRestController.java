package com.example.demo.controller;

import com.example.demo.model.Convocatoria;
import com.example.demo.service.ConvocatoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/convocatorias")
public class ConvocatoriaRestController {

    private final ConvocatoriaService service;

    public ConvocatoriaRestController(ConvocatoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Convocatoria> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Convocatoria obtener(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

}