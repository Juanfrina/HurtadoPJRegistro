package es.albarregas.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controlador para cerrar sesión (logout).
 * @author jfco1
 */
@WebServlet(name = "VolverController", urlPatterns = {"/VolverController", "/LogoutController"})
public class VolverController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cerrarSesion(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cerrarSesion(request, response);
    }
    
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        // Invalidar sesión
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Redirigir al login
        response.sendRedirect(request.getContextPath() + "/LoginController");
    }

    @Override
    public String getServletInfo() {
        return "Controlador de Logout";
    }
}
