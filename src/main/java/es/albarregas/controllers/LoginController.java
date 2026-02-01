package es.albarregas.controllers;

import es.albarregas.beans.Usuario;
import es.albarregas.DAO.IUsuarioDAO;
import es.albarregas.DAOFactory.DAOFactory;
import es.albarregas.models.SecurityUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controlador para el login de usuarios.
 * 
 * @author jfco1
 */
@WebServlet(name = "LoginController", urlPatterns = { "/LoginController" })
public class LoginController extends HttpServlet {

    private IUsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar si hay cookie de recordar usuario
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("recordarUsuario".equals(cookie.getName())) {
                    request.setAttribute("usernameRecordado", cookie.getValue());
                    break;
                }
            }
        }

        // Redirigir a la página de login
        request.getRequestDispatcher("/JSP/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String recordar = request.getParameter("recordar");

        // Validar campos vacíos
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Usuario y contraseña son obligatorios");
            request.getRequestDispatcher("/JSP/login.jsp").forward(request, response);
            return;
        }

        // Encriptar contraseña y buscar usuario
        String passwordMD5 = SecurityUtils.encriptarMD5(password);
        Usuario usuario = usuarioDAO.buscarPorCredenciales(username.trim(), passwordMD5);

        if (usuario == null) {
            request.setAttribute("error", "Usuario no registrado o credenciales incorrectas");
            request.setAttribute("usernameRecordado", username);
            request.getRequestDispatcher("/JSP/login.jsp").forward(request, response);
            return;
        }

        // Usuario válido - gestionar cookie de recordar
        if (recordar != null && "on".equals(recordar)) {
            Cookie cookie = new Cookie("recordarUsuario", username);
            cookie.setMaxAge(60 * 60 * 24 * 30); // 30 días
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        } else {
            // Eliminar cookie si existe
            Cookie cookie = new Cookie("recordarUsuario", "");
            cookie.setMaxAge(0);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }

        // Guardar el último acceso anterior antes de actualizarlo
        LocalDateTime ultimoAccesoAnterior = usuario.getUltimoAcceso();
        String ultimoAccesoFormateado = null;
        if (ultimoAccesoAnterior != null) {
            ultimoAccesoFormateado = ultimoAccesoAnterior.format(
                    java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy 'a las' HH:mm:ss"));
        }

        // Actualizar último acceso en BD
        usuarioDAO.actualizarUltimoAcceso(usuario.getIdUsuario());

        // Crear sesión y guardar usuario
        HttpSession session = request.getSession(true);
        session.setAttribute("usuario", usuario);
        session.setAttribute("ultimoAccesoAnterior", ultimoAccesoFormateado);

        // Redirigir según el rol
        if (usuario.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/JSP/menuAdmin.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/JSP/menuNormal.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Controlador de Login";
    }
}
