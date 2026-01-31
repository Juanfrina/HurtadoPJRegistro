package es.albarregas.DAO;

import es.albarregas.beans.Usuario;
import java.util.List;

/**
 * Interfaz DAO para operaciones con usuarios.
 * @author jfco1
 */
public interface IUsuarioDAO {
    
    /**
     * Busca un usuario por username y password.
     * @param username El nombre de usuario
     * @param passwordMD5 La contraseña encriptada en MD5
     * @return El usuario si existe, null si no
     */
    Usuario buscarPorCredenciales(String username, String passwordMD5);
    
    /**
     * Busca un usuario por su username.
     * @param username El nombre de usuario
     * @return El usuario si existe, null si no
     */
    Usuario buscarPorUsername(String username);
    
    /**
     * Busca un usuario por su ID.
     * @param idUsuario El ID del usuario
     * @return El usuario si existe, null si no
     */
    Usuario buscarPorId(int idUsuario);
    
    /**
     * Obtiene todos los usuarios de tipo normal (no admin).
     * @return Lista de usuarios normales
     */
    List<Usuario> obtenerUsuariosNormales();
    
    /**
     * Obtiene todos los usuarios.
     * @return Lista de todos los usuarios
     */
    List<Usuario> obtenerTodos();
    
    /**
     * Inserta un nuevo usuario.
     * @param usuario El usuario a insertar
     * @return true si se insertó correctamente, false si no
     */
    boolean insertar(Usuario usuario);
    
    /**
     * Actualiza los datos de un usuario existente.
     * @param usuario El usuario con los datos actualizados
     * @return true si se actualizó correctamente, false si no
     */
    boolean actualizar(Usuario usuario);
    
    /**
     * Actualiza el último acceso de un usuario.
     * @param idUsuario El ID del usuario
     * @return true si se actualizó correctamente, false si no
     */
    boolean actualizarUltimoAcceso(int idUsuario);
    
    /**
     * Elimina un usuario por su ID.
     * @param idUsuario El ID del usuario a eliminar
     * @return true si se eliminó correctamente, false si no
     */
    boolean eliminar(int idUsuario);
    
    /**
     * Verifica si existe un username en la base de datos.
     * @param username El username a verificar
     * @return true si existe, false si no
     */
    boolean existeUsername(String username);
}
