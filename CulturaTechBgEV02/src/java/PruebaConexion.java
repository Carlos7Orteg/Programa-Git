/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package util;

import java.sql.Connection;

public class PruebaConexion {

    public static void main(String[] args) {

        try (Connection conexion = ConexionBD.conectar()) {

            System.out.println("CONEXION EXITOSA");
            System.out.println(
                    "Base de datos: "
                    + conexion.getCatalog()
            );

        } catch (Exception e) {

            System.out.println("ERROR DE CONEXION");
            System.out.println("Tipo: " + e.getClass().getName());
            System.out.println("Mensaje: " + e.getMessage());

            e.printStackTrace();
        }
    }
}