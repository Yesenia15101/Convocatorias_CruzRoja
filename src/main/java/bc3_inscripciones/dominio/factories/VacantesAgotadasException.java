package bc3_inscripciones.dominio.factories;

/**
 * Excepción de negocio (no verificada) lanzada cuando una convocatoria
 * ya alcanzó su número máximo de vacantes y un voluntario intenta
 * inscribirse de todas formas.
 *
 * @author Natalie Marleny Lazo Paxi
 */
public class VacantesAgotadasException extends RuntimeException {

    public VacantesAgotadasException(Long convocatoriaId, int capacidadMaxima) {
        super(String.format(
                "La convocatoria %d ya alcanzó su límite de %d vacante(s); no se pueden registrar más inscripciones",
                convocatoriaId, capacidadMaxima));
    }
}
