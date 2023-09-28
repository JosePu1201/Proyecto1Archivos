/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.marketchapin;

import com.mycompany.marketchapin.frontEnd.Bodega;
import com.mycompany.marketchapin.frontEnd.Frame;
import com.mycompany.marketchapin.frontEnd.Login;
import com.mycompany.marketchapin.controladores.CambioFrame;
import java.awt.Dimension;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author jose
 */
public class MarketChapin {

    public static void main(String[] args) {

        Frame primero = new Frame("Market-Chapin");
        Login panel = new Login(primero);
        CambioFrame cambio = new CambioFrame(primero, panel);
//        JFrame ventana = new JFrame();
//        Login panel = new Login();
//        ventana.setTitle("Market-Chapin");
//        Dimension panelSize = panel.getPreferredSize();
//        ventana.setSize(panelSize);
//        ventana.setResizable(false);
//        ventana.setLocationRelativeTo(null);
//        ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
//    //        ventana.setLayout(new FlowLayout());
//        ventana.add(panel);
//        ventana.setVisible(true);
////        conexion();
    }

    public static void conexion() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket"; // Cambia la URL según tu configuración
        String username = "postgres";
        String password = "jose";

        try {
            // Establece la conexión
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            // Crea una declaración SQL
            Statement statement = (Statement) connection.createStatement();

            // Ejecuta la consulta SQL
            String sqlQuery = "SELECT * FROM prodG.Producto";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Procesa y muestra los resultados
            while (resultSet.next()) {
                String nombre = resultSet.getString("Nombre");
                String codigo = resultSet.getString("Codigo");
                double precio = resultSet.getDouble("Precio");
                System.out.println("Nombre: " + nombre + ", Código: " + codigo + ", Precio: " + precio);
            }

            // Cierra la conexión y recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
