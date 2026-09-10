<%-- 
    Document   : eventos
    Created on : 2 sept 2026, 5:02:50 p.m.
    Author     : Carlos
--%>

<%@page import="java.util.List"%>
<%@page import="model.Evento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>CulturaTech Bogotá - Eventos</title>
    </head>

    <body>

        <h1>Registro de eventos</h1>

        <h2>Nuevo evento</h2>

        <form action="EventoServlet" method="post">

            <label>Título:</label>
            <input type="text" name="titulo" required>
            <br><br>

            <label>Descripción:</label>
            <input type="text" name="descripcion">
            <br><br>

            <label>Fecha y hora:</label>
            <input type="datetime-local" name="fechaHora" required>
            <br><br>

            <label>Costo:</label>
            <input type="number" name="costo" step="0.01" value="0">
            <br><br>

            <label>Imagen:</label>
            <input type="text" name="imagen">
            <br><br>

            <label>ID Categoría:</label>
            <input type="number" name="idCategoria" required>
            <br><br>

            <label>ID Lugar:</label>
            <input type="number" name="idLugar" required>
            <br><br>

            <button type="submit">Guardar evento</button>

        </form>

        <hr>

        <h2>Eventos registrados</h2>

        <%
            List<Evento> eventos = (List<Evento>) request.getAttribute("eventos");

            if (eventos != null && !eventos.isEmpty()) {
                for (Evento evento : eventos) {
        %>

                    <div>
                        <p><strong>ID:</strong> <%= evento.getIdEvento() %></p>
                        <p><strong>Título:</strong> <%= evento.getTitulo() %></p>
                        <p><strong>Descripción:</strong> <%= evento.getDescripcion() %></p>
                        <p><strong>Fecha:</strong> <%= evento.getFechaHora() %></p>
                        <p><strong>Costo:</strong> <%= evento.getCosto() %></p>
                        <p><strong>Estado:</strong> <%= evento.getEstado() %></p>
                        <p><strong>Categoría:</strong> <%= evento.getIdCategoria() %></p>
                        <p><strong>Lugar:</strong> <%= evento.getIdLugar() %></p>
                        <hr>
                    </div>

        <%
                }
            } else {
        %>

                <p>No hay eventos registrados.</p>

        <%
            }
        %>

    </body>
</html>