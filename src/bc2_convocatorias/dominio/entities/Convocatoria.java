package bc2_convocatorias.dominio.entities;

import bc2_convocatorias.dominio.enums.EstadoConvocatoria;
import bc2_convocatorias.dominio.valueobjects.Horario;
import bc2_convocatorias.dominio.valueobjects.Ubicacion;
import java.time.LocalDate;

/** Convocatoria con la regla de cantidad mínima de participantes. */
public final class Convocatoria {
    private final String codigo;
    private final String nombre;
    private int minimoParticipantes;
    private boolean confirmada;
    private final LocalDate fechaServicio;
    private final Ubicacion ubicacion;
    private Horario horario;

    public Convocatoria(String codigo, String nombre, int minimoParticipantes) {
        this(codigo, nombre, minimoParticipantes, false);
    }

    public Convocatoria(
            String codigo, String nombre, int minimoParticipantes, boolean confirmada) {
        this(codigo, nombre, minimoParticipantes, confirmada, null,
                new Ubicacion("Lugar por definir"), null);
    }

    public Convocatoria(
            String codigo, String nombre, int minimoParticipantes, boolean confirmada,
            LocalDate fechaServicio, Ubicacion ubicacion, Horario horario) {
        this.codigo = validarTexto(codigo, "código");
        this.nombre = validarTexto(nombre, "nombre");
        definirMinimo(minimoParticipantes);
        this.confirmada = confirmada;
        this.fechaServicio = fechaServicio;
        this.ubicacion = java.util.Objects.requireNonNull(ubicacion);
        this.horario = horario;
    }

    public void definirMinimo(int minimo) {
        if (minimo <= 0) {
            throw new IllegalArgumentException("El mínimo debe ser mayor que cero");
        }
        if (confirmada) {
            throw new IllegalStateException("No se puede cambiar el mínimo de una convocatoria confirmada");
        }
        this.minimoParticipantes = minimo;
    }

    public void confirmar(int inscritosActuales) {
        if (inscritosActuales < minimoParticipantes) {
            throw new IllegalStateException("No se puede confirmar: aún faltan participantes");
        }
        confirmada = true;
    }

    public EstadoConvocatoria estadoPara(int inscritosActuales) {
        if (confirmada) {
            return EstadoConvocatoria.CONFIRMADA;
        }
        return inscritosActuales >= minimoParticipantes
                ? EstadoConvocatoria.MINIMO_ALCANZADO
                : EstadoConvocatoria.MINIMO_NO_ALCANZADO;
    }

    public int faltantesPara(int inscritosActuales) {
        return Math.max(0, minimoParticipantes - inscritosActuales);
    }

    public void definirHorario(Horario nuevoHorario) {
        this.horario = java.util.Objects.requireNonNull(
                nuevoHorario, "El horario es obligatorio");
    }

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El " + campo + " es obligatorio");
        }
        return valor.trim();
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getMinimoParticipantes() { return minimoParticipantes; }
    public boolean isConfirmada() { return confirmada; }
    public LocalDate getFechaServicio() { return fechaServicio; }
    public Ubicacion getUbicacion() { return ubicacion; }
    public Horario getHorario() { return horario; }
    public boolean tieneHorarioDefinido() { return horario != null; }
}
