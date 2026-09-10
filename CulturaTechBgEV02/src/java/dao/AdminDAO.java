package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import model.Lugar;

public class AdminDAO {

    public List<Categoria> listarCategorias(Connection c) throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre_categoria, descripcion FROM categoria ORDER BY id_categoria";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria x = new Categoria();
                x.setIdCategoria(rs.getInt("id_categoria"));
                x.setNombreCategoria(rs.getString("nombre_categoria"));
                x.setDescripcion(rs.getString("descripcion"));
                lista.add(x);
            }
        }
        return lista;
    }

    public void insertarCategoria(Connection c, Categoria x) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO categoria(nombre_categoria, descripcion) VALUES(?,?)")) {
            ps.setString(1, x.getNombreCategoria());
            ps.setString(2, x.getDescripcion());
            ps.executeUpdate();
        }
    }

    public void actualizarCategoria(Connection c, Categoria x) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("UPDATE categoria SET nombre_categoria=?, descripcion=? WHERE id_categoria=?")) {
            ps.setString(1, x.getNombreCategoria());
            ps.setString(2, x.getDescripcion());
            ps.setInt(3, x.getIdCategoria());
            ps.executeUpdate();
        }
    }

    public void eliminarCategoria(Connection c, int id) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("DELETE FROM categoria WHERE id_categoria=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Lugar> listarLugares(Connection c) throws SQLException {
        List<Lugar> lista = new ArrayList<>();
        String sql = "SELECT id_lugar, nombre_lugar, direccion, localidad, latitud, longitud, telefono, pagina_web FROM lugar ORDER BY id_lugar";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Lugar x = new Lugar();
                x.setIdLugar(rs.getInt("id_lugar"));
                x.setNombreLugar(rs.getString("nombre_lugar"));
                x.setDireccion(rs.getString("direccion"));
                x.setLocalidad(rs.getString("localidad"));

                Number latitud = (Number) rs.getObject("latitud");
                Number longitud = (Number) rs.getObject("longitud");

                x.setLatitud(latitud != null ? latitud.doubleValue() : null);
                x.setLongitud(longitud != null ? longitud.doubleValue() : null);

                x.setTelefono(rs.getString("telefono"));
                x.setPaginaWeb(rs.getString("pagina_web"));
                lista.add(x);
            }
        }
        return lista;
    }

    public void insertarLugar(Connection c, Lugar x) throws SQLException {
        String sql = "INSERT INTO lugar(nombre_lugar,direccion,localidad,latitud,longitud,telefono,pagina_web) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            setLugar(ps, x);
            ps.executeUpdate();
        }
    }

    public void actualizarLugar(Connection c, Lugar x) throws SQLException {
        String sql = "UPDATE lugar SET nombre_lugar=?,direccion=?,localidad=?,latitud=?,longitud=?,telefono=?,pagina_web=? WHERE id_lugar=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            setLugar(ps, x);
            ps.setInt(8, x.getIdLugar());
            ps.executeUpdate();
        }
    }

    private void setLugar(PreparedStatement ps, Lugar x) throws SQLException {
        ps.setString(1, x.getNombreLugar());
        ps.setString(2, x.getDireccion());
        ps.setString(3, x.getLocalidad());
        if (x.getLatitud() == null) {
            ps.setNull(4, Types.DECIMAL);
        } else {
            ps.setDouble(4, x.getLatitud());
        }
        if (x.getLongitud() == null) {
            ps.setNull(5, Types.DECIMAL);
        } else {
            ps.setDouble(5, x.getLongitud());
        }
        ps.setString(6, x.getTelefono());
        ps.setString(7, x.getPaginaWeb());
    }

    public void eliminarLugar(Connection c, int id) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("DELETE FROM lugar WHERE id_lugar=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
