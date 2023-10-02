/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marketchapin.Conexiones;

import com.mycompany.marketchapin.frontEnd.Bodega;
import com.mycompany.marketchapin.frontEnd.Frame;
import com.mycompany.marketchapin.frontEnd.opcionCajero;
import com.mycompany.marketchapin.controladores.CambioFrame;
import com.mycompany.marketchapin.frontEnd.Inventario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jose
 */
public class CLogin {

    private Frame principal;
    private String usuario;
    private String contra;

    public CLogin(Frame principal, String usaurio, String contra) {
        this.principal = principal;
        this.usuario = usaurio;
        this.contra = contra;
        conexion(usuario, contra);
    }

    private void conexion(String usuario, String contra) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket"; // Cambia la URL según tu configuración
        String username = "postgres";
        String password = "jose";

        String consultaSql = "SELECT * FROM admin.Empleado WHERE Usuario = ?";

        try {
            // Establecer la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(jdbcURL, username, password);

            // Crear una sentencia preparada para la consulta
            PreparedStatement preparedStatement = conexion.prepareStatement(consultaSql);
            preparedStatement.setString(1, usuario); // Parámetro de la consulta

            // Ejecutar la consulta
            ResultSet resultado = preparedStatement.executeQuery();

            // Procesar el resultado de la consulta
            if (resultado.next()) {
                String usuarioCajero = resultado.getString("Usuario");
                String puesto = resultado.getString("Puesto");
                String codigoSucursal = resultado.getString("codigoSucursal");
                String contraBD = resultado.getString("Contrasena");
                // Mostrar la información del cajero1
                if (usuarioCajero.equals(usuario) && contraBD.equals(contra)) {
                    cambioFrame(puesto, usuarioCajero, codigoSucursal);
                    JOptionPane.showMessageDialog(null, " Iniciaste sesion como " + usuarioCajero + " en la sucursal " + codigoSucursal);
                } else {
                    System.out.println("Olvidaste tu contrsenea?");
                }
            } else {
                System.out.println("No se encontró al usuario en la base de datos.");
            }

            // Cerrar recursos
            resultado.close();
            preparedStatement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    *Se va a una vista especifica segun su puesto de trabajo
    */
    private void cambioFrame(String puesto, String usuario, String sucursal) {
        if (puesto.equals("Cajero")) {
            CambioFrame nuevo = new CambioFrame(principal, new opcionCajero(principal, usuario, sucursal));
        } else if (puesto.equals("Bodega")) {
            CambioFrame nuevo = new CambioFrame(principal, new Bodega(principal, usuario, sucursal));
        } else if (puesto.equals("Inventario")) {
            CambioFrame nuevo = new CambioFrame(principal, new Inventario(sucursal, usuario, principal));
        } else {
            System.out.println("Puesto no encontrado");
        }
    }
}
