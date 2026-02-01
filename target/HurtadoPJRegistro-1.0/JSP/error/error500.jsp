<%-- 
    Document   : error500
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Página de error 500 - Error interno del servidor
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>

<c:set var="contexto" value="${pageContext.request.contextPath}" scope="request"/>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${contexto}/CSS/estilos.css"/>
        <title>Error 500 - Error del servidor</title>
    </head>
    <body>
        <main class="contenedor">
            <h1>Error 500</h1>
            <p>Se ha producido un error interno en el servidor.</p>
            <p>Por favor, inténtelo de nuevo más tarde.</p>
            <div class="menu-opciones">
                <form action="${contexto}/LoginController" method="post" style="display:inline;">
                    <button type="submit" class="enlace-boton">Volver al inicio</button>
                </form>
            </div>
        </main>
    </body>
</html>
