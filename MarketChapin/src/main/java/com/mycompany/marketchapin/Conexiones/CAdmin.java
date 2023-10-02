/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marketchapin.Conexiones;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jose
 */
public class CAdmin {

    private String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket";
    private String username = "postgres";
    private String password = "jose";
    private Connection conexion;

    public CAdmin() {
        try {
            conexion = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(CBodega.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean obtenerDescuentoPorTarjeta(String nit, String numeroTarjeta) {
        // Sentencia SQL para obtener información de descuento por tarjeta
        String sql = "SELECT Tipo, Descuento FROM descuentos.Tarjeta WHERE Nit_Cliente = ? AND Numero_Tarjeta = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, nit);
            preparedStatement.setString(2, numeroTarjeta);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // La tarjeta existe, obtener el tipo y el descuento
                resultSet.getString("Tipo");
                resultSet.getInt("Descuento");
                return true;
            } else {
                // La tarjeta no existe
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
            return false;
        }
    }

    public BigDecimal obtenerGastosReset(String nit) {
        // Sentencia SQL para obtener los GastosReset de un cliente
        String sql = "SELECT GastosReset FROM clientes.Cliente WHERE Nit = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, nit);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("GastosReset");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }

        return BigDecimal.ZERO; // Retornar BigDecimal.ZERO en caso de error o si no se encuentra el cliente
    }

    public String obtenerTipoPorTarjeta(String numeroTarjeta) {
        // Sentencia SQL para obtener el tipo de tarjeta por número de tarjeta
        String sql = "SELECT Tipo FROM descuentos.Tarjeta WHERE Numero_Tarjeta = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, numeroTarjeta);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // La tarjeta existe, obtener el tipo
                String tipo = resultSet.getString("Tipo");

                System.out.println("Tipo: " + tipo);

                return tipo;
            } else {
                // La tarjeta no existe
                System.out.println("Tarjeta no encontrada.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
            return null;
        }
    }

    public void actualizarDatosTarjeta(String numeroTarjeta, String nuevoTipo, int nuevoDescuento) {
        // Sentencia SQL para actualizar los datos de una tarjeta por su número
        String sql = "UPDATE descuentos.Tarjeta SET Tipo = ?, Descuento = ? WHERE Numero_Tarjeta = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, nuevoTipo);
            preparedStatement.setInt(2, nuevoDescuento);
            preparedStatement.setString(3, numeroTarjeta);

            int filasActualizadas = preparedStatement.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Datos de la tarjeta actualizados con éxito");

            } else {
                System.out.println("No se encontró la tarjeta con el número: " + numeroTarjeta);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }

    public boolean nitAsociadoATarjeta(String nit) {
        // Sentencia SQL para verificar la asociación entre NIT y tarjeta
        String sql = "SELECT COUNT(*) AS count FROM descuentos.Tarjeta WHERE Nit_Cliente = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, nit);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // Si count es mayor que 0, el NIT está asociado a una tarjeta
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }

        return false; // Retorna false en caso de error
    }

    public boolean insertarTarjeta(String numeroTarjeta, String nitCliente, String nombre, int descuento, int puntos, String tipo) {
        // Sentencia SQL para insertar una nueva tarjeta
        String sql = "INSERT INTO descuentos.Tarjeta (Numero_Tarjeta, Nit_Cliente, Nombre, Descuento, Puntos, Tipo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, numeroTarjeta);
            preparedStatement.setString(2, nitCliente);
            preparedStatement.setString(3, nombre);
            preparedStatement.setInt(4, descuento);
            preparedStatement.setInt(5, puntos);
            preparedStatement.setString(6, tipo);

            // Ejecutar la consulta de inserción
            int filasAfectadas = preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tarjeta creada con exito"
                    + "\nTipo: Comun"
                    + "\nNumero de tarjeta: " + numeroTarjeta + ""
                    + "\nNit Cliente: " + nitCliente);
            // Verificar si la inserción fue exitosa (una fila afectada indica éxito)
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
            return false;
        }
    }

    public Map<String, Integer> obtenerTopProductosMasVendidos() {
        Map<String, Integer> topProductos = new LinkedHashMap<>();

        String sql = "SELECT p.Nombre, SUM(lp.cantidadVendiad) AS TotalVendido "
                + "FROM factura.ListaProd lp "
                + "JOIN prodG.Producto p ON lp.codigoProducto = p.Codigo "
                + "GROUP BY p.Nombre "
                + "ORDER BY TotalVendido DESC "
                + "LIMIT 10";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String nombreProducto = resultSet.getString("Nombre");
                int cantidadVendida = resultSet.getInt("TotalVendido");
                topProductos.put(nombreProducto, cantidadVendida);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }

        return topProductos;
    }
public Map<String, BigDecimal> obtenerTopClientes() {
    // Sentencia SQL para obtener el top 10 de clientes por gastos en tienda
    String sql = "SELECT Nit, Nombre, GastosEnTienda FROM clientes.Cliente ORDER BY GastosEnTienda DESC LIMIT 10";

    Map<String, BigDecimal> topClientes = new LinkedHashMap<>();

    try {
        PreparedStatement preparedStatement = conexion.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        int posicion = 1;
        while (resultSet.next()) {
            String nombre = resultSet.getString("Nombre");
            BigDecimal gastosEnTienda = resultSet.getBigDecimal("GastosEnTienda");

            String resultado = String.format("%s: %s ", nombre, gastosEnTienda);
            topClientes.put(resultado, gastosEnTienda);

            posicion++;
        }

        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        // Manejar la excepción según tus necesidades
    }

    return topClientes;
}


public Map<String, Double> obtenerTop3SucursalesConIngresos() {
    Map<String, Double> topSucursales = new LinkedHashMap<>(); // Usamos LinkedHashMap para mantener el orden de inserción

    // Sentencia SQL para obtener el top 3 de sucursales con más ingresos
    String sql = "SELECT Nombre, COALESCE(Ingresos, 0.0) AS Ingresos FROM admin.Sucursal ORDER BY Ingresos DESC LIMIT 3";

    try {
        PreparedStatement preparedStatement = conexion.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        // Iterar sobre los resultados y agregar al mapa
        while (resultSet.next()) {
            String nombreSucursal = resultSet.getString("Nombre");
            double ingresos = resultSet.getDouble("Ingresos");
            topSucursales.put(nombreSucursal, ingresos);
        }

        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        // Manejar la excepción según tus necesidades
    }

    return topSucursales;
}


    public void cerrarC() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(CAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
