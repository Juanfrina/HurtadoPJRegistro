<%-- 
    Document   : menuAdmin
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Menú principal para el administrador
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Verificar sesión --%>
<c:if test="${empty sessionScope.usuario}">
    <c:redirect url="/LoginController"/>
</c:if>

<%-- Verificar que es admin --%>
<c:if test="${sessionScope.usuario.rol != 'admin'}">
    <c:redirect url="/JSP/menu_normal.jsp"/>
</c:if>

<c:set var="contexto" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="usuario" value="${sessionScope.usuario}" scope="request"/>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/INC/metas.inc"/>
        <link rel="stylesheet" type="text/css" href="${contexto}/CSS/estilos.css"/>
        <title>Menú Administrador</title>
    </head>
    <body>
        <main class="contenedor">
            <h1>Menú del administrador</h1>
            
            <p class="info-acceso">
                <c:choose>
                    <c:when test="${not empty sessionScope.ultimoAccesoAnterior}">
                        Último acceso: ${sessionScope.ultimoAccesoAnterior}
                    </c:when>
                    <c:otherwise>
                        Bienvenido a nuestra web como usuario registrado
                    </c:otherwise>
                </c:choose>
            </p>
            
            <div class="menu-opciones">
                <a href="${contexto}/AuditoriaController" class="enlace-boton">Auditoría</a>
                <a href="${contexto}/GestionUserController" class="enlace-boton">Usuarios</a>
                <a href="${contexto}/VolverController" class="enlace-boton">Salir</a>
            </div>
        </main>
    </body>
</html>
