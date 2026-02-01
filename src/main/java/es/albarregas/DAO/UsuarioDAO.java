package es.albarregas.DAO;

import es.albarregas.beans.Usuario;
import es.albarregas.beans.Usuario.Genero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para operaciones con usuarios.
 * 
 * @author jfco1
 */

public class UsuarioDAO implements IUsuarioDAO {
    private final ConnectionFactory connectionFactory;
    private Connection conn;

    public UsuarioDAO() {
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    @Override
    public Usuario buscarPorCredenciales(String username, String passwordMD5) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionUsuario();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, passwordMD5);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por credenciales: " + e.getMessage());
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar ResultSet: " + e.getMessage());
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return null;
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionUsuario();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por username: " + e.getMessage());
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar ResultSet: " + e.getMessage());
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return null;
    }

    @Override
    public Usuario buscarPorId(int idUsuario) {
        String sql = "SELECT * FROM usuarios WHERE idusuario = ?";
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionUsuario();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID: " + e.getMessage());
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar ResultSet: " + e.getMessage());
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerUsuariosNormales() {
        String sql = "SELECT * FROM usuarios WHERE rol = 'normal' ORDER BY apellidos, nombre";
        List<Usuario> usuarios = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionUsuario();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios normales: " + e.getMessage());
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar ResultSet: " + e.getMessage());
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return usuarios;
    }

    @Override
    public List<Usuario> obtenerTodos() {
        String sql = "SELECT * FROM usuarios ORDER BY rol, apellidos, nombre";
        List<Usuario> usuarios = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionUsuario();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar ResultSet: " + e.getMessage());
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return usuarios;
    }

    @Override
    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellidos, genero, username, password, ultimoAcceso, rol) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionFactory.getConnectionUsuario();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getGenero() != null ? usuario.getGenero().getInicial() : null);
            ps.setString(4, usuario.getUsername());
            ps.setString(5, usuario.getPassword());
            ps.setObject(6, usuario.getUltimoAcceso());
            ps.setString(7, usuario.getRol() != null ? usuario.getRol() : "normal");

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    usuario.setIdUsuario(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return false;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellidos = ?, genero = ?, password = ? " +
                "WHERE idusuario = ?";
        conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionFactory.getConnectionUsuario();
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getGenero() != null ? usuario.getGenero().getInicial() : null);
            ps.setString(4, usuario.getPassword());
            ps.setInt(5, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return false;
    }

    @Override
    public boolean actualizarUltimoAcceso(int idUsuario) {
        String sql = "UPDATE usuarios SET ultimoAcceso = ? WHERE idusuario = ?";
        conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionFactory.getConnectionUsuario();
            ps = conn.prepareStatement(sql);
            ps.setObject(1, LocalDateTime.now());
            ps.setInt(2, idUsuario);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar último acceso: " + e.getMessage());
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            ConnectionFactory.closeConnection(conn);
        }
        return false;
    }

    @Override
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE idusuario = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Usa conexión de admin para DELETE
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return false;
    }

    @Override
    public boolean existeUsername(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionUsuario();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar username: " + e.getMessage());
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar ResultSet: " + e.getMessage());
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return false;
    }

    /**
     * Mapea un ResultSet a un objeto Usuario.
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("idusuario"));

        // No capitalizamos al leer, ya viene capitalizado de la BD
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        // Usamos setters directos para evitar doble capitalización
        try {
            java.lang.reflect.Field nombreField = Usuario.class.getDeclaredField("nombre");
            nombreField.setAccessible(true);
            nombreField.set(usuario, nombre);

            java.lang.reflect.Field apellidosField = Usuario.class.getDeclaredField("apellidos");
            apellidosField.setAccessible(true);
            apellidosField.set(usuario, apellidos);
        } catch (Exception e) {
            // Si falla la reflexión, usar setters normales
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
        }

        String generoStr = rs.getString("genero");
        if (generoStr != null && !generoStr.isEmpty()) {
            usuario.setGenero(Genero.fromInicial(generoStr));
        }

        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));

        Timestamp ultimoAcceso = rs.getTimestamp("ultimoAcceso");
        if (ultimoAcceso != null) {
            usuario.setUltimoAcceso(ultimoAcceso.toLocalDateTime());
        }

        usuario.setRol(rs.getString("rol"));

        return usuario;
    }

    /**
     * Cierra recursos de base de datos.
     */
    @Override
    public void closeConnection() {
        ConnectionFactory.closeConnection(conn);
        conn = null;
    }
}
