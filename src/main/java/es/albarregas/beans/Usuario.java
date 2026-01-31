package es.albarregas.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bean que representa un usuario del sistema.
 * @author jfco1
 */
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private Genero genero;
    private String username;
    private String password;
    private LocalDateTime ultimoAcceso;
    private String rol;

    // Constructor vacío requerido para BeanUtils
    public Usuario() {
    }

    // Constructor completo
    public Usuario(int idUsuario, String nombre, String apellidos, Genero genero, 
                   String username, String password, LocalDateTime ultimoAcceso, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.username = username;
        this.password = password;
        this.ultimoAcceso = ultimoAcceso;
        this.rol = rol;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = capitalizar(nombre);
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = capitalizar(apellidos);
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Método auxiliar para capitalizar nombres y apellidos
    private String capitalizar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return texto;
        }
        String[] palabras = texto.trim().toLowerCase().split("\\s+");
        StringBuilder resultado = new StringBuilder();
        for (String palabra : palabras) {
            if (palabra.length() > 0) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                         .append(palabra.substring(1))
                         .append(" ");
            }
        }
        return resultado.toString().trim();
    }

    // Método para obtener el último acceso formateado
    public String getUltimoAccesoFormateado() {
        if (ultimoAcceso == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy 'a las' HH:mm:ss");
        return ultimoAcceso.format(formatter);
    }

    // Método para obtener nombre completo
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(rol);
    }
}

