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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import model.Usuario;
import util.ConexionBD;

@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

    private static final int ITERACIONES = 120000;
    private static final int LONGITUD_SAL = 16;
    private static final int LONGITUD_HASH = 256;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String documento = request.getParameter("documento");
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        if (nombres == null || nombres.isBlank()
                || apellidos == null || apellidos.isBlank()
                || documento == null || documento.isBlank()
                || fechaNacimiento == null || fechaNacimiento.isBlank()
                || correo == null || correo.isBlank()
                || contrasena == null || contrasena.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(
                    "{\"ok\":false,\"mensaje\":\"Todos los campos son obligatorios.\"}"
            );
            return;
        }

        try {
            Usuario usuario = new Usuario();

            usuario.setNombres(nombres.trim());
            usuario.setApellidos(apellidos.trim());
            usuario.setDocumento(documento.trim());
            usuario.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
            usuario.setCorreo(correo.trim());
            usuario.setContrasena(hashPassword(contrasena));
            usuario.setRol("USER");

            try (Connection conexion = ConexionBD.conectar()) {

                UsuarioDAO usuarioDAO = new UsuarioDAO();

                usuarioDAO.insertar(conexion, usuario);

                // Recuperamos el usuario recién creado
                // para obtener el ID generado por MySQL.
                Usuario registrado = usuarioDAO.buscarPorCorreo(
                   conexion,
                   usuario.getCorreo()
                );

                if (registrado == null) {
                    throw new ServletException(
                        "El usuario fue insertado, pero no pudo ser recuperado."
                    );
                }

                // Creamos la sesión autenticada.
                HttpSession session = request.getSession(true);

                request.changeSessionId();

                session.setAttribute(
                    "usuarioAutenticado",
                     true
                );

                session.setAttribute(
                    "usuarioId",
                    registrado.getIdUsuario()
                );

                session.setAttribute(
                   "usuarioCorreo",
                   registrado.getCorreo()
                );

                session.setAttribute(
                   "usuarioRol",
                   registrado.getRol()
                );

                session.setMaxInactiveInterval(30 * 60);

                // Devolvemos los datos mínimos que necesita otp.js.
                String json = "{"
                    + "\"ok\":true,"
                    + "\"mensaje\":\"Usuario registrado correctamente.\","
                    + "\"usuario\":{"
                    + "\"idUsuario\":" + registrado.getIdUsuario() + ","
                    + "\"nombres\":\"" + escapeJson(registrado.getNombres()) + "\","
                    + "\"apellidos\":\"" + escapeJson(registrado.getApellidos()) + "\","
                    + "\"documento\":\"" + escapeJson(registrado.getDocumento()) + "\","
                    + "\"fechaNacimiento\":\"" + registrado.getFechaNacimiento() + "\","
                    + "\"correo\":\"" + escapeJson(registrado.getCorreo()) + "\","
                    + "\"rol\":\"" + escapeJson(registrado.getRol()) + "\""
                    + "}"
                    + "}";

                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write(json);         
            }

        } catch (SQLIntegrityConstraintViolationException e) {

            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write(
                    "{\"ok\":false,\"mensaje\":\"El documento o correo ya está registrado.\"}"
            );

        } catch (Exception e) {

            throw new ServletException("Error al registrar el usuario.", e);
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

    private String hashPassword(String password)
            throws Exception {

        byte[] salt = new byte[LONGITUD_SAL];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERACIONES,
                LONGITUD_HASH
        );

        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return ITERACIONES
                    + ":"
                    + Base64.getEncoder().encodeToString(salt)
                    + ":"
                    + Base64.getEncoder().encodeToString(hash);

        } finally {
            spec.clearPassword();
        }
    }
}
