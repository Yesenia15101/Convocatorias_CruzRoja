import bc3_inscripciones.aplicacion.interfaces.IInscripcionServicio;
import bc3_inscripciones.aplicacion.services.InscripcionServicioAplicacion;
import bc3_inscripciones.dominio.repositories.IInscripcionRepositorio;
import bc3_inscripciones.infraestructura.persistence.InscripcionRepositorioImpl;
import bc3_inscripciones.presentacion.controllers.InscripcionController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

/** Punto de entrada del módulo de gestión de inscripciones. */
public final class Main {
    private static final int PUERTO = resolverPuerto();
    private static final Path RECURSOS = Path.of("resources")
            .toAbsolutePath()
            .normalize();
    private static final Path TABLA = RECURSOS.resolve("database/inscripciones.tsv");

    private Main() {
    }

    public static void main(String[] args) {
        try {
            IInscripcionRepositorio repositorio = new InscripcionRepositorioImpl(TABLA);
            IInscripcionServicio servicio = new InscripcionServicioAplicacion(repositorio);
            iniciarServidor(servicio);
        } catch (IOException exception) {
            System.err.println("No se pudo iniciar el módulo: " + exception.getMessage());
        }
    }

    private static int resolverPuerto() {
        String valor = System.getenv("PORT");
        if (valor == null || valor.isBlank()) {
            return 8080;
        }

        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("La variable PORT debe ser numérica", exception);
        }
    }

    private static void iniciarServidor(IInscripcionServicio servicio) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress(PUERTO), 0);
        servidor.createContext("/api/inscripciones", new InscripcionController(servicio));
        servidor.createContext("/", Main::servirRecurso);
        servidor.setExecutor(Executors.newFixedThreadPool(4));
        servidor.start();

        System.out.println("Módulo de inscripciones iniciado correctamente.");
        System.out.println("Abre: http://localhost:" + PUERTO + "/gestion_inscripciones.html");
    }

    private static void servirRecurso(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            enviarTexto(exchange, 405, "Método no permitido");
            return;
        }

        String ruta = exchange.getRequestURI().getPath();
        if ("/".equals(ruta)) {
            redirigir(exchange, "/gestion_inscripciones.html");
            return;
        }

        Path archivo = RECURSOS.resolve(ruta.substring(1)).normalize();
        if (!archivo.startsWith(RECURSOS)) {
            enviarTexto(exchange, 403, "Acceso denegado");
            return;
        }

        if (!Files.isRegularFile(archivo)) {
            enviarTexto(exchange, 404, "Recurso no encontrado");
            return;
        }

        byte[] contenido = Files.readAllBytes(archivo);
        exchange.getResponseHeaders().set("Content-Type", obtenerTipoContenido(archivo));
        exchange.sendResponseHeaders(200, contenido.length);
        try (OutputStream salida = exchange.getResponseBody()) {
            salida.write(contenido);
        }
    }

    private static String obtenerTipoContenido(Path archivo) throws IOException {
        String tipo = Files.probeContentType(archivo);
        if (tipo != null) {
            return tipo + (tipo.startsWith("text/") ? "; charset=UTF-8" : "");
        }

        String nombre = archivo.getFileName().toString().toLowerCase();
        if (nombre.endsWith(".html")) {
            return "text/html; charset=UTF-8";
        }
        if (nombre.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        }
        if (nombre.endsWith(".js")) {
            return "application/javascript; charset=UTF-8";
        }
        return "application/octet-stream";
    }

    private static void redirigir(HttpExchange exchange, String destino) throws IOException {
        exchange.getResponseHeaders().set("Location", destino);
        exchange.sendResponseHeaders(302, -1);
        exchange.close();
    }

    private static void enviarTexto(HttpExchange exchange, int codigo, String mensaje)
            throws IOException {
        byte[] contenido = mensaje.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(codigo, contenido.length);
        try (OutputStream salida = exchange.getResponseBody()) {
            salida.write(contenido);
        }
    }
}
