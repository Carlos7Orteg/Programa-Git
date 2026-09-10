/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import util.ConexionBD;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        if (correo == null || correo.isBlank()
                || contrasena == null || contrasena.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(
                    "{\"ok\":false,\"mensaje\":\"Correo y contraseña son obligatorios.\"}"
            );
            return;
        }

        try (Connection conexion = ConexionBD.conectar()) {

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Usuario usuario = usuarioDAO.buscarPorCorreo(
                    conexion,
                    correo.trim()
            );

            if (usuario == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(
                        "{\"ok\":false,\"mensaje\":\"Correo o contraseña incorrectos.\"}"
                );
                return;
            }

            boolean contraseñaCorrecta
                    = usuarioDAO.verificarContrasena(
                            contrasena,
                            usuario.getContrasena()
                    );

            if (!contraseñaCorrecta) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(
                        "{\"ok\":false,\"mensaje\":\"Correo o contraseña incorrectos.\"}"
                );
                return;
            }

            // Credenciales correctas: autenticar directamente la sesión.
            HttpSession session = request.getSession(true);
            request.changeSessionId();

            session.setAttribute(
                    "usuarioAutenticado",
                    true
            );

            session.setAttribute(
                    "usuarioId",
                    usuario.getIdUsuario()
            );

            session.setAttribute(
                    "usuarioCorreo",
                    usuario.getCorreo()
            );

            session.setAttribute(
                    "usuarioRol",
                    usuario.getRol()
            );

            session.setMaxInactiveInterval(30 * 60);

            response.setStatus(HttpServletResponse.SC_OK);

            String nombres = escapeJson(usuario.getNombres());
            String apellidos = escapeJson(usuario.getApellidos());
            String documento = escapeJson(usuario.getDocumento());
            String fechaNacimiento = usuario.getFechaNacimiento().toString();
            String correoUsuario = escapeJson(usuario.getCorreo());
            String rol = escapeJson(usuario.getRol());

            String json = "{"
                    + "\"ok\":true,"
                    + "\"usuario\":{"
                    + "\"idUsuario\":" + usuario.getIdUsuario() + ","
                    + "\"nombres\":\"" + nombres + "\","
                    + "\"apellidos\":\"" + apellidos + "\","
                    + "\"documento\":\"" + documento + "\","
                    + "\"fechaNacimiento\":\"" + fechaNacimiento + "\","
                    + "\"correo\":\"" + correoUsuario + "\","
                    + "\"rol\":\"" + rol + "\""
                    + "}"
                    + "}";

            response.getWriter().write(json);

        } catch (Exception e) {

            throw new ServletException(
                    "Error al validar las credenciales del usuario.",
                    e
            );
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
}
