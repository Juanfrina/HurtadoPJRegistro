<%-- 
    Document   : error
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Página de error genérico
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>

<c:set var="contexto" value="${pageContext.request.contextPath}" scope="request"/>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${contexto}/CSS/estilos.css"/>
        <title>Error - Algo salió mal</title>
    </head>
    <body>
        <main class="contenedor">
            <h1>Error</h1>
            <p>Se ha producido un error inesperado.</p>
            <c:if test="${not empty pageContext.exception}">
                <p class="mensaje-error">${pageContext.exception.message}</p>
            </c:if>
            <div class="menu-opciones">
                <form action="${contexto}/LoginController" method="post" style="display:inline;">
                    <button type="submit" class="enlace-boton">Volver al inicio</button>
                </form>
            </div>
        </main>
    </body>
</html>
