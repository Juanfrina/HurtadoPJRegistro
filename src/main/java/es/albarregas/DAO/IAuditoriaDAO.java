package es.albarregas.DAO;

import es.albarregas.beans.Auditoria;
import es.albarregas.beans.Usuario;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz DAO para operaciones con auditorías.
 * @author jfco1
 */
public interface IAuditoriaDAO {
    
    /**
     * Inserta un nuevo registro de auditoría (entrada de usuario).
     * @param auditoria El registro de auditoría a insertar
     * @return El ID generado del registro, o -1 si falló
     */
    int insertarEntrada(Auditoria auditoria);
    
    /**
     * Actualiza la hora de salida de un registro de auditoría.
     * @param idAuditoria El ID del registro de auditoría
     * @return true si se actualizó correctamente, false si no
     */
    boolean actualizarSalida(int idAuditoria);
    
    /**
     * Obtiene todos los registros de auditoría.
     * @return Lista de todos los registros de auditoría
     */
    List<Auditoria> obtenerTodos();
    
    /**
     * Obtiene los registros de auditoría de un usuario específico.
     * @param idUsuario El ID del usuario
     * @return Lista de registros de auditoría del usuario
     */
    List<Auditoria> obtenerPorUsuario(int idUsuario);
    
    /**
     * Obtiene los registros de auditoría de un usuario en una fecha específica.
     * @param idUsuario El ID del usuario
     * @param fecha La fecha a consultar
     * @return Lista de registros de auditoría
     */
    List<Auditoria> obtenerPorUsuarioYFecha(int idUsuario, LocalDate fecha);
    
    /**
     * Obtiene los registros de auditoría de una fecha específica (todos los usuarios).
     * @param fecha La fecha a consultar
     * @return Lista de registros de auditoría
     */
    List<Auditoria> obtenerPorFecha(LocalDate fecha);
    
    /**
     * Obtiene las fechas únicas de acceso de un usuario.
     * @param idUsuario El ID del usuario
     * @return Lista de fechas únicas
     */
    List<LocalDate> obtenerFechasPorUsuario(int idUsuario);
    
    /**
     * Obtiene todas las fechas únicas de acceso.
     * @return Lista de fechas únicas
     */
    List<LocalDate> obtenerTodasLasFechas();
    
    /**
     * Obtiene los usuarios que tienen registros de auditoría.
     * @return Lista de usuarios con auditorías
     */
    List<Usuario> obtenerUsuariosConAuditorias();
    
    /**
     * Elimina las auditorías de un usuario (cuando se elimina el usuario).
     * @param idUsuario El ID del usuario
     * @return true si se eliminaron correctamente, false si no
     */
    boolean eliminarPorUsuario(int idUsuario);
}
