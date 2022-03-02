<%-- 
    Document   : listarArticulos
    Created on : 23/02/2022, 09:59:27 AM
    Author     : SENA
--%>

<%@taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.4/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.4/js/dataTables.bootstrap5.min.js"></script>
        <title>Listado de Artículos</title>
        <script>
            $(document).ready(function(){
                $("#table").DataTable({
                    "info": true,
                    "paging": true,
                    "buttons": true,
                    "paginate": true,
                    "searching": true,
                    "language": {
                        url: "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Spanish.json"
                    }
                });
            })
        </script>
    </head>
    <%@include file="header.jsp" %>
        <br>
        <h1 style="text-align: center;">Listado de Artículos</h1>
        <div class="pane panel-primary" style="margin: auto; width: 60%;">
            <div>
                <a href="addArticulo.htm" class="btn btn-primary">Agregar Artículo</a>
            </div>
            <br>
            <table class="table table-hover" id="table">
                <thead>
                    <tr>
                        <th>Título</th>
                        <th>Descripción</th>
                        <th>Precio</th>
                        <th>Foto</th> 
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${articulo}" var="a">
                    <tr>
                        <td><c:out value="${a.titulo}"/></td>
                        <td><c:out value="${a.descripcion}"/></td>
                        <td><c:out value="${a.precio}"/></td>
                        <td><c:out value="${a.foto}"/></td>
                        <td>
                            <a href="updateArticulo.htm?id=${a.id_articulo}" class="btn btn-warning">Modificar</a>
                            <a href="deleteArticulo.htm?id=${a.id_articulo}" class="btn btn-danger">Eliminar</a>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>
            <a href="formArticulo.htm" class="btn btn-secondary">Regresar </a>            
        </div>
    </body>
</html>
