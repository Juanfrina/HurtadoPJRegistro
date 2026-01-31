<%-- 
    Document   : index
    Created on : 31 ene. 2026
    Author     : jfco1
    Descripción: Página de inicio que redirige al login
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>

<%-- Redirigir al controlador de login --%>
<c:redirect url="/LoginController"/>
