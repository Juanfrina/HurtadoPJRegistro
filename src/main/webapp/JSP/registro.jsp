<%-- 
    Document   : registro
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Página de registro de nuevos usuarios
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
        <script>
            function confirmarRegistro() {
                var nombre = document.getElementById('nombre').value;
                var apellidos = document.getElementById('apellidos').value;
                var username = document.getElementById('username').value;
                
                if (confirm('¿Desea registrarse con los siguientes datos?\n\n' +
                    'Nombre: ' + nombre + '\n' +
                    'Apellidos: ' + apellidos + '\n' +
                    'Usuario: ' + username)) {
                    document.getElementById('formRegistro').submit();
                }
                return false;
            }
        </script>
    </head>
    <body>
        <main class="contenedor">
            <h1>Página de registro</h1>
            
            <%-- Mostrar mensaje de error si existe --%>
            <c:if test="${not empty error}">
                <p class="mensaje-error">${error}</p>
            </c:if>
            
            <form id="formRegistro" action="${contexto}/RegistroController" method="post">
                <input type="hidden" name="accion" value="registrar" />
                <p class="subtitulo">* Datos personales</p>
                
                <table class="tabla-formulario">
                    <tr>
                        <td><label for="nombre">Nombre</label></td>
                        <td>
                            <input type="text" id="nombre" name="nombre" 
                                   placeholder="Ej. Pedro"
                                   value="${usuario.nombre}"
                                   required/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="apellidos">Apellidos</label></td>
                        <td>
                            <input type="text" id="apellidos" name="apellidos" 
                                   placeholder="Ej. Sosa Hurtado"
                                   value="${usuario.apellidos}"
                                   required/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="genero">Genero</label></td>
                        <td>
                            <select id="genero" name="genero">
                                <option value="Mujer" ${usuario.genero == 'Mujer' ? 'selected' : ''}>Mujer</option>
                                <option value="Hombre" ${usuario.genero == 'Hombre' ? 'selected' : ''}>Hombre</option>
                                <option value="Otro" ${usuario.genero == 'Otro' ? 'selected' : ''}>Otro</option>
                            </select>
                        </td>
                    </tr>
                </table>
                
                <p class="subtitulo">* Datos de acceso</p>
                
                <table class="tabla-formulario">
                    <tr>
                        <td><label for="username">Username</label></td>
                        <td>
                            <input type="text" id="username" name="username" 
                                   placeholder="Ej. pedro"
                                   value="${usuario.username}" required/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="password">Contraseña</label></td>
                        <td>
                            <input type="password" id="password" name="password" required/>
                        </td>
                    </tr>
                </table>
                <div class="menu-opciones">
                    <button type="submit" class="enlace-boton" onclick="return confirmarRegistro();">Enviar</button>
                    <button type="reset" class="enlace-boton">Limpiar</button>
                </div>
            </form>
            <div class="menu-opciones">
                <form action="${contexto}/LoginController" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="cancelar" />
                    <button type="submit" class="enlace-boton">Cancelar</button>
                </form>
            </div>
        </main>
    </body>
</html>
