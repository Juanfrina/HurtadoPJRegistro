package es.albarregas.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Bean que representa un registro de auditoría.
 *
 * @author jfco1
 */
public class Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idAuditoria;
    private int idUsuario;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;

    // Campos adicionales para mostrar en vistas
    private String nombreUsuario;
    private String apellidosUsuario;

    public Auditoria() {
    }

    public Auditoria(int idAuditoria, int idUsuario, LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        this.idAuditoria = idAuditoria;
        this.idUsuario = idUsuario;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    // Getters y Setters
    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
    }

    // Métodos auxiliares para formateo
    public String getFechaFormateada() {
        if (fechaEntrada == null)
            return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fechaEntrada.format(formatter);
    }

    public String getHoraEntradaFormateada() {
        if (fechaEntrada == null)
            return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return fechaEntrada.format(formatter);
    }

    public String getHoraSalidaFormateada() {
        if (fechaSalida == null)
            return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return fechaSalida.format(formatter);
    }

    public LocalDate getFecha() {
        if (fechaEntrada == null)
            return null;
        return fechaEntrada.toLocalDate();
    }

    public LocalTime getHoraEntrada() {
        if (fechaEntrada == null)
            return null;
        return fechaEntrada.toLocalTime();
    }

    public LocalTime getHoraSalida() {
        if (fechaSalida == null)
            return null;
        return fechaSalida.toLocalTime();
    }
}
