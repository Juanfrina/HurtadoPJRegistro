<%-- 
    Document   : error404
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Página de error 404 - No encontrado
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>

<c:set var="contexto" value="${pageContext.request.contextPath}" scope="request"/>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${contexto}/CSS/estilos.css"/>
        <title>Error 404 - Página no encontrada</title>
    </head>
    <body>
        <main class="contenedor">
            <h1>Error 404</h1>
            <p>La página que busca no existe.</p>
            <div class="menu-opciones">
                <a href="${contexto}/LoginController" class="enlace-boton">Volver al inicio</a>
            </div>
        </main>
    </body>
</html>
