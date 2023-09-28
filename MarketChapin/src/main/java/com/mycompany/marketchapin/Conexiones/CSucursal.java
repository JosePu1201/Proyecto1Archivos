/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marketchapin.Conexiones;

import static com.mycompany.marketchapin.MarketChapin.conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author jose
 */
public class CSucursal {

    private JComboBox combo;
    String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket"; // Cambia la URL según tu configuración
    String username = "postgres";
    String password = "jose";

    public CSucursal(JComboBox combo) {
        this.combo = combo;
        conexion();
    }

    private void conexion() {

        String consultaSql = "SELECT * FROM admin.Sucursal";

        try {
            // Establecer la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(jdbcURL, username, password);

            // Crear una sentencia preparada para la consulta
            Statement statement = conexion.createStatement();

            // Ejecutar la consulta
            ResultSet resultado = statement.executeQuery(consultaSql);
            while (resultado.next()) {
                String sucursalS = resultado.getString("Codigo");
                combo.addItem(sucursalS);
            }

            // Cerrar recursos
            resultado.close();
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeEmpleado(String codigoEmpleado) {
        boolean bandera = false;
        // Consulta SQL para verificar si el empleado existe
        try {
            Connection conexion = DriverManager.getConnection(jdbcURL, username, password);
            String consulta = "SELECT COUNT(*) FROM admin.Empleado WHERE Usuario = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, codigoEmpleado);

            ResultSet resultado = statement.executeQuery();

            // Comprobar si el empleado existe
            if (resultado.next()) {
                int cantidadEmpleados = resultado.getInt(1);
                if (cantidadEmpleados > 0) {
                    bandera = true;
                } else {

                }
            }

            // Cerrar la conexión y recursos
            resultado.close();
            statement.close();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bandera;
    }

    public boolean agregarEmpleado(String usuario, String contraseña, String puesto, String codigoSucursal) {
        boolean exito = false;
        try {
            // Establecer la conexión a la base de datos
            Connection conexion = DriverManager.getConnection(jdbcURL, username, password);

            // Consulta SQL para insertar un nuevo empleado
            String consulta = "INSERT INTO admin.Empleado (Usuario, Contrasena, Puesto, codigoSucursal) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, usuario);
            statement.setString(2, contraseña);
            statement.setString(3, puesto);
            statement.setString(4, codigoSucursal);

            // Ejecutar la inserción
            int filasInsertadas = statement.executeUpdate();

            // Cerrar la conexión y recursos
            statement.close();
            conexion.close();

            // Comprobar si la inserción fue exitosa
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Empleado creado de fomra correcta");
                exito = true;
            } else {
                JOptionPane.showMessageDialog(null, "Empleado creado de fomra correcta");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exito;
    }
}
