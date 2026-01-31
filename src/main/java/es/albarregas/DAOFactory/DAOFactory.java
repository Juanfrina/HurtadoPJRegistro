package es.albarregas.DAOFactory;

import es.albarregas.DAO.*;

/**
 * Factory para obtener instancias de los DAOs.
 * Implementa el patrón Factory para desacoplar la creación de DAOs.
 * @author jfco1
 */
public class DAOFactory {
    
    private static DAOFactory instance;
    private IUsuarioDAO usuarioDAO;
    private IAuditoriaDAO auditoriaDAO;
    
    /**
     * Constructor privado (patrón Singleton).
     */
    private DAOFactory() {
    }
    
    /**
     * Obtiene la instancia única de DAOFactory.
     * @return La instancia de DAOFactory
     */
    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }
    
    /**
     * Obtiene el DAO de usuarios.
     * @return Una instancia de IUsuarioDAO
     */
    public IUsuarioDAO getUsuarioDAO() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }
    
    /**
     * Obtiene el DAO de auditorías.
     * @return Una instancia de IAuditoriaDAO
     */
    public IAuditoriaDAO getAuditoriaDAO() {
        if (auditoriaDAO == null) {
            auditoriaDAO = new AuditoriaDAO();
        }
        return auditoriaDAO;
    }
}
