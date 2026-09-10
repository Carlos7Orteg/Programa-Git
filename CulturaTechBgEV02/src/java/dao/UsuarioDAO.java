/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.security.MessageDigest;
import java.util.Base64;
import java.sql.ResultSet;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

/**
 *
 * @author Carlos
 */
public class UsuarioDAO {
    public void insertar(Connection conexion, Usuario usuario) throws SQLException {

        String sql = "INSERT INTO usuario "
                + "(nombres, apellidos, documento, fecha_nacimiento, correo, contraseña, rol) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, usuario.getNombres());
            sentencia.setString(2, usuario.getApellidos());
            sentencia.setString(3, usuario.getDocumento());
            sentencia.setDate(4, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            sentencia.setString(5, usuario.getCorreo());
            sentencia.setString(6, usuario.getContrasena());
            sentencia.setString(7, usuario.getRol());

            sentencia.executeUpdate();
        }
    }
    
    public Usuario buscarPorCorreo(Connection conexion, String correo) throws SQLException {
        
        String sql = "SELECT id_usuario, nombres, apellidos, documento, "
            + "fecha_nacimiento, correo, contraseña, rol "
            + "FROM usuario WHERE correo = ?";
        
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            
            sentencia.setString(1, correo);
            
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    
                    Usuario usuario = new Usuario();
                    
                    usuario.setIdUsuario(resultado.getInt("id_usuario"));
                    usuario.setNombres(resultado.getString("nombres"));
                    usuario.setApellidos(resultado.getString("apellidos"));
                    usuario.setDocumento(resultado.getString("documento"));
                    usuario.setFechaNacimiento(
                            resultado.getDate("fecha_nacimiento").toLocalDate()
                    );
                    
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setContrasena(resultado.getString("contraseña"));
                    usuario.setRol(resultado.getString("rol"));
                
                    return usuario;
                }
            }
        }

        return null;
    }
    

    public Usuario buscarPorId(Connection conexion, int idUsuario) throws SQLException {
        String sql = "SELECT id_usuario, nombres, apellidos, documento, "
                + "fecha_nacimiento, correo, contraseña, rol "
                + "FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, idUsuario);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("id_usuario"));
                    usuario.setNombres(resultado.getString("nombres"));
                    usuario.setApellidos(resultado.getString("apellidos"));
                    usuario.setDocumento(resultado.getString("documento"));
                    usuario.setFechaNacimiento(resultado.getDate("fecha_nacimiento").toLocalDate());
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setContrasena(resultado.getString("contraseña"));
                    usuario.setRol(resultado.getString("rol"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public List<Usuario> listarTodos(Connection conexion) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombres, apellidos, documento, "
                + "fecha_nacimiento, correo, rol FROM usuario ORDER BY id_usuario";
        try (PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(resultado.getInt("id_usuario"));
                usuario.setNombres(resultado.getString("nombres"));
                usuario.setApellidos(resultado.getString("apellidos"));
                usuario.setDocumento(resultado.getString("documento"));
                usuario.setFechaNacimiento(resultado.getDate("fecha_nacimiento").toLocalDate());
                usuario.setCorreo(resultado.getString("correo"));
                usuario.setRol(resultado.getString("rol"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public void actualizar(Connection conexion, Usuario usuario, boolean cambiarContrasena)
            throws SQLException {
        String sql;
        if (cambiarContrasena) {
            sql = "UPDATE usuario SET nombres=?, apellidos=?, documento=?, "
                    + "fecha_nacimiento=?, correo=?, contraseña=?, rol=? WHERE id_usuario=?";
        } else {
            sql = "UPDATE usuario SET nombres=?, apellidos=?, documento=?, "
                    + "fecha_nacimiento=?, correo=?, rol=? WHERE id_usuario=?";
        }
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            int i = 1;
            sentencia.setString(i++, usuario.getNombres());
            sentencia.setString(i++, usuario.getApellidos());
            sentencia.setString(i++, usuario.getDocumento());
            sentencia.setDate(i++, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            sentencia.setString(i++, usuario.getCorreo());
            if (cambiarContrasena) {
                sentencia.setString(i++, usuario.getContrasena());
            }
            sentencia.setString(i++, usuario.getRol());
            sentencia.setInt(i, usuario.getIdUsuario());
            sentencia.executeUpdate();
        }
    }

    public void actualizarPerfil(Connection conexion, Usuario usuario, boolean cambiarContrasena)
            throws SQLException {
        String sql;
        if (cambiarContrasena) {
            sql = "UPDATE usuario SET nombres=?, apellidos=?, documento=?, "
                    + "fecha_nacimiento=?, correo=?, contraseña=? WHERE id_usuario=?";
        } else {
            sql = "UPDATE usuario SET nombres=?, apellidos=?, documento=?, "
                    + "fecha_nacimiento=?, correo=? WHERE id_usuario=?";
        }
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            int i = 1;
            sentencia.setString(i++, usuario.getNombres());
            sentencia.setString(i++, usuario.getApellidos());
            sentencia.setString(i++, usuario.getDocumento());
            sentencia.setDate(i++, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            sentencia.setString(i++, usuario.getCorreo());
            if (cambiarContrasena) {
                sentencia.setString(i++, usuario.getContrasena());
            }
            sentencia.setInt(i, usuario.getIdUsuario());
            sentencia.executeUpdate();
        }
    }

    public void eliminar(Connection conexion, int idUsuario) throws SQLException {
        boolean autoCommit = conexion.getAutoCommit();
        conexion.setAutoCommit(false);
        try {
            String[] dependencias = {
                "DELETE FROM agenda_personal WHERE id_usuario = ?",
                "DELETE FROM favorito WHERE id_usuario = ?",
                "DELETE FROM notificacion WHERE id_usuario = ?"
            };
            for (String sql : dependencias) {
                try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
                    sentencia.setInt(1, idUsuario);
                    sentencia.executeUpdate();
                }
            }
            try (PreparedStatement sentencia = conexion.prepareStatement(
                    "DELETE FROM usuario WHERE id_usuario = ?")) {
                sentencia.setInt(1, idUsuario);
                sentencia.executeUpdate();
            }
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(autoCommit);
        }
    }

    public boolean verificarContrasena(String contrasena, String hashGuardado)
        throws Exception {
        
        if (hashGuardado == null || hashGuardado.isBlank()) {
            return false;
        }
        
        String[] partes = hashGuardado.split(":");
        
        if (partes.length != 3) {
            return false;
        }

        int iteraciones = Integer.parseInt(partes[0]);
        byte[] sal = Base64.getDecoder().decode(partes[1]);
        byte[] hashOriginal = Base64.getDecoder().decode(partes[2]);
        
        PBEKeySpec spec = new PBEKeySpec(
            contrasena.toCharArray(),
            sal,
            iteraciones,
            hashOriginal.length * 8
        );

        try {
            SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

                byte[] hashCalculado = factory.generateSecret(spec).getEncoded();

                return MessageDigest.isEqual(hashOriginal, hashCalculado);
        } finally {
            spec.clearPassword();
        }
    }  
}
