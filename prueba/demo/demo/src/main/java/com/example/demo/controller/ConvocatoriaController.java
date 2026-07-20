package com.example.demo.controller;

import com.example.demo.service.ConvocatoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConvocatoriaController {

    @Autowired
    private ConvocatoriaService service;

    @GetMapping("/convocatorias")
    public String convocatorias(Model model){

        model.addAttribute(
                "convocatorias",
                service.listar()
        );

        return "convocatorias";

    }

}