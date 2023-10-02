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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose
 */
public class CBodega {

    private String idBodega;
    private String idSucursal;
    private String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket";
    private String username = "postgres";
    private String password = "jose";
    private Connection conexion;

    public CBodega(String idSucursal) {
        this.idSucursal = idSucursal;
        try {
            conexion = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(CBodega.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerProductosEnBodega(JTable productos, String codigoBodega, ArrayList<String> prodEnBodega) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Producto");
        modelo.addColumn("Codigo");
        modelo.addColumn("CantidadDisponible");
        prodEnBodega.clear();
        productos.setModel(modelo);

        // Sentencia SQL
        String sql = "SELECT prodG.Producto.Codigo, prodG.Producto.Nombre, invBodega.cant "
                + "FROM bodegaS.inventario_de_Producto_enBodega AS invBodega "
                + "JOIN prodG.Producto ON invBodega.idProducto = prodG.Producto.Codigo "
                + "WHERE invBodega.idBodega = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            // Establecer el código de bodega como parámetro
            preparedStatement.setString(1, codigoBodega);

            // Procesar los resultados
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String codigo = resultSet.getString("codigo");
                String nombre = resultSet.getString("nombre");
                int cantidad = resultSet.getInt("cant");
                prodEnBodega.add(codigo);
                // Agregar fila al modelo
                modelo.addRow(new Object[]{nombre, codigo, cantidad});
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            // Manejar la excepción según tus necesidades
            e.printStackTrace();
        }
    }

    public String obtenerBodegaPorSucursal(String codigoSucursal) {
        String retorno;
        // Sentencia SQL
        String sql = "SELECT idBodega FROM bodegaS.Bodega WHERE idSucursal = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            // Establecer el código de sucursal como parámetro
            preparedStatement.setString(1, codigoSucursal);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Verificar si preparedStatement.close();
            if (resultSet.next()) {
                retorno = resultSet.getString("idBodega");
            } else {
                // No se encontró una bodega para la sucursal
                retorno = null;
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            //e.printStackTrace();
            retorno = null;
        }

        return retorno;
    }

    public void aumentarCantidadEnBodega(String codigoBodega, int codigoProducto, int cantidadAumentar) {

        // Sentencia SQL para aumentar la cantidad
        String sql = "UPDATE bodegaS.inventario_de_Producto_enBodega SET cant = cant + ? WHERE idBodega = ? AND idProducto = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
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
            preparedStatement.close();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void insertarProductoEnBodega(String codigoSucursal, int idProducto, int cantidad) {
        // Obtener el ID de la bodega para la sucursal dada
        String idBodega = obtenerBodegaPorSucursal(codigoSucursal);
        System.out.println("idBodega: "+idBodega);
        System.out.println("idProcudto:" +idProducto);
        System.out.println("cantida: "+cantidad);
        // Verificar si se encontró una bodega para la sucursal
        if (idBodega != null) {
            // Sentencia SQL para la inserción
            String sql = "INSERT INTO bodegaS.inventario_de_Producto_enBodega (idBodega, idProducto, cant) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                // Establecer los parámetros
                preparedStatement.setString(1, idBodega);
                preparedStatement.setInt(2, idProducto);
                preparedStatement.setInt(3, cantidad);

                // Ejecutar la inserción
                int filasAfectadas = preparedStatement.executeUpdate();

                // Verificar si se realizaron inserciones
                if (filasAfectadas > 0) {
                    System.out.println("Se insertó el producto en la bodega " + idBodega + " con ID " + idProducto);
                } else {
                    System.out.println("No se pudo insertar el producto en la bodega " + idBodega + " con ID " + idProducto);
                }
                preparedStatement.close();
            } catch (SQLException e) {
                // Manejo de excepciones
                Logger.getLogger(CBodega.class.getName()).log(Level.SEVERE, "Error al insertar producto en la bodega", e);
            }
        } else {
            // No se encontró una bodega para la sucursal
            System.out.println("No se encontró una bodega para la sucursal " + codigoSucursal);
        }
    }

    public int obtenerCantidadFilasPorIdBodega(String idBodega) {
        // Sentencia SQL
        String sql = "SELECT COUNT(*) AS cantidadFilas FROM bodegaS.inventario_de_Producto_enBodega WHERE idBodega = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            // Establecer el idBodega como parámetro
            preparedStatement.setString(1, idBodega);

            // Ejecutar la consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            // Procesar los resultados
            if (resultSet.next()) {
                int cantidadFilas = resultSet.getInt("cantidadFilas") + 1;
                return cantidadFilas;
            } else {
                System.out.println("No se encontraron filas para la bodega: " + idBodega);
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int obtenerCantidadProductosPorSucursal(String codigoSucursal) {
        // Sentencia SQL
        String sql = "SELECT cantProducto FROM admin.Sucursal WHERE Codigo = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            // Establecer el código de sucursal como parámetro
            preparedStatement.setString(1, codigoSucursal);

            // Ejecutar la consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            // Procesar los resultados
            if (resultSet.next()) {
                int cantidadProductos = resultSet.getInt("cantProducto");
                System.out.println("Cantidad de productos para la sucursal " + codigoSucursal + ": " + cantidadProductos);
                return cantidadProductos;
            } else {
                System.out.println("No se encontró la sucursal con el código: " + codigoSucursal);
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(CBodega.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
