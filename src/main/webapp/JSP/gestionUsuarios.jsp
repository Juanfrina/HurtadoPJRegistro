<%-- 
    Document   : gestionUsuarios
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Página de gestión de usuarios (solo admin)
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Verificar sesión y rol admin --%>
<c:if test="${empty sessionScope.usuario || sessionScope.usuario.rol != 'admin'}">
    <!DOCTYPE html>
    <html lang="es">
    <body>
        <form id="redirigirLogin" action="${pageContext.request.contextPath}/LoginController" method="post"></form>
        <script>document.getElementById('redirigirLogin').submit();</script>
    </body>
    </html>
    <c:set var="redirigir" value="true"/>
</c:if>
<c:if test="${empty redirigir}">

<c:set var="contexto" value="${pageContext.request.contextPath}" scope="request"/>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/INC/metas.inc"/>
        <link rel="stylesheet" type="text/css" href="${contexto}/CSS/estilos.css"/>
        <title>Usuarios</title>
        <script>
            function confirmarEliminacion(idUsuario, nombreCompleto) {
                if (confirm('¿Estás seguro de que quieres eliminar a ' + nombreCompleto + '?')) {
                    var form = document.createElement('form');
                    form.method = 'post';
                    form.action = '${contexto}/GestionUserController';
                    
                    var inputAccion = document.createElement('input');
                    inputAccion.type = 'hidden';
                    inputAccion.name = 'accion';
                    inputAccion.value = 'eliminar';
                    form.appendChild(inputAccion);
                    
                    var inputId = document.createElement('input');
                    inputId.type = 'hidden';
                    inputId.name = 'idUsuario';
                    inputId.value = idUsuario;
                    form.appendChild(inputId);
                    
                    document.body.appendChild(form);
                    form.submit();
                }
                return false;
            }
        </script>
    </head>
    <body>
        <main class="contenedor">
            <h1>Gestión de usuarios</h1>
            
            <%-- Mostrar mensajes --%>
            <c:if test="${not empty error}">
                <p class="mensaje-error">${error}</p>
            </c:if>
            <c:if test="${not empty mensaje}">
                <p class="mensaje-exito">${mensaje}</p>
            </c:if>
            
            <c:choose>
                <c:when test="${empty usuarios}">
                    <p>No hay usuarios registrados.</p>
                </c:when>
                <c:otherwise>
                    <table class="tabla-datos">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Apellidos</th>
                                <th>Usuario</th>
                                <th>Último acceso</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${usuarios}">
                                <tr>
                                    <td>${u.nombre}</td>
                                    <td>${u.apellidos}</td>
                                    <td>${u.username}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty u.ultimoAcceso}">
                                                ${u.ultimoAccesoFormateado}
                                            </c:when>
                                            <c:otherwise>
                                                Nunca
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="celda-checkbox">
                                        <input type="checkbox" 
                                               onclick="return confirmarEliminacion('${u.idUsuario}', '${u.nombre} ${u.apellidos}');"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
            
            <div class="menu-opciones">
                <a href="${contexto}/JSP/menuAdmin.jsp" class="enlace-boton">Volver al menú</a>
            </div>
        </main>
    </body>
</html>
</c:if>
