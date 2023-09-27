/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marketchapin.Conexiones;

import com.mycompany.marketchapin.frontEnd.Admin.Administrador;
import com.mycompany.marketchapin.frontEnd.Bodega;
import com.mycompany.marketchapin.frontEnd.Frame;
import com.mycompany.marketchapin.frontEnd.opcionCajero;
import com.mycompany.marketchapin.controladores.CambioFrame;
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
public class CLoginAdmin {

    private Frame principal;
    private String usuario;
    private String contra;

    public CLoginAdmin(Frame principal, String usuario, String contra) {
        this.principal = principal;
        this.usuario = usuario;
        this.contra = contra;
        conexion(usuario, contra);
    }
    
 private void conexion(String usuario, String contra) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket"; // Cambia la URL según tu configuración
        String username = "postgres";
        String password = "jose";

        String consultaSql = "SELECT * FROM admin.Administrador WHERE Usuario = ?";

        try {
            // Establecer la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(jdbcURL, username, password);

            // Crear una sentencia preparada para la consulta
            PreparedStatement preparedStatement = conexion.prepareStatement(consultaSql);
            preparedStatement.setString(1, usuario); // Parámetro de la consulta

            // Ejecutar la consulta
            ResultSet resultado = preparedStatement.executeQuery();

            // Procesar el resultado de la consulta
            /*
            INSERT INTO admin.Administrador (usuario,contrasene)
VALUES('admin','admin');*/
            if (resultado.next()) {
                String usuarioCajero = resultado.getString("usuario");
                String contraBD = resultado.getString("contrasena");
                // Mostrar la información del cajero1
                if (usuarioCajero.equals(usuario) && contraBD.equals(contra)) {
                    cambioFrame();
                    JOptionPane.showMessageDialog(null, " Iniciaste sesion como " + usuarioCajero);
                } else {
                    System.out.println("Olvidaste tu contrsenea?");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El usuario no existe");
            }

            // Cerrar recursos
            resultado.close();
            preparedStatement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cambioFrame() {
        CambioFrame nuevo = new CambioFrame(principal, new Administrador(principal));
    }
}
