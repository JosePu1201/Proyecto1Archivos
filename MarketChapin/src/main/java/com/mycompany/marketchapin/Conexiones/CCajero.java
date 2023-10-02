/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marketchapin.Conexiones;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose
 * Todas las acciones del cajero codigo puy grnade y tomar en cuenta para muchas validaciones 
 */
public class CCajero {

    private String idBodega;
    private String idSucursal;
    private String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket";
    private String username = "postgres";
    private String password = "jose";
    private Connection conexion;

    public CCajero(String idSucursal) {
        this.idSucursal = idSucursal;
        try {
            conexion = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(CBodega.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.idBodega = encontrarBodega(idSucursal);
    }
    /*
    *Busca la bodega y la devuelve como String, devuelve su id
    */
    public String encontrarBodega(String codigoSucursal) {
        String retorno;

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
    /*
    *Obtiene todos los productos en bodega
    */
    public void obtenerCantidadProductosEnBodega(DefaultTableModel modelo) {
        
        modelo.setRowCount(0);
        String sql = "SELECT p.Codigo AS codigo_producto, p.Nombre AS nombre_producto, SUM(pe.cant) AS cantidad_total "
                + "FROM estante.Producto_En_Estanteria pe "
                + "JOIN prodG.Producto p ON pe.idProducto = p.Codigo "
                + "JOIN estante.Estanteria e ON pe.idEstante = e.idEstante "
                + "WHERE e.idBodega = ? "
                + "GROUP BY p.Codigo, p.Nombre";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, idBodega);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigoProducto = resultSet.getInt("codigo_producto");
                String nombreProducto = resultSet.getString("nombre_producto");
                int cantidadTotal = resultSet.getInt("cantidad_total");
                String[] cosas = {codigoProducto + "", nombreProducto, cantidadTotal + ""};
                modelo.addRow(cosas);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }
    /*
    *Obtiene el total de productos de una sucursal y luego en base a una cantidad de compra este va reduciendo de estanteria en estanteria 
    */
    public boolean restarCantidadProductosEnEstanterias(int idProducto, int cantidad, ArrayList<String> info) {
        boolean bandera = false;
        try {
           
            String sqlCantidadTotal = "SELECT SUM(cant) AS cantidad_total FROM estante.Producto_En_Estanteria WHERE idProducto = ?";
            PreparedStatement preparedStatementCantidadTotal = conexion.prepareStatement(sqlCantidadTotal);
            preparedStatementCantidadTotal.setInt(1, idProducto);

            ResultSet resultSetCantidadTotal = preparedStatementCantidadTotal.executeQuery();
            int cantidadTotal = 0;

            if (resultSetCantidadTotal.next()) {
                cantidadTotal = resultSetCantidadTotal.getInt("cantidad_total");
            }

            resultSetCantidadTotal.close();
            preparedStatementCantidadTotal.close();

            if (cantidadTotal >= cantidad) {
                // Hay suficiente cantidad del producto en las estanterías

                // Obtener las estanterías con ese producto
                String sqlEstanterias = "SELECT idEstante, cant FROM estante.Producto_En_Estanteria WHERE idProducto = ?";
                PreparedStatement preparedStatementEstanterias = conexion.prepareStatement(sqlEstanterias);
                preparedStatementEstanterias.setInt(1, idProducto);

                ResultSet resultSetEstanterias = preparedStatementEstanterias.executeQuery();

                while (resultSetEstanterias.next() && cantidad > 0) {
                    int idEstante = resultSetEstanterias.getInt("idEstante");
                    int cantidadEnEstante = resultSetEstanterias.getInt("cant");

                    // Calcular la cantidad a restar en esta estantería
                    int cantidadAQuitar = Math.min(cantidad, cantidadEnEstante);

                    // Actualizar la cantidad en la estantería
                    String sqlActualizar = "UPDATE estante.Producto_En_Estanteria SET cant = ? WHERE idEstante = ? AND idProducto = ?";
                    PreparedStatement preparedStatementActualizar = conexion.prepareStatement(sqlActualizar);
                    preparedStatementActualizar.setInt(1, cantidadEnEstante - cantidadAQuitar);
                    preparedStatementActualizar.setInt(2, idEstante);
                    preparedStatementActualizar.setInt(3, idProducto);
                    String salida = idEstante + "," + idProducto + "," + cantidadAQuitar;
                    info.add(salida);
                    preparedStatementActualizar.executeUpdate();

                    preparedStatementActualizar.close();

                    // Restar la cantidad procesada
                    cantidad -= cantidadAQuitar;
                }

                resultSetEstanterias.close();
                preparedStatementEstanterias.close();

                if (cantidad == 0) {
                    // Se restó la cantidad solicitada en todas las estanterías
                    bandera = true;
                    System.out.println("Se restó la cantidad solicitada en todas las estanterías.");
                } else {
                    // No hay suficiente cantidad en algunas estanterías
                    System.out.println("No hay suficiente cantidad en algunas estanterías.");
                }
            } else {
                // No hay suficiente cantidad total del producto en las estanterías
                JOptionPane.showMessageDialog(null, "La cantidad no esta disponible en estanteria");
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        }
        return bandera;
    }
    /*
    *Crea nueva factura 
    */
    public int insertarNuevaFactura(String nombre, Date fecha, String codigoEmpleado, BigDecimal totalSinDescuento, BigDecimal totalConDescuento) {
        String sql = "INSERT INTO factura.Venta (Nombre, Fecha, codigoEmpleado, TotalSinDescuento, TotalConDescuento) "
                + "VALUES (?, ?, ?, ?, ?) RETURNING numeroFactura";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);

            preparedStatement.setString(1, nombre);
            preparedStatement.setDate(2, fecha);
            preparedStatement.setString(3, codigoEmpleado);
            preparedStatement.setBigDecimal(4, totalSinDescuento);
            preparedStatement.setBigDecimal(5, totalConDescuento);

            // Ejecutar la inserción y obtener el número de factura
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int numeroFactura = resultSet.getInt("numeroFactura");
                return numeroFactura;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        return -1; // Retorna -1 en caso de error
    }

    public boolean existeNit(String nit) {
        
        String sql = "SELECT COUNT(*) AS count FROM clientes.Cliente WHERE Nit = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, nit);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // Si count es mayor que 0, el NIT existe
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        return false; // Retorna false en caso de error
    }

    public void insertarNuevoCliente(String nombre, String nit) {
        // Verificar si el NIT ya existe antes de insertar el nuevo cliente

        String sql = "INSERT INTO clientes.Cliente (Nit, Nombre, GastosEnTienda) VALUES (?, ?, 0)";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, nit);
            preparedStatement.setString(2, nombre);

            int filasAfectadas = preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }

    }

    public boolean insertarNuevaListaProd(int cantidadVendida, int codigoProducto, int numeroFactura, DefaultTableModel modelo) {
        // Obtener el precio del producto
        BigDecimal precioProducto = obtenerPrecioProducto(codigoProducto);

        // Verificar si el producto existe y se pudo obtener el precio
        if (precioProducto == null) {
            System.out.println("Error al obtener el precio del producto.");
            return false;
        }

        // Calcular el subtotal
        BigDecimal subtotal = precioProducto.multiply(new BigDecimal(cantidadVendida));

        
        String sql = "INSERT INTO factura.ListaProd (cantidadVendiad, codigoProducto, numeroFactura, subTotal) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, cantidadVendida);
            preparedStatement.setInt(2, codigoProducto);
            preparedStatement.setInt(3, numeroFactura);
            preparedStatement.setBigDecimal(4, subtotal);

            int filasAfectadas = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (filasAfectadas > 0) {
                // La inserción fue exitosa, agregar información al modelo
                agregarInformacionAlModelo(cantidadVendida, codigoProducto, precioProducto, subtotal, modelo);

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }

        return false; // Retorna false en caso de error
    }

// Función para agregar información al modelo de la tabla
    private void agregarInformacionAlModelo(int cantidadVendida, int codigoProducto, BigDecimal precioProducto, BigDecimal subtotal, DefaultTableModel modelo) {
        // Obtener el nombre del producto
        String nombreProducto = obtenerNombreProducto(codigoProducto);

        // Agregar la información al modelo
        Object[] fila = {nombreProducto, cantidadVendida, precioProducto, subtotal};
        modelo.addRow(fila);
    }

// Función para obtener el nombre de un producto
    private String obtenerNombreProducto(int codigoProducto) {
        // Sentencia SQL para obtener el nombre del producto
        String sql = "SELECT Nombre FROM prodG.Producto WHERE Codigo = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, codigoProducto);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("Nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
          
        }

        return null; // Retorna null si hay un error o el producto no existe
    }

// Función para obtener el precio de un producto
    private BigDecimal obtenerPrecioProducto(int codigoProducto) {
        
        String sql = "SELECT Precio FROM prodG.Producto WHERE Codigo = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, codigoProducto);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("Precio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }

        return null; // Retorna null si hay un error o el producto no existe
    }

    public boolean eliminarFactura(int numeroFactura) {
       
        String sqlEliminarFactura = "DELETE FROM factura.Venta WHERE numeroFactura = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sqlEliminarFactura);
            preparedStatement.setInt(1, numeroFactura);

            int filasAfectadas = preparedStatement.executeUpdate();
            preparedStatement.close();

            return filasAfectadas > 0; // Retorna true si se eliminó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        return false; // Retorna false en caso de error
    }

    public void insertarProductoEnEstanteria(int idEstante, int idProducto, int cantidad) {
       
        String sql = "INSERT INTO estante.Producto_En_Estanteria (idEstante, idProducto, cant) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, idEstante);
            preparedStatement.setInt(2, idProducto);
            preparedStatement.setInt(3, cantidad);

            // Ejecutar la inserción
            int filasAfectadas = preparedStatement.executeUpdate();

            // Verificar si se realizaron inserciones
            if (filasAfectadas > 0) {
                System.out.println("Se insertó el producto en la estantería con éxito.");
            } else {
                System.out.println("No se pudo insertar el producto en la estantería.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

    public boolean productoEnEstanteriaExiste(String idEstante, int idProducto) {
       
        String sql = "SELECT COUNT(*) AS count "
                + "FROM estante.Producto_En_Estanteria "
                + "WHERE idEstante = CAST(? AS INTEGER) AND idProducto = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, idEstante);
            preparedStatement.setInt(2, idProducto);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del producto en estantería");
            e.printStackTrace();
        }

        return false;
    }

    public void actualizarCantidadEnEstanteria(String idEstante, int idProducto, int cantidadAIncrementar) {
        // Obtener la cantidad actual en la estantería
        int cantidadActual = obtenerCantidadEnEstanteria(idEstante, idProducto);

        if (cantidadActual == -1) {
            System.out.println("No se encontró el registro en la estantería para actualizar.");
            return;
        }

        // Calcular la nueva cantidad sumando la cantidad actual con la cantidad a incrementar
        int nuevaCantidad = cantidadActual + cantidadAIncrementar;

        String sql = "UPDATE estante.Producto_En_Estanteria "
                + "SET cant = ? "
                + "WHERE idEstante = CAST(? AS INTEGER) AND idProducto = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, nuevaCantidad);
            preparedStatement.setString(2, idEstante);
            preparedStatement.setInt(3, idProducto);

            // Ejecutar la actualización
            int filasActualizadas = preparedStatement.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "La cantidad se actualizo en la estanteria: " + idEstante);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el registro en la estantería para actualizar");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar la cantidad en la estantería");
            e.printStackTrace();
        }
    }

    private int obtenerCantidadEnEstanteria(String idEstante, int idProducto) {
        
        String sql = "SELECT cant "
                + "FROM estante.Producto_En_Estanteria "
                + "WHERE idEstante = CAST(? AS INTEGER) AND idProducto = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, idEstante);
            preparedStatement.setInt(2, idProducto);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("cant");
            } else {
                return -1; // Indicar que no se encontró el registro
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la cantidad en la estantería");
            e.printStackTrace();
            return -1; // Indicar un error
        }
    }

    public BigDecimal sumarSubtotalesPorFactura(int numeroFactura) {
       
        String sql = "SELECT SUM(subTotal) AS sumaSubtotales FROM factura.ListaProd WHERE numeroFactura = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, numeroFactura);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("sumaSubtotales");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        return BigDecimal.ZERO; // Retorna BigDecimal.ZERO si hay un error o no se encuentra ninguna factura
    }

    public boolean obtenerTarjetaAsociada(String nit, String tarjetaAsociada) {
        
        String sql = "SELECT Numero_Tarjeta FROM descuentos.Tarjeta WHERE Nit_Cliente = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, nit);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Si existe una tarjeta asociada, obtener el número de tarjeta
                String numeroTarjeta = resultSet.getString("Numero_Tarjeta");

                // Almacenar el número de tarjeta en el arreglo (segundo parámetro)
                tarjetaAsociada = numeroTarjeta;

                // Indicar que el NIT está asociado a una tarjeta
                return true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        // Indicar que el NIT no está asociado a una tarjeta
        return false;
    }

    public boolean actualizarFactura(int numeroFactura, BigDecimal totalSinDescuento, BigDecimal totalConDescuento) {
        
        String sql = "UPDATE factura.Venta SET TotalSinDescuento = ?, TotalConDescuento = ? WHERE numeroFactura = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, totalSinDescuento);
            preparedStatement.setBigDecimal(2, totalConDescuento);
            preparedStatement.setInt(3, numeroFactura);

            // Ejecutar la consulta de actualización
            int filasAfectadas = preparedStatement.executeUpdate();

            // Verificar si la actualización fue exitosa (una fila afectada indica éxito)
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
           
            return false;
        }
    }

    public boolean actualizarGastosCliente(String nit, BigDecimal gastosEnTienda, BigDecimal gastosReset) {
        
        String sql = "UPDATE clientes.Cliente SET GastosEnTienda = GastosEnTienda + ?, GastosReset = GastosReset + ? WHERE Nit = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, gastosEnTienda);
            preparedStatement.setBigDecimal(2, gastosReset);
            preparedStatement.setString(3, nit);

            // Ejecutar la consulta de actualización
            int filasAfectadas = preparedStatement.executeUpdate();

            // Verificar si la actualización fue exitosa (una fila afectada indica éxito)
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }

    public int obtenerDescuentoPorNit(String nit) {
        
        String sql = "SELECT Descuento FROM descuentos.Tarjeta WHERE Nit_Cliente = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, nit);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Descuento");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
           
        }

        return 0; // Retornar 0 en caso de error o si no se encuentra la tarjeta
    }

    public void actualizarPuntosTarjeta(String nit, int puntosASumar) {
       
        String sql = "UPDATE descuentos.Tarjeta SET Puntos = Puntos + ? WHERE Nit_Cliente = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, puntosASumar);
            preparedStatement.setString(2, nit);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

    public int obtenerPuntosTarjeta(String nit) {
      
        String sql = "SELECT Puntos FROM descuentos.Tarjeta WHERE Nit_Cliente = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, nit);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Puntos");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
         
        }

        return 0; // Retornar 0 en caso de error o si no se encuentra la tarjeta
    }
    public void sumarIngresosSucursal(String codigoSucursal, BigDecimal nuevosIngresos) {
       
        String sqlSumarIngresos = "UPDATE admin.Sucursal SET Ingresos = Ingresos + ? WHERE Codigo = ?";

        try {
            // Actualizar los ingresos en la base de datos
            PreparedStatement preparedStatement = conexion.prepareStatement(sqlSumarIngresos);
            preparedStatement.setBigDecimal(1, nuevosIngresos);
            preparedStatement.setString(2, codigoSucursal);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

}
