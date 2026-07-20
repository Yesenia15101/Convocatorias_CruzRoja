package com.example.demo.service;

import com.example.demo.model.Convocatoria;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ConvocatoriaService {

    private final List<Convocatoria> convocatorias = List.of(

            new Convocatoria(
                    1,
                    "Campaña Donación de Sangre",
                    "Arequipa",
                    "25 Julio 2026",
                    "08:00 - 14:00",
                    42
            ),

            new Convocatoria(
                    2,
                    "Apoyo en Emergencias",
                    "Camaná",
                    "28 Julio 2026",
                    "07:30 - 17:00",
                    61
            ),

            new Convocatoria(
                    3,
                    "Capacitación Primeros Auxilios",
                    "Yanahuara",
                    "30 Julio 2026",
                    "09:00 - 13:00",
                    80
            ),

            new Convocatoria(
                    4,
                    "Campaña Médica",
                    "Cerro Colorado",
                    "05 Agosto 2026",
                    "08:30 - 15:00",
                    19
            )
    );

    public List<Convocatoria> listar() {

        return convocatorias.stream()
                .sorted(Comparator.comparing(Convocatoria::getFecha))
                .toList();
    }

    public Convocatoria buscarPorId(Integer id) {

        return convocatorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);

    }

}