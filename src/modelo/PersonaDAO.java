package modelo;

import conexion.Conexion;
import java.sql.*;
import java.util.*;

public class PersonaDAO {

    public List<PersonaVO> consultarPersonas() {
        List<PersonaVO> lista = new ArrayList<>();
        String sql = "SELECT documento, nombre, direccion, telefono FROM empleado";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                PersonaVO persona = new PersonaVO(
                    rs.getInt("documento"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono")
                );
                lista.add(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public String registrarPersona(String nombre, int documento, String direccion, String telefono) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre no puede estar vacío.";
        }

        String sql = "INSERT INTO empleado (documento, nombre, direccion, telefono) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, documento);
            ps.setString(2, nombre);
            ps.setString(3, direccion);
            ps.setString(4, telefono);
            ps.executeUpdate();
            return "Registro exitoso.";

        } catch (SQLException e) {
            return "Error al registrar: " + e.getMessage();
        }
    }

    public PersonaVO consultarPorDocumento(int documento) {
        String sql = "SELECT documento, nombre, direccion, telefono FROM empleado WHERE documento = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, documento);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PersonaVO(
                    rs.getInt("documento"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
