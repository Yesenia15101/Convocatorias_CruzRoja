package bc3_inscripciones.presentacion.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bc3_inscripciones.aplicacion.dto.InscripcionDTO;
import bc3_inscripciones.aplicacion.interfaces.IInscripcionServicio;
import bc3_inscripciones.dominio.factories.InscripcionDuplicadaException;
import bc3_inscripciones.presentacion.requests.InscripcionRequest;

/**
 * Controlador REST de Inscripciones.
 *
 * Es el único punto donde inscripcion.html debe "hablar" con el
 * backend (vía fetch()). El controlador NO contiene reglas de
 * negocio: solo traduce HTTP hacia/desde la capa de aplicación
 * (IInscripcionServicio), delegando el trabajo real.
 *
 * Flujo: inscripcion.html (fetch) -> InscripcionController
 *        -> InscripcionServicioAplicacion -> Inscripcion (dominio)
 *        -> IInscripcionRepositorio -> InscripcionRepositorioImpl
 *
 * @author Natalie Marleny Lazo Paxi
 */
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final IInscripcionServicio inscripcionServicio;

    public InscripcionController(IInscripcionServicio inscripcionServicio) {
        this.inscripcionServicio = inscripcionServicio;
    }

    /**
     * Registra la inscripción de un voluntario a una convocatoria.
     * Requisitos: HF.3.1.1 (Inscribirme) y HF.3.1.2 (registrar en el sistema).
     */
    @PostMapping
    public ResponseEntity<InscripcionDTO> registrar(@RequestBody InscripcionRequest solicitud) {
        InscripcionDTO inscripcionCreada = inscripcionServicio.registrarInscripcion(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionCreada);
    }

    /**
     * Consulta el estado y detalle de una inscripción.
     * Requisito HF.3.2.1 — Mostrar estado de inscripción.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionServicio.obtenerPorId(id));
    }

    /**
     * Lista las inscripciones previas de un voluntario. inscripcion.html la
     * usa para saber si ya está inscrito antes de habilitar el botón.
     */
    @GetMapping("/voluntario/{dni}")
    public ResponseEntity<List<InscripcionDTO>> listarPorVoluntario(@PathVariable String dni) {
        return ResponseEntity.ok(inscripcionServicio.listarPorVoluntario(dni));
    }

    /**
     * Confirma una inscripción pendiente.
     * Requisito HF.3.2.2 — Actualizar el estado de inscripción.
     */
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<InscripcionDTO> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionServicio.confirmarInscripcion(id));
    }

    /**
     * Rechaza una inscripción pendiente.
     */
    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<InscripcionDTO> rechazar(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionServicio.rechazarInscripcion(id));
    }

    /**
     * Cuenta los inscritos activos de una convocatoria. inscripcion.html
     * lo usa para comparar contra el mínimo de participantes requerido
     * (cantidadMinima, que hoy vive en el propio HTML).
     */
    @GetMapping("/convocatoria/{convocatoriaId}/contador")
    public ResponseEntity<Long> contarPorConvocatoria(@PathVariable Long convocatoriaId) {
        return ResponseEntity.ok(inscripcionServicio.contarActivasPorConvocatoria(convocatoriaId));
    }

    /**
     * Traduce la regla de negocio "inscripción duplicada" a HTTP 409 (Conflict),
     * con un mensaje claro para que el frontend lo muestre al voluntario.
     */
    @ExceptionHandler(InscripcionDuplicadaException.class)
    public ResponseEntity<String> manejarInscripcionDuplicada(InscripcionDuplicadaException excepcion) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(excepcion.getMessage());
    }

    /**
     * Traduce "inscripción no encontrada" a HTTP 404.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> manejarNoEncontrada(NoSuchElementException excepcion) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excepcion.getMessage());
    }

    /**
     * Traduce errores de validación de dominio a HTTP 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarSolicitudInvalida(IllegalArgumentException excepcion) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excepcion.getMessage());
    }
}