package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    /**
     * Prueba de humo (smoke test): verifica que el contexto de Spring
     * cargue sin errores de configuración o de inyección de dependencias.
     * No requiere aserciones adicionales; si el contexto no levanta,
     * el test falla automáticamente por excepción.
     */
    @Test
    void contextLoads() {
        // Intencionalmente vacío: JUnit falla el test si el ApplicationContext
        // no se inicializa correctamente al ejecutar este método.
    }
}