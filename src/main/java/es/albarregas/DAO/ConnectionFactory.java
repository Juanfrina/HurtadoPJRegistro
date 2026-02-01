package es.albarregas.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Factory para obtener conexiones a la base de datos.
 * Gestiona dos DataSources:
 * - Usuario: para operaciones de lectura, inserción y actualización de usuarios
 * - AdminRegistro: para operaciones que requieren permisos de administrador
 * (borrado)
 * 
 * @author jfco1
 */
public class ConnectionFactory {

    private static ConnectionFactory instance;
    private DataSource dsUsuario;
    private DataSource dsAdmin;

    /**
     * Constructor privado (patrón Singleton).
     * Inicializa los dos DataSources desde JNDI.
     */
    private ConnectionFactory() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            // DataSource para operaciones de usuario (lectura, inserción, actualización)
            dsUsuario = (DataSource) envContext.lookup("jdbc/Usuario");

            // DataSource para operaciones de admin (cualquier operación incluido DELETE)
            dsAdmin = (DataSource) envContext.lookup("jdbc/AdminRegistro");

        } catch (NamingException e) {
            throw new RuntimeException("Error al inicializar ConnectionFactory: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene la instancia única de ConnectionFactory.
     * 
     * @return La instancia de ConnectionFactory
     */
    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    /**
     * Obtiene una conexión del DataSource de usuario.
     * Para operaciones de lectura, inserción y actualización de usuarios.
     * 
     * @return Una conexión a la base de datos
     * @throws SQLException Si ocurre un error al obtener la conexión
     */
    public Connection getConnectionUsuario() throws SQLException {
        return dsUsuario.getConnection();
    }

    /**
     * Obtiene una conexión del DataSource de administrador.
     * Para operaciones que requieren permisos de admin (ej: DELETE).
     * 
     * @return Una conexión a la base de datos
     * @throws SQLException Si ocurre un error al obtener la conexión
     */
    public Connection getConnectionAdmin() throws SQLException {
        return dsAdmin.getConnection();
    }

    /**
     * Cierra una conexión de forma segura.
     * 
     * @param conn La conexión a cerrar
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Log el error pero no lanzar excepción
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
