/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Evento;

public class EventoDAO {
    
    public void insertar(Connection conexion, Evento evento) throws SQLException {

        String sql = "INSERT INTO evento "
                + "(titulo, descripcion, fecha_hora, costo, imagen, estado, id_categoria, id_lugar) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, evento.getTitulo());
            sentencia.setString(2, evento.getDescripcion());
            sentencia.setTimestamp(3,
                    java.sql.Timestamp.valueOf(evento.getFechaHora()));
            sentencia.setDouble(4, evento.getCosto());
            sentencia.setString(5, evento.getImagen());
            sentencia.setString(6, evento.getEstado());
            sentencia.setInt(7, evento.getIdCategoria());
            sentencia.setInt(8, evento.getIdLugar());

            sentencia.executeUpdate();
        }
    }
    
    public List<Evento> consultarTodas(Connection conexion) throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        
        String sql = "SELECT id_evento, titulo, descripcion, fecha_hora, "
            + "costo, imagen, estado, id_categoria, id_lugar "
            + "FROM evento";
        
        try (PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery()) {
            
            while (resultado.next()) {
                
                Evento evento = new Evento();

                evento.setIdEvento(resultado.getInt("id_evento"));
                evento.setTitulo(resultado.getString("titulo"));
                evento.setDescripcion(resultado.getString("descripcion"));
                evento.setFechaHora(resultado.getTimestamp("fecha_hora").toLocalDateTime());
                evento.setCosto(resultado.getDouble("costo"));
                evento.setImagen(resultado.getString("imagen"));
                evento.setEstado(resultado.getString("estado"));
                evento.setIdCategoria(resultado.getInt("id_categoria"));
                evento.setIdLugar(resultado.getInt("id_lugar"));

                eventos.add(evento);
            }
        }
        
        return eventos;
    }
    
    public void actualizar(Connection conexion, Evento evento) throws SQLException {
        
        String sql = "UPDATE evento SET "
            + "titulo = ?, descripcion = ?, fecha_hora = ?, "
            + "costo = ?, imagen = ?, estado = ?, "
            + "id_categoria = ?, id_lugar = ? "
            + "WHERE id_evento = ?";
        
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            
            sentencia.setString(1, evento.getTitulo());
            sentencia.setString(2, evento.getDescripcion());
            sentencia.setTimestamp(3,java.sql.Timestamp.valueOf(evento.getFechaHora()));
            sentencia.setDouble(4, evento.getCosto());
            sentencia.setString(5, evento.getImagen());
            sentencia.setString(6, evento.getEstado());
            sentencia.setInt(7, evento.getIdCategoria());
            sentencia.setInt(8, evento.getIdLugar());
            sentencia.setInt(9, evento.getIdEvento());

           sentencia.executeUpdate();
        }
    }
    
    public void eliminar(Connection conexion, int idEvento) throws SQLException {
        
        String sql = "DELETE FROM evento WHERE id_evento = ?";
        
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            
            sentencia.setInt(1, idEvento);

            sentencia.executeUpdate();
        }
    }
}
