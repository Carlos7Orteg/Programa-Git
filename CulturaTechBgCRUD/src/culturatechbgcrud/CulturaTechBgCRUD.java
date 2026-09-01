/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package culturatechbgcrud;

import java.sql.Connection;
import dao.EventoDAO;
import util.ConexionBD;

public class CulturaTechBgCRUD {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try (Connection conexion = ConexionBD.conectar()) {
            
            EventoDAO eventoDAO = new EventoDAO();

            eventoDAO.eliminar(conexion, 2);

            System.out.println("Evento eliminado correctamente.");
        
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    } 
}
