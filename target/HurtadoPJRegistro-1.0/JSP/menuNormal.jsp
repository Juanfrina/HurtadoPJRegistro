<%-- 
    Document   : menu_normal
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Menú principal para usuarios normales
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Verificar sesión --%>
<c:if test="${empty sessionScope.usuario}">
    <!DOCTYPE html>
    <html>
    <body>
        <form id="redirigirLogin" action="${pageContext.request.contextPath}/LoginController" method="post"></form>
        <script>document.getElementById('redirigirLogin').submit();</script>
    </body>
    </html>
    <c:set var="redirigir" value="true"/>
</c:if>
<c:if test="${empty redirigir}">

<c:set var="contexto" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="usuario" value="${sessionScope.usuario}" scope="request"/>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/INC/metas.inc"/>
        <link rel="stylesheet" type="text/css" href="${contexto}/CSS/estilos.css"/>
        <title>Menú Usuario</title>
    </head>
    <body>
        <main class="contenedor">
            <h1>Menú de ${usuario.nombre} ${usuario.apellidos}</h1>
            
            <p class="info-acceso">
                <c:choose>
                    <c:when test="${sessionScope.primerAcceso == true}">
                        Bienvenido a nuestra web como usuario registrado
                    </c:when>
                    <c:when test="${not empty sessionScope.ultimoAccesoAnterior}">
                        Último acceso: ${sessionScope.ultimoAccesoAnterior}
                    </c:when>
                    <c:otherwise>
                        Bienvenido a nuestra web como usuario registrado
                    </c:otherwise>
                </c:choose>
            </p>
            
            <div class="menu-opciones">
                <form action="${contexto}/ModificarPerfilController" method="post" style="display:inline;">
                    <button type="submit" class="enlace-boton">Modificar datos</button>
                </form>
                <form action="${contexto}/VolverController" method="post" style="display:inline;">
                    <button type="submit" class="enlace-boton">Salir</button>
                </form>
            </div>
        </main>
    </body>
</html>
</c:if>
