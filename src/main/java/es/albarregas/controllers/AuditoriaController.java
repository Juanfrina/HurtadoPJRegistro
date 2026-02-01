package es.albarregas.controllers;

import es.albarregas.beans.Auditoria;
import es.albarregas.beans.Usuario;
import es.albarregas.DAO.IAuditoriaDAO;
import es.albarregas.DAOFactory.DAOFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controlador para la gestión de auditorías.
 * 
 * @author jfco1
 */
@WebServlet(name = "AuditoriaController", urlPatterns = { "/AuditoriaController" })
public class AuditoriaController extends HttpServlet {

    private IAuditoriaDAO auditoriaDAO;

    @Override
    public void init() throws ServletException {
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

        // Obtener usuarios con auditorías para el selector
        List<Usuario> usuariosConAuditorias = auditoriaDAO.obtenerUsuariosConAuditorias();
        request.setAttribute("usuarios", usuariosConAuditorias);

        // Procesar parámetros de filtro
        String idUsuarioStr = request.getParameter("idUsuario");
        String fechaStr = request.getParameter("fecha");

        if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
            int idUsuarioFiltro = Integer.parseInt(idUsuarioStr);

            // Obtener fechas para el usuario seleccionado
            List<LocalDate> fechas;
            if (idUsuarioFiltro == 0) {
                // Todos los usuarios
                fechas = auditoriaDAO.obtenerTodasLasFechas();
            } else {
                fechas = auditoriaDAO.obtenerFechasPorUsuario(idUsuarioFiltro);
            }
            request.setAttribute("fechas", fechas);
            request.setAttribute("idUsuarioSeleccionado", idUsuarioFiltro);

            // Si hay fecha seleccionada, obtener auditorías
            if (fechaStr != null && !fechaStr.isEmpty()) {
                List<Auditoria> auditorias;

                // Manejar caso "todas" las fechas
                if ("todas".equalsIgnoreCase(fechaStr)) {
                    if (idUsuarioFiltro == 0) {
                        // Todos los usuarios, todas las fechas
                        auditorias = auditoriaDAO.obtenerTodos();
                    } else {
                        // Usuario específico, todas las fechas
                        auditorias = auditoriaDAO.obtenerPorUsuario(idUsuarioFiltro);
                    }
                } else {
                    // Fecha específica
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate fechaFiltro = LocalDate.parse(fechaStr, formatter);

                    if (idUsuarioFiltro == 0) {
                        // Todos los usuarios en esa fecha
                        auditorias = auditoriaDAO.obtenerPorFecha(fechaFiltro);
                    } else {
                        auditorias = auditoriaDAO.obtenerPorUsuarioYFecha(idUsuarioFiltro, fechaFiltro);
                    }
                }
                request.setAttribute("auditorias", auditorias);
                request.setAttribute("fechaSeleccionada", fechaStr);
            }
        }

        request.getRequestDispatcher("/JSP/auditorias.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador de Auditorías";
    }
}
