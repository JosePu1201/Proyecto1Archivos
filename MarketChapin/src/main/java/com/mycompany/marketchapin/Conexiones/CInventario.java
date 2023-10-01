/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marketchapin.Conexiones;

import static java.lang.String.valueOf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author jose
 */
public class CInventario {

    private String idBodega;
    private String idSucursal;
    private String jdbcURL = "jdbc:postgresql://localhost:5432/chapinmarket";
    private String username = "postgres";
    private String password = "jose";
    private Connection conexion;

    public CInventario(String idSucursal) {
        this.idSucursal = idSucursal;
        try {
            conexion = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(CBodega.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.idBodega = encontrarBodega(this.idSucursal);

    }

    public String encontrarBodega(String codigoSucursal) {
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

    public void llenarComboBoxEstanterias(JComboBox<String> comboBoxEstanterias,ArrayList<String> estanterias) {
        // Limpiar el combo antes de agregar nuevos elementos
        comboBoxEstanterias.removeAllItems();
        estanterias.clear();
        // Sentencia SQL para obtener las estanterías asociadas a la bodega
        String sql = "SELECT idEstante FROM estante.Estanteria WHERE idBodega = ?";

        try {
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, this.idBodega);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Agregar cada estantería al combo
                comboBoxEstanterias.addItem(resultSet.getString("idEstante"));
                estanterias.add(resultSet.getString("idEstante"));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }
public void obtenerProductosEnEstanteria(String idEstante, JTable tabla) {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("Nombre");
    modelo.addColumn("Cantidad");
    tabla.setModel(modelo);

    // Sentencia SQL para obtener los productos en la estantería
    String sql = "SELECT p.Nombre, pe.cant " +
                 "FROM estante.Producto_En_Estanteria pe " +
                 "JOIN prodG.Producto p ON pe.idProducto = p.Codigo " +
                 "WHERE pe.idEstante = CAST(? AS INTEGER)";

    try {
        PreparedStatement preparedStatement = conexion.prepareStatement(sql);
        preparedStatement.setString(1, idEstante);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String nombreProducto = resultSet.getString("Nombre");
            int cantidad = resultSet.getInt("cant");
            String[] datos = {nombreProducto, String.valueOf(cantidad)};
            modelo.addRow(datos);
        }
        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        System.out.println("Error al obtener productos en estantería");
        e.printStackTrace();
    }
}


    public void obtenerProductosEnBodega(JTable productos, ArrayList<String> prodEnBodega) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
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
            preparedStatement.setString(1, idBodega);

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
    public void insertarProductoEnEstanteria(int idEstante, int idProducto, int cantidad) {
    // Sentencia SQL para insertar un nuevo producto en la estantería
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
        // Manejar la excepción según tus necesidades
    }
    }
    public boolean productoEnEstanteriaExiste(String idEstante, int idProducto) {
    // Sentencia SQL para verificar la existencia del producto en la estantería
    String sql = "SELECT COUNT(*) AS count " +
                 "FROM estante.Producto_En_Estanteria " +
                 "WHERE idEstante = CAST(? AS INTEGER) AND idProducto = ?";

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

    // Sentencia SQL para actualizar la cantidad de productos en la estantería
    String sql = "UPDATE estante.Producto_En_Estanteria " +
                 "SET cant = ? " +
                 "WHERE idEstante = CAST(? AS INTEGER) AND idProducto = ?";

    try {
        PreparedStatement preparedStatement = conexion.prepareStatement(sql);
        preparedStatement.setInt(1, nuevaCantidad);
        preparedStatement.setString(2, idEstante);
        preparedStatement.setInt(3, idProducto);

        // Ejecutar la actualización
        int filasActualizadas = preparedStatement.executeUpdate();

        if (filasActualizadas > 0) {
            JOptionPane.showMessageDialog(null, "La cantidad se actualizo en la estanteria: "+idEstante);
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
    // Sentencia SQL para obtener la cantidad de productos en la estantería
    String sql = "SELECT cant " +
                 "FROM estante.Producto_En_Estanteria " +
                 "WHERE idEstante = CAST(? AS INTEGER) AND idProducto = ?";

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

public int obtenerNumeroPasillo(String idEstante) {
    int numeroPasillo = -1;  // Valor por defecto en caso de no encontrar el número de pasillo
    
    // Sentencia SQL para obtener el número de pasillo
    String sql = "SELECT NumPasillo FROM estante.Estanteria WHERE idEstante = ?";

    try {
        PreparedStatement preparedStatement = conexion.prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(idEstante));
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            // Si se encuentra el número de pasillo, asignarlo a la variable
            numeroPasillo = resultSet.getInt("NumPasillo");
        }

        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        // Manejar la excepción según tus necesidades
        e.printStackTrace();
    }

    return numeroPasillo;
}
public int obtenerCantidadProductoEnBodega(int idProducto) {
    int cantidad = -1;  // Valor por defecto en caso de no encontrar la cantidad

    // Sentencia SQL para obtener la cantidad del producto en la bodega
    String sql = "SELECT cant FROM bodegaS.inventario_de_Producto_enBodega WHERE idBodega = ? AND idProducto = ?";

    try {
        PreparedStatement preparedStatement = conexion.prepareStatement(sql);
        preparedStatement.setString(1, idBodega);
        preparedStatement.setInt(2, idProducto);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            // Si se encuentra la cantidad, asignarla a la variable
            cantidad = resultSet.getInt("cant");
        }

        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        // Manejar la excepción según tus necesidades
        e.printStackTrace();
    }

    return cantidad;
}
public void restarCantidadProductoEnBodega( int idProducto, int cantidadARestar) {
    // Sentencia SQL para actualizar la cantidad del producto en la bodega
    String sql = "UPDATE bodegaS.inventario_de_Producto_enBodega SET cant = cant - ? WHERE idBodega = ? AND idProducto = ?";

    try {
        PreparedStatement preparedStatement = conexion.prepareStatement(sql);
        preparedStatement.setInt(1, cantidadARestar);
        preparedStatement.setString(2, idBodega);
        preparedStatement.setInt(3, idProducto);

        int filasAfectadas = preparedStatement.executeUpdate();

        preparedStatement.close();

        // Verificar si se actualizaron filas (es decir, si se encontró la combinación de idBodega e idProducto)
       
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

    public void CerrarConcexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(CBodega.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
