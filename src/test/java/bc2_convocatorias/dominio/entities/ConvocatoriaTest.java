package bc2_convocatorias.dominio.entities;

import bc2_convocatorias.dominio.enums.EstadoConvocatoria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConvocatoriaTest {
    @Test
    void muestraCuantosParticipantesFaltan() {
        Convocatoria convocatoria = new Convocatoria("medica", "Campaña Médica", 3);

        assertEquals(EstadoConvocatoria.MINIMO_NO_ALCANZADO, convocatoria.estadoPara(2));
        assertEquals(1, convocatoria.faltantesPara(2));
    }

    @Test
    void alcanzaElMinimo() {
        Convocatoria convocatoria = new Convocatoria("medica", "Campaña Médica", 3);

        assertEquals(EstadoConvocatoria.MINIMO_ALCANZADO, convocatoria.estadoPara(3));
        assertEquals(0, convocatoria.faltantesPara(3));
    }

    @Test
    void impideConfirmarAntesDeAlcanzarElMinimo() {
        Convocatoria convocatoria = new Convocatoria("medica", "Campaña Médica", 3);

        assertThrows(IllegalStateException.class, () -> convocatoria.confirmar(2));
    }
}
