import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación.
 *
 * Escanea explícitamente los tres Bounded Contexts (bc1_usuarios,
 * bc2_convocatorias, bc3_inscripciones) porque no comparten un
 * paquete raíz común. Al arrancar, Spring Boot expone los
 * controladores REST y sirve los archivos de src/main/resources/static
 * (por ejemplo inscripcion.html) en http://localhost:8080/inscripcion.html
 *
 * @author Natalie Marleny Lazo Paxi
 */
@SpringBootApplication(scanBasePackages = {
        "bc1_usuarios",
        "bc2_convocatorias",
        "bc3_inscripciones"
})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
