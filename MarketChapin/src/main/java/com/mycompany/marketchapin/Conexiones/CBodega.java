/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marketchapin.Conexiones;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose
 */
public class CBodega {

    private String idBodega;
    private String idSucursal;

    public CBodega(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void obtenerProductosPorSucursal(JTable productos, String codigoSucursal, ArrayList<String> prodEnBodega) {
        DefaultTableModel modelo = new DefaultTableModel();
        String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket";
        String username = "postgres";
        String password = "jose";

        modelo.addColumn("Producto");
        modelo.addColumn("Codigo");
        modelo.addColumn("CantidadDisponible");

        productos.setModel(modelo);

        // Sentencia SQL
        String sql = "SELECT prodG.Producto.Codigo, prodG.Producto.Nombre, invBodega.cant "
                + "FROM bodegaS.inventario_de_Producto_enBodega AS invBodega "
                + "JOIN inventarioG.Inventaro AS inventario ON invBodega.idInventarioB = inventario.idInventario "
                + "JOIN prodG.Producto ON inventario.idProducto = prodG.Producto.Codigo "
                + "WHERE invBodega.idBodega = (SELECT idBodega FROM bodegaS.Bodega WHERE idSucursal = ? LIMIT 1)";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Establecer el código de sucursal como parámetro
            preparedStatement.setString(1, codigoSucursal);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Procesar los resultados
                while (resultSet.next()) {
                    String codigo = resultSet.getString("codigo");
                    String nombre = resultSet.getString("nombre");
                    int cantidad = resultSet.getInt("cant");
                    prodEnBodega.add(codigo);
                    // Agregar fila al modelo
                    modelo.addRow(new Object[]{nombre, codigo, cantidad});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String obtenerBodegaPorSucursal(String codigoSucursal) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket";
        String username = "postgres";
        String password = "jose";

        // Sentencia SQL
        String sql = "SELECT idBodega FROM bodegaS.Bodega WHERE idSucursal = ? LIMIT 1";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Establecer el código de sucursal como parámetro
            preparedStatement.setString(1, codigoSucursal);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Verificar si hay resultados
                if (resultSet.next()) {
                    return resultSet.getString("idBodega");
                } else {
                    // No se encontró una bodega para la sucursal
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
public void aumentarCantidadEnBodega(String codigoBodega, int codigoProducto, int cantidadAumentar) {
    String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket";
    String username = "postgres";
    String password = "jose";

    // Sentencia SQL para aumentar la cantidad
    String sql = "UPDATE bodegaS.inventario_de_Producto_enBodega SET cant = cant + ? WHERE idBodega = ? AND idProducto = ?";

    try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        // Establecer la cantidad, el código de bodega y el código de producto como parámetros
        preparedStatement.setInt(1, cantidadAumentar);
        preparedStatement.setString(2, codigoBodega);
        preparedStatement.setInt(3, codigoProducto);

        // Ejecutar la actualización
        int filasAfectadas = preparedStatement.executeUpdate();

        // Verificar si se realizaron actualizaciones
        if (filasAfectadas > 0) {
            System.out.println("Se aumentó la cantidad en la bodega " + codigoBodega + " para el producto " + codigoProducto);
        } else {
            System.out.println("No se pudo aumentar la cantidad en la bodega " + codigoBodega + " para el producto " + codigoProducto);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}





}
