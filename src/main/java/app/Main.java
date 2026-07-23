package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {
        "app",
        "bc1_usuarios",
        "bc2_convocatorias",
        "bc3_inscripciones"
    }
)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}