package es.albarregas.controllers;

import es.albarregas.beans.Genero;
import es.albarregas.beans.Usuario;
import es.albarregas.DAO.IUsuarioDAO;
import es.albarregas.DAOFactory.DAOFactory;
import es.albarregas.models.SecurityUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controlador para modificar el perfil del usuario.
 * @author jfco1
 */
@WebServlet(name = "ModificarPerfilController", urlPatterns = {"/ModificarPerfilController"})
public class ModificarPerfilController extends HttpServlet {

    private IUsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginController");
            return;
        }
        
        // Mostrar formulario de modificación
        request.getRequestDispatcher("/JSP/modificar_perfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginController");
            return;
        }
        
        String accion = request.getParameter("accion");
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        
        if ("cancelar".equals(accion)) {
            // Volver al menú correspondiente
            if (usuarioSesion.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/JSP/menu_admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/JSP/menu_normal.jsp");
            }
            return;
        }
        
        // Obtener datos del formulario
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String generoStr = request.getParameter("genero");
        String password = request.getParameter("password");
        
        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() ||
            apellidos == null || apellidos.trim().isEmpty()) {
            request.setAttribute("error", "Nombre y apellidos son obligatorios");
            request.getRequestDispatcher("/JSP/modificar_perfil.jsp").forward(request, response);
            return;
        }
        
        // Actualizar datos del usuario
        usuarioSesion.setNombre(nombre);
        usuarioSesion.setApellidos(apellidos);
        
        if (generoStr != null && !generoStr.isEmpty()) {
            usuarioSesion.setGenero(Genero.fromNombre(generoStr));
        }
        
        // Solo actualizar contraseña si se proporcionó una nueva
        if (password != null && !password.trim().isEmpty()) {
            usuarioSesion.setPassword(SecurityUtils.encriptarMD5(password));
        }
        
        // Guardar en BD
        if (usuarioDAO.actualizar(usuarioSesion)) {
            // Actualizar usuario en sesión
            Usuario usuarioActualizado = usuarioDAO.buscarPorId(usuarioSesion.getIdUsuario());
            session.setAttribute("usuario", usuarioActualizado);
            request.setAttribute("mensaje", "Datos actualizados correctamente");
        } else {
            request.setAttribute("error", "Error al actualizar los datos");
        }
        
        request.getRequestDispatcher("/JSP/modificar_perfil.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador de Modificación de Perfil";
    }
}
