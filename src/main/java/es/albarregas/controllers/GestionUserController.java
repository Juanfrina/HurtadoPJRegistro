package es.albarregas.controllers;

import es.albarregas.beans.Usuario;
import es.albarregas.DAO.IAuditoriaDAO;
import es.albarregas.DAO.IUsuarioDAO;
import es.albarregas.DAOFactory.DAOFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controlador para la gestión de usuarios (solo admin).
 * 
 * @author jfco1
 */
@WebServlet(name = "GestionUserController", urlPatterns = { "/GestionUserController" })
public class GestionUserController extends HttpServlet {

    private IUsuarioDAO usuarioDAO;
    private IAuditoriaDAO auditoriaDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
        auditoriaDAO = DAOFactory.getInstance().getAuditoriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar sesión y rol admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginController");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (!usuario.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/JSP/menuNormal.jsp");
            return;
        }

        // Obtener lista de usuarios normales
        List<Usuario> usuarios = usuarioDAO.obtenerUsuariosNormales();
        request.setAttribute("usuarios", usuarios);

        request.getRequestDispatcher("/JSP/gestionUsuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar sesión y rol admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginController");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (!usuario.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/JSP/menuNormal.jsp");
            return;
        }

        String accion = request.getParameter("accion");

        if ("eliminar".equals(accion)) {
            String idUsuarioStr = request.getParameter("idUsuario");
            if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
                int idUsuario = Integer.parseInt(idUsuarioStr);

                // Obtener datos del usuario antes de eliminar para el mensaje
                Usuario usuarioAEliminar = usuarioDAO.buscarPorId(idUsuario);
                String nombreCompleto = usuarioAEliminar != null
                        ? usuarioAEliminar.getNombre() + " " + usuarioAEliminar.getApellidos()
                        : "";

                // Primero eliminar auditorías del usuario (integridad referencial)
                auditoriaDAO.eliminarPorUsuario(idUsuario);

                // Eliminar usuario
                if (usuarioDAO.eliminar(idUsuario)) {
                    request.setAttribute("mensaje", "Se ha eliminado el usuario " + nombreCompleto);
                } else {
                    request.setAttribute("error", "No se pudo eliminar el usuario");
                }
            }
        }

        // Volver a cargar la lista
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador de Gestión de Usuarios";
    }
}
