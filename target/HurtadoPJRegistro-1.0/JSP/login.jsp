<%-- 
    Document   : login
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Página de acceso a la aplicación
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="contexto" value="${pageContext.request.contextPath}" scope="request"/>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/INC/metas.inc"/>
        <link rel="stylesheet" type="text/css" href="${contexto}/CSS/estilos.css"/>
        <title>Registro</title>
    </head>
    <body>
        <main class="contenedor">
            <h1>Acceso a la aplicación</h1>
            
            <%-- Mostrar mensaje de error si existe --%>
            <c:if test="${not empty error}">
                <p class="mensaje-error">${error}</p>
            </c:if>
            
            <form action="${contexto}/LoginController" method="post">
                <table class="tabla-formulario">
                    <tr>
                        <td><label for="username">Usuario</label></td>
                        <td>
                            <input type="text" id="username" name="username" 
                                   placeholder="Ej. pepe"
                                   value="${not empty usernameRecordado ? usernameRecordado : ''}" 
                                   required/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="password">Contraseña</label></td>
                        <td>
                            <input type="password" id="password" name="password" required/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="checkbox" id="recordar" name="recordar"/>
                            <label for="recordar">Recordar usuario</label>
                        </td>
                    </tr>
                </table>
                <div class="menu-opciones">
                    <button type="submit" class="enlace-boton">Acceder</button>
                    <button type="reset" class="enlace-boton">Limpiar</button>
                </div>
            </form>
            <div class="menu-opciones">
                <form id="formRegistroRedir" action="${contexto}/RegistroController" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="mostrar" />
                    <button type="submit" class="enlace-boton">Registro</button>
                </form>
            </div>
        </main>
    </body>
</html>
