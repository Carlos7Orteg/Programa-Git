/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package culturatechbgcrud;

import java.sql.Connection;
import dao.EventoDAO;
import model.Evento;
import util.ConexionBD;

public class CulturaTechBgCRUD {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try (Connection conexion = ConexionBD.conectar()) {

            EventoDAO eventoDAO = new EventoDAO();

            for (Evento evento : eventoDAO.consultarTodas(conexion)) {

                System.out.println("ID: " + evento.getIdEvento());
                System.out.println("Título: " + evento.getTitulo());
                System.out.println("Descripción: " + evento.getDescripcion());
                System.out.println("Fecha: " + evento.getFechaHora());
                System.out.println("Costo: " + evento.getCosto());
                System.out.println("Estado: " + evento.getEstado());
                System.out.println("Categoría: " + evento.getIdCategoria());
                System.out.println("Lugar: " + evento.getIdLugar());
                System.out.println("-----------------------------");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
