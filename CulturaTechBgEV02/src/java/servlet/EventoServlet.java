/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;
// Autoría: Carlos Rodrigo Ortegón | ADSO-3235898 CulturaTech Bogotá | 2026
import dao.EventoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import model.Evento;
import util.ConexionBD;

@WebServlet(name = "EventoServlet", urlPatterns = {"/EventoServlet"})
public class EventoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        try (Connection conexion = ConexionBD.conectar()) {
            
            EventoDAO eventoDAO = new EventoDAO();
            var eventos = eventoDAO.consultarTodas(conexion);
            
            if ("json".equalsIgnoreCase(request.getParameter("format"))) {
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                StringBuilder json = new StringBuilder("[");
                boolean primero = true;
                
                for (Evento evento : eventos) {
                    
                    if (!primero) {
                        json.append(",");
                    }
                    
                    json.append("{")
                    .append("\"id\":").append(evento.getIdEvento()).append(",")
                    .append("\"title\":\"").append(escapeJson(evento.getTitulo())).append("\",")
                    .append("\"description\":\"").append(escapeJson(evento.getDescripcion())).append("\",")
                    .append("\"date\":\"").append(evento.getFechaHora()).append("\",")
                    .append("\"cost\":").append(evento.getCosto()).append(",")
                    .append("\"imageUrl\":\"").append(escapeJson(evento.getImagen())).append("\",")
                    .append("\"category\":\"").append(escapeJson(evento.getNombreCategoria())).append("\",")
                    .append("\"location\":\"").append(escapeJson(evento.getNombreLugar())).append("\",")
                    .append("\"address\":\"").append(escapeJson(evento.getDireccion())).append("\",")
                    .append("\"locality\":\"").append(escapeJson(evento.getLocalidad())).append("\"")
                    .append("}");
                    
                    primero = false;
                }

                json.append("]");

                response.getWriter().write(json.toString());
            
            } else {
                
                request.setAttribute("eventos", eventos);

                request.getRequestDispatcher("/eventos.jsp")
                        .forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error al consultar los eventos.", e);
        }
    }
    
    private String escapeJson(String texto) {
        
        if (texto == null) {
            return "";
        }
        
        return texto
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try (Connection conexion = ConexionBD.conectar()) {

            Evento evento = new Evento();

            evento.setTitulo(request.getParameter("titulo"));
            evento.setDescripcion(request.getParameter("descripcion"));
            evento.setFechaHora(
                    LocalDateTime.parse(request.getParameter("fechaHora")));
            evento.setCosto(
                    Double.parseDouble(request.getParameter("costo")));
            evento.setImagen(request.getParameter("imagen"));
            evento.setEstado("ACTIVO");
            evento.setIdCategoria(
                    Integer.parseInt(request.getParameter("idCategoria")));
            evento.setIdLugar(
                    Integer.parseInt(request.getParameter("idLugar")));

            EventoDAO eventoDAO = new EventoDAO();
            eventoDAO.insertar(conexion, evento);

            response.sendRedirect("EventoServlet");

        } catch (Exception e) {
            throw new ServletException("Error al registrar el evento.", e);
        }
    }
}