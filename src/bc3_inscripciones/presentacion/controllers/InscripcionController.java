package bc3_inscripciones.presentacion.controllers;

import bc3_inscripciones.aplicacion.dto.InscripcionDTO;
import bc3_inscripciones.aplicacion.interfaces.IInscripcionServicio;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controlador REST sin librerías externas.
 *
 * GET   /api/inscripciones
 * PATCH /api/inscripciones/{id}/estado
 */
public final class InscripcionController implements HttpHandler {
    private static final Pattern RUTA_ESTADO = Pattern.compile(
            "^/api/inscripciones/(\\d+)/estado$"
    );
    private static final Pattern CAMPO_ESTADO = Pattern.compile(
            "\\\"estado\\\"\\s*:\\s*\\\"([^\\\"]+)\\\""
    );

    private final IInscripcionServicio servicio;

    public InscripcionController(IInscripcionServicio servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio es obligatorio");
        }
        this.servicio = servicio;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        agregarCabeceras(exchange);

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            enviarSinContenido(exchange);
            return;
        }

        try {
            procesarSolicitud(exchange);
        } catch (NoSuchElementException exception) {
            enviarError(exchange, 404, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            enviarError(exchange, 400, exception.getMessage());
        } catch (IOException exception) {
            enviarError(exchange, 500, "No se pudo acceder a la tabla de inscripciones");
        } catch (RuntimeException exception) {
            enviarError(exchange, 500, "Ocurrió un error interno en el servidor");
        }
    }

    private void procesarSolicitud(HttpExchange exchange) throws IOException {
        String metodo = exchange.getRequestMethod().toUpperCase();
        URI uri = exchange.getRequestURI();
        String ruta = uri.getPath();

        if ("GET".equals(metodo) && "/api/inscripciones".equals(ruta)) {
            listar(exchange);
            return;
        }

        Matcher matcher = RUTA_ESTADO.matcher(ruta);
        if ("PATCH".equals(metodo) && matcher.matches()) {
            long id = Long.parseLong(matcher.group(1));
            actualizarEstado(exchange, id);
            return;
        }

        enviarError(exchange, 404, "Ruta no encontrada");
    }

    private void listar(HttpExchange exchange) throws IOException {
        List<InscripcionDTO> inscripciones = servicio.listarInscripciones();
        String json = inscripciones.stream()
                .map(this::aJson)
                .collect(java.util.stream.Collectors.joining(",", "[", "]"));
        enviarJson(exchange, 200, json);
    }

    private void actualizarEstado(HttpExchange exchange, long id) throws IOException {
        String cuerpo = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );
        Matcher matcher = CAMPO_ESTADO.matcher(cuerpo);

        if (!matcher.find()) {
            throw new IllegalArgumentException(
                    "El cuerpo debe incluir el campo estado"
            );
        }

        InscripcionDTO actualizada = servicio.actualizarEstado(id, matcher.group(1));
        enviarJson(exchange, 200, aJson(actualizada));
    }

    private String aJson(InscripcionDTO dto) {
        return "{" +
                "\"id\":" + dto.getId() + ',' +
                "\"nombre\":\"" + escaparJson(dto.getNombre()) + "\"," +
                "\"dni\":\"" + escaparJson(dto.getDni()) + "\"," +
                "\"correo\":\"" + escaparJson(dto.getCorreo()) + "\"," +
                "\"telefono\":\"" + escaparJson(dto.getTelefono()) + "\"," +
                "\"convocatoria\":\"" + escaparJson(dto.getConvocatoria()) + "\"," +
                "\"codigoConvocatoria\":\"" + escaparJson(dto.getCodigoConvocatoria()) + "\"," +
                "\"fechaInscripcion\":\"" + escaparJson(dto.getFechaInscripcion()) + "\"," +
                "\"estado\":\"" + escaparJson(dto.getEstado()) + "\"," +
                "\"experiencia\":\"" + escaparJson(dto.getExperiencia()) + "\"" +
                '}';
    }

    private static String escaparJson(String valor) {
        return valor
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private static void agregarCabeceras(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, PATCH, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
    }

    private static void enviarJson(HttpExchange exchange, int codigo, String json)
            throws IOException {
        byte[] contenido = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(codigo, contenido.length);
        try (OutputStream salida = exchange.getResponseBody()) {
            salida.write(contenido);
        }
    }

    private static void enviarError(HttpExchange exchange, int codigo, String mensaje)
            throws IOException {
        String json = "{\"error\":\"" + escaparJson(mensaje) + "\"}";
        enviarJson(exchange, codigo, json);
    }

    private static void enviarSinContenido(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }
}
