package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/culturatechbg";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "TU_CONTRASENA_LOCAL";
    
    public static Connection conectar() throws SQLException {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new SQLException("No se pudo cargar el driver de MySQL.", e);
    }
        
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}