<%-- 
    Document   : modificarPerfil
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Página para modificar datos del perfil
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <title>Modificar Perfil</title>
    </head>
    <body>
        <main class="contenedor">
            <h1>Modificar datos del perfil</h1>
            
            <%-- Mostrar mensajes --%>
            <c:if test="${not empty error}">
                <p class="mensaje-error">${error}</p>
            </c:if>
            <c:if test="${not empty mensaje}">
                <p class="mensaje-exito">${mensaje}</p>
            </c:if>
            
            <form action="${contexto}/ModificarPerfilController" method="post">
                <table class="tabla-formulario">
                    <tr>
                        <td><label for="nombre">Nombre</label></td>
                        <td>
                            <input type="text" id="nombre" name="nombre" 
                                   value="${usuario.nombre}" required/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="apellidos">Apellidos</label></td>
                        <td>
                            <input type="text" id="apellidos" name="apellidos" 
                                   value="${usuario.apellidos}" required/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="genero">Género</label></td>
                        <td>
                            <select id="genero" name="genero">
                                <option value="Mujer" ${usuario.genero == 'Mujer' ? 'selected' : ''}>Mujer</option>
                                <option value="Hombre" ${usuario.genero == 'Hombre' ? 'selected' : ''}>Hombre</option>
                                <option value="Otro" ${usuario.genero == 'Otro' ? 'selected' : ''}>Otro</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="username">Username</label></td>
                        <td>
                            <input type="text" id="username" name="username" 
                                   value="${usuario.username}" readonly class="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="password">Nueva Contraseña</label></td>
                        <td>
                            <input type="password" id="password" name="password" 
                                   placeholder="Dejar vacío para no cambiar"/>
                        </td>
                    </tr>
                </table>
                
                <div class="botones">
                    <button type="submit" name="accion" value="guardar" class="enlace-boton">Realizar</button>
                    <a href="#" onclick="document.forms[0].reset();" class="enlace-boton">Limpiar</a>
                    <button type="submit" name="accion" value="cancelar" class="enlace-boton">Cancelar</button>
                </div>
            </form>
        </main>
    </body>
</html>
</c:if>
