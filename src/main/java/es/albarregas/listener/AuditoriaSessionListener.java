package es.albarregas.listener;

import es.albarregas.beans.Auditoria;
import es.albarregas.beans.Usuario;
import es.albarregas.DAO.IAuditoriaDAO;
import es.albarregas.DAOFactory.DAOFactory;
import java.time.LocalDateTime;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Listener para registrar auditorías cuando un usuario normal inicia o cierra sesión.
 * Implementa tanto HttpSessionAttributeListener como HttpSessionListener para capturar
 * todos los casos de cierre de sesión.
 * @author jfco1
 */
@WebListener
public class AuditoriaSessionListener implements HttpSessionAttributeListener, HttpSessionListener {

    /**
     * Se ejecuta cuando se añade un atributo a la sesión.
     * Si es el atributo "usuario" y es un usuario normal, registra la entrada.
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("usuario".equals(event.getName())) {
            Usuario usuario = (Usuario) event.getValue();
            
            // Solo registrar auditoría para usuarios normales
            if (usuario != null && !usuario.isAdmin()) {
                try {
                    IAuditoriaDAO auditoriaDAO = DAOFactory.getInstance().getAuditoriaDAO();
                    
                    Auditoria auditoria = new Auditoria();
                    auditoria.setIdUsuario(usuario.getIdUsuario());
                    auditoria.setFechaEntrada(LocalDateTime.now());
                    auditoria.setFechaSalida(null); // Se actualizará al cerrar sesión
                    
                    int idAuditoria = auditoriaDAO.insertarEntrada(auditoria);
                    
                    // Guardar el ID de auditoría en la sesión para actualizar la salida
                    if (idAuditoria > 0) {
                        event.getSession().setAttribute("idAuditoria", idAuditoria);
                    }
                } catch (Exception e) {
                    System.err.println("Error al registrar entrada de auditoría: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Se ejecuta cuando se elimina un atributo de la sesión.
     * Si es el atributo "usuario" y hay una auditoría pendiente, registra la salida.
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if ("usuario".equals(event.getName())) {
            registrarSalida(event.getSession());
        }
    }

    /**
     * Se ejecuta cuando se reemplaza un atributo en la sesión.
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        // No necesitamos hacer nada cuando se reemplaza el atributo
    }
    
    /**
     * Se ejecuta cuando se crea una sesión.
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // No necesitamos hacer nada al crear la sesión
    }
    
    /**
     * Se ejecuta cuando se destruye/invalida una sesión.
     * Esto captura el caso de session.invalidate() y timeout.
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        registrarSalida(se.getSession());
    }
    
    /**
     * Registra la hora de salida en la auditoría.
     */
    private void registrarSalida(HttpSession session) {
        try {
            Integer idAuditoria = (Integer) session.getAttribute("idAuditoria");
            
            if (idAuditoria != null && idAuditoria > 0) {
                IAuditoriaDAO auditoriaDAO = DAOFactory.getInstance().getAuditoriaDAO();
                auditoriaDAO.actualizarSalida(idAuditoria);
                // Marcar como procesado para evitar doble registro
                session.setAttribute("idAuditoria", -1);
            }
        } catch (Exception e) {
            System.err.println("Error al registrar salida de auditoría: " + e.getMessage());
        }
    }
}
