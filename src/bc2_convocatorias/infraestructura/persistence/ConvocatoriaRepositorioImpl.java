package bc2_convocatorias.infraestructura.persistence;

import bc2_convocatorias.dominio.entities.Convocatoria;
import bc2_convocatorias.dominio.valueobjects.Horario;
import bc2_convocatorias.dominio.valueobjects.Ubicacion;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Repositorio persistente. La lectura admite la tabla antigua de cuatro columnas. */
@Repository
public final class ConvocatoriaRepositorioImpl {
    private final Map<String, Convocatoria> convocatorias = new LinkedHashMap<>();
    private final Path archivo = Path.of("resources", "database", "convocatorias.tsv");

    public ConvocatoriaRepositorioImpl() throws IOException {
        if (Files.exists(archivo) && Files.size(archivo) > 0) cargar();
        else cargarDatosIniciales();
        guardarCambios();
    }

    private void cargar() throws IOException {
        List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
        for (int i = 1; i < lineas.size(); i++) {
            if (lineas.get(i).isBlank()) continue;
            String[] c = lineas.get(i).split("\t", -1);
            if (c.length >= 8) {
                agregar(new Convocatoria(c[0], c[1], Integer.parseInt(c[2]),
                        Boolean.parseBoolean(c[3]), fecha(c[4]), new Ubicacion(c[5]),
                        horario(c[6], c[7])));
            } else {
                agregar(enriquecerDatosAntiguos(
                        c[0], c[1], Integer.parseInt(c[2]), Boolean.parseBoolean(c[3])));
            }
        }
    }

    private void cargarDatosIniciales() {
        agregar(crear("donacion", "Campaña de Donación de Sangre", 3,
                "2026-07-25", "Arequipa - Cercado", "08:00", "14:00"));
        agregar(crear("emergencias", "Apoyo en Emergencias", 2,
                "2026-07-28", "Camaná", "07:30", "17:00"));
        agregar(crear("auxilios", "Capacitación en Primeros Auxilios", 2,
                "2026-07-30", "Yanahuara", "09:00", "13:00"));
        agregar(new Convocatoria("medica", "Campaña Médica Comunitaria", 3,
                false, LocalDate.parse("2026-08-05"),
                new Ubicacion("Cerro Colorado"), null));
    }

    private Convocatoria enriquecerDatosAntiguos(
            String codigo, String nombre, int minimo, boolean confirmada) {
        return switch (codigo) {
            case "donacion" -> crear(codigo, nombre, minimo, confirmada,
                    "2026-07-25", "Arequipa - Cercado", "08:00", "14:00");
            case "emergencias" -> crear(codigo, nombre, minimo, confirmada,
                    "2026-07-28", "Camaná", "07:30", "17:00");
            case "auxilios" -> crear(codigo, nombre, minimo, confirmada,
                    "2026-07-30", "Yanahuara", "09:00", "13:00");
            default -> new Convocatoria(codigo, nombre, minimo, confirmada,
                    LocalDate.parse("2026-08-05"), new Ubicacion("Cerro Colorado"), null);
        };
    }

    private static Convocatoria crear(
            String codigo, String nombre, int minimo, String fecha,
            String lugar, String inicio, String fin) {
        return crear(codigo, nombre, minimo, false, fecha, lugar, inicio, fin);
    }

    private static Convocatoria crear(
            String codigo, String nombre, int minimo, boolean confirmada,
            String fecha, String lugar, String inicio, String fin) {
        return new Convocatoria(codigo, nombre, minimo, confirmada,
                LocalDate.parse(fecha), new Ubicacion(lugar), Horario.desdeTexto(inicio, fin));
    }

    private static LocalDate fecha(String valor) {
        return valor.isBlank() ? null : LocalDate.parse(valor);
    }

    private static Horario horario(String inicio, String fin) {
        return inicio.isBlank() || fin.isBlank() ? null : Horario.desdeTexto(inicio, fin);
    }

    private void agregar(Convocatoria convocatoria) {
        convocatorias.put(convocatoria.getCodigo(), convocatoria);
    }

    public List<Convocatoria> listar() { return List.copyOf(convocatorias.values()); }
    public Optional<Convocatoria> buscar(String codigo) {
        return Optional.ofNullable(convocatorias.get(codigo));
    }

    public void guardarCambios() throws IOException {
        Files.createDirectories(archivo.getParent());
        List<String> lineas = new java.util.ArrayList<>();
        lineas.add("codigo\tnombre\tminimoParticipantes\tconfirmada\tfecha\tlugar\thoraInicio\thoraFin");
        for (Convocatoria c : convocatorias.values()) {
            lineas.add(String.join("\t", c.getCodigo(), c.getNombre(),
                    Integer.toString(c.getMinimoParticipantes()), Boolean.toString(c.isConfirmada()),
                    c.getFechaServicio() == null ? "" : c.getFechaServicio().toString(),
                    c.getUbicacion().lugar(),
                    c.tieneHorarioDefinido() ? c.getHorario().getHoraInicioTexto() : "",
                    c.tieneHorarioDefinido() ? c.getHorario().getHoraFinTexto() : ""));
        }
        Files.write(archivo, lineas, StandardCharsets.UTF_8);
    }
}
