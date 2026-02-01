package es.albarregas.controllers;

import es.albarregas.beans.Usuario;
import es.albarregas.beans.Usuario.Genero;
import es.albarregas.DAO.IUsuarioDAO;
import es.albarregas.DAOFactory.DAOFactory;
import es.albarregas.models.EnumConverter;
import es.albarregas.models.SecurityUtils;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * Controlador para el registro de nuevos usuarios.
 * 
 * @author jfco1
 */
@WebServlet(name = "RegistroController", urlPatterns = { "/RegistroController" })
public class RegistroController extends HttpServlet {

    private IUsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
        // Registrar el convertidor para el enum Genero
        ConvertUtils.register(new EnumConverter(), Genero.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Rechazar peticiones GET - solo se permite POST
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Solo se permite POST");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String accion = request.getParameter("accion");

        // Si la acción es nula o "mostrar", solo mostrar el formulario sin errores
        if (accion == null || "mostrar".equals(accion)) {
            request.getRequestDispatcher("/JSP/registro.jsp").forward(request, response);
            return;
        }

        if ("cancelar".equals(accion)) {
            request.getRequestDispatcher("/LoginController").forward(request, response);
            return;
        }

        try {

            // Crear usuario y poblarlo con BeanUtils
            Usuario usuario = new Usuario();
            BeanUtils.populate(usuario, request.getParameterMap());

            // Validar campos obligatorios
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty() ||
                    usuario.getApellidos() == null || usuario.getApellidos().trim().isEmpty() ||
                    usuario.getUsername() == null || usuario.getUsername().trim().isEmpty() ||
                    usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                request.setAttribute("error", "Todos los campos son obligatorios");
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("/JSP/registro.jsp").forward(request, response);
                return;
            }

            // Validar solo letras en nombre y apellidos
            if (!usuario.getNombre().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
                request.setAttribute("error", "El nombre solo puede contener letras");
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("/JSP/registro.jsp").forward(request, response);
                return;
            }
            if (!usuario.getApellidos().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+")) {
                request.setAttribute("error", "Los apellidos solo pueden contener letras");
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("/JSP/registro.jsp").forward(request, response);
                return;
            }

            // Verificar si el username ya existe
            if (usuarioDAO.existeUsername(usuario.getUsername())) {
                request.setAttribute("error", "El nombre de usuario ya existe");
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("/JSP/registro.jsp").forward(request, response);
                return;
            }

            // Encriptar contraseña
            String passwordOriginal = usuario.getPassword();
            usuario.setPassword(SecurityUtils.encriptarMD5(passwordOriginal));

            // Establecer rol como normal
            usuario.setRol("normal");

            // ultimoAcceso queda null para nuevo usuario
            usuario.setUltimoAcceso(null);

            // Insertar usuario
            if (usuarioDAO.insertar(usuario)) {
                // Registro exitoso - crear sesión
                HttpSession session = request.getSession(true);
                session.setAttribute("usuario", usuario);
                session.setAttribute("primerAcceso", true);

                // Redirigir al menú de usuario normal
                response.sendRedirect(request.getContextPath() + "/JSP/menuNormal.jsp");
            } else {
                request.setAttribute("error", "Error al registrar el usuario. Inténtelo de nuevo.");
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("/JSP/registro.jsp").forward(request, response);
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("error", "Error al procesar los datos: " + e.getMessage());
            request.getRequestDispatcher("/JSP/registro.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Controlador de Registro de Usuarios";
    }
}
