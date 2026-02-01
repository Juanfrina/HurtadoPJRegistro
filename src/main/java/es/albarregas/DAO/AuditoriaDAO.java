package es.albarregas.DAO;

import es.albarregas.beans.Auditoria;
import es.albarregas.beans.Usuario;
import es.albarregas.beans.Usuario.Genero;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para operaciones con auditorías.
 * 
 * @author jfco1
 */

public class AuditoriaDAO implements IAuditoriaDAO {
    private final ConnectionFactory connectionFactory;
    private Connection conn;

    public AuditoriaDAO() {
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    @Override
    public int insertarEntrada(Auditoria auditoria) {
        String sql = "INSERT INTO auditoria (idusuario, fechaEntrada, fechaSalida) VALUES (?, ?, ?)";
        conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, auditoria.getIdUsuario());
            ps.setObject(2, auditoria.getFechaEntrada());
            ps.setObject(3, auditoria.getFechaSalida());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    auditoria.setIdAuditoria(idGenerado);
                    return idGenerado;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar entrada de auditoría: " + e.getMessage());
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            this.closeConnection();
        }
        return -1;
    }

    @Override
    public boolean actualizarSalida(int idAuditoria) {
        String sql = "UPDATE auditoria SET fechaSalida = ? WHERE idauditoria = ?";
        conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            ps.setObject(1, LocalDateTime.now());
            ps.setInt(2, idAuditoria);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar salida de auditoría: " + e.getMessage());
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
    public List<Auditoria> obtenerTodos() {
        String sql = "SELECT a.*, u.nombre, u.apellidos FROM auditoria a " +
                "INNER JOIN usuarios u ON a.idusuario = u.idusuario " +
                "ORDER BY a.fechaEntrada DESC";
        List<Auditoria> auditorias = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                auditorias.add(mapearAuditoria(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las auditorías: " + e.getMessage());
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
        return auditorias;
    }

    @Override
    public List<Auditoria> obtenerPorUsuario(int idUsuario) {
        String sql = "SELECT a.*, u.nombre, u.apellidos FROM auditoria a " +
                "INNER JOIN usuarios u ON a.idusuario = u.idusuario " +
                "WHERE a.idusuario = ? ORDER BY a.fechaEntrada DESC";
        List<Auditoria> auditorias = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                auditorias.add(mapearAuditoria(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener auditorías por usuario: " + e.getMessage());
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
        return auditorias;
    }

    @Override
    public List<Auditoria> obtenerPorUsuarioYFecha(int idUsuario, LocalDate fecha) {
        String sql = "SELECT a.*, u.nombre, u.apellidos FROM auditoria a " +
                "INNER JOIN usuarios u ON a.idusuario = u.idusuario " +
                "WHERE a.idusuario = ? AND DATE(a.fechaEntrada) = ? " +
                "ORDER BY a.fechaEntrada DESC";
        List<Auditoria> auditorias = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setDate(2, Date.valueOf(fecha));
            rs = ps.executeQuery();

            while (rs.next()) {
                auditorias.add(mapearAuditoria(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener auditorías por usuario y fecha: " + e.getMessage());
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
        return auditorias;
    }

    @Override
    public List<Auditoria> obtenerPorFecha(LocalDate fecha) {
        String sql = "SELECT a.*, u.nombre, u.apellidos FROM auditoria a " +
                "INNER JOIN usuarios u ON a.idusuario = u.idusuario " +
                "WHERE DATE(a.fechaEntrada) = ? " +
                "ORDER BY u.apellidos, u.nombre, a.fechaEntrada DESC";
        List<Auditoria> auditorias = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(fecha));
            rs = ps.executeQuery();

            while (rs.next()) {
                auditorias.add(mapearAuditoria(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener auditorías por fecha: " + e.getMessage());
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
        return auditorias;
    }

    @Override
    public List<LocalDate> obtenerFechasPorUsuario(int idUsuario) {
        String sql = "SELECT DISTINCT DATE(fechaEntrada) as fecha FROM auditoria " +
                "WHERE idusuario = ? ORDER BY fecha DESC";
        List<LocalDate> fechas = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                Date fecha = rs.getDate("fecha");
                if (fecha != null) {
                    fechas.add(fecha.toLocalDate());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener fechas por usuario: " + e.getMessage());
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
        return fechas;
    }

    @Override
    public List<LocalDate> obtenerTodasLasFechas() {
        String sql = "SELECT DISTINCT DATE(fechaEntrada) as fecha FROM auditoria ORDER BY fecha DESC";
        List<LocalDate> fechas = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Date fecha = rs.getDate("fecha");
                if (fecha != null) {
                    fechas.add(fecha.toLocalDate());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las fechas: " + e.getMessage());
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
        return fechas;
    }

    @Override
    public List<Usuario> obtenerUsuariosConAuditorias() {
        String sql = "SELECT DISTINCT u.* FROM usuarios u " +
                "INNER JOIN auditoria a ON u.idusuario = a.idusuario " +
                "ORDER BY u.apellidos, u.nombre";
        List<Usuario> usuarios = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios con auditorías: " + e.getMessage());
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
    public boolean eliminarPorUsuario(int idUsuario) {
        String sql = "DELETE FROM auditoria WHERE idusuario = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionFactory.getConnectionAdmin();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar auditorías por usuario: " + e.getMessage());
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

    /**
     * Mapea un ResultSet a un objeto Auditoria.
     */
    private Auditoria mapearAuditoria(ResultSet rs) throws SQLException {
        Auditoria auditoria = new Auditoria();
        auditoria.setIdAuditoria(rs.getInt("idauditoria"));
        auditoria.setIdUsuario(rs.getInt("idusuario"));

        Timestamp fechaEntrada = rs.getTimestamp("fechaEntrada");
        if (fechaEntrada != null) {
            auditoria.setFechaEntrada(fechaEntrada.toLocalDateTime());
        }

        Timestamp fechaSalida = rs.getTimestamp("fechaSalida");
        if (fechaSalida != null) {
            auditoria.setFechaSalida(fechaSalida.toLocalDateTime());
        }

        // Campos adicionales si están disponibles
        try {
            auditoria.setNombreUsuario(rs.getString("nombre"));
            auditoria.setApellidosUsuario(rs.getString("apellidos"));
        } catch (SQLException e) {
            // Los campos pueden no estar disponibles en todas las consultas
        }

        return auditoria;
    }

    /**
     * Mapea un ResultSet a un objeto Usuario.
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("idusuario"));

        try {
            java.lang.reflect.Field nombreField = Usuario.class.getDeclaredField("nombre");
            nombreField.setAccessible(true);
            nombreField.set(usuario, rs.getString("nombre"));

            java.lang.reflect.Field apellidosField = Usuario.class.getDeclaredField("apellidos");
            apellidosField.setAccessible(true);
            apellidosField.set(usuario, rs.getString("apellidos"));
        } catch (Exception e) {
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellidos(rs.getString("apellidos"));
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
