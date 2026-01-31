<%-- 
    Document   : auditorias
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Página de auditorías (solo admin)
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Verificar sesión y rol admin --%>
<c:if test="${empty sessionScope.usuario || sessionScope.usuario.rol != 'admin'}">
    <c:redirect url="/LoginController"/>
</c:if>

<c:set var="contexto" value="${pageContext.request.contextPath}" scope="request"/>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/INC/metas.inc"/>
        <link rel="stylesheet" type="text/css" href="${contexto}/CSS/estilos.css"/>
        <title>Auditorias</title>
        <script>
            function cargarFechas() {
                var selectUsuario = document.getElementById('idUsuario');
                var idUsuario = selectUsuario.value;
                
                if (idUsuario) {
                    // Habilitar selector de fechas y redirigir
                    window.location.href = '${contexto}/AuditoriaController?idUsuario=' + idUsuario;
                }
            }
            
            function cargarAuditorias() {
                var selectUsuario = document.getElementById('idUsuario');
                var selectFecha = document.getElementById('fecha');
                var idUsuario = selectUsuario.value;
                var fecha = selectFecha.value;
                
                if (idUsuario && fecha) {
                    window.location.href = '${contexto}/AuditoriaController?idUsuario=' + idUsuario + '&fecha=' + fecha;
                }
            }
        </script>
    </head>
    <body>
        <main class="contenedor">
            <h1>Auditorías</h1>
            
            <div class="filtros">
                <select id="idUsuario" name="idUsuario" onchange="cargarFechas()">
                    <option value="">Selecciona un usuario</option>
                    <option value="0" ${idUsuarioSeleccionado == 0 ? 'selected' : ''}>Todos</option>
                    <c:forEach var="u" items="${usuarios}">
                        <option value="${u.idUsuario}" ${idUsuarioSeleccionado == u.idUsuario ? 'selected' : ''}>
                            ${u.nombre} ${u.apellidos}
                        </option>
                    </c:forEach>
                </select>
                
                <select id="fecha" name="fecha" onchange="cargarAuditorias()"
                        ${empty fechas ? 'disabled' : ''}>
                    <option value="">Selecciona una fecha</option>
                    <c:if test="${not empty fechas}">
                        <option value="todas">Todas</option>
                        <c:forEach var="f" items="${fechas}">
                            <fmt:parseDate value="${f}" pattern="yyyy-MM-dd" var="fechaParsed" type="date"/>
                            <fmt:formatDate value="${fechaParsed}" pattern="dd-MM-yyyy" var="fechaFormateada"/>
                            <option value="${fechaFormateada}" ${fechaSeleccionada == fechaFormateada ? 'selected' : ''}>
                                ${fechaFormateada}
                            </option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            
            <%-- Mostrar tabla de auditorías si hay datos --%>
            <c:if test="${not empty auditorias}">
                <table class="tabla-datos">
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Fecha</th>
                            <th>Entrada</th>
                            <th>Salida</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="a" items="${auditorias}">
                            <tr>
                                <td>${a.nombreUsuario}</td>
                                <td>${a.apellidosUsuario}</td>
                                <td>${a.fechaFormateada}</td>
                                <td>${a.horaEntradaFormateada}</td>
                                <td>${a.horaSalidaFormateada}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            
            <div class="menu-opciones">
                <a href="${contexto}/JSP/menu_admin.jsp" class="enlace-boton">Volver al menú</a>
            </div>
        </main>
    </body>
</html>
