/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.marketchapin;

import com.mycompany.marketchapin.Conexiones.CAdmin;
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
import java.util.Map;

/**
 *
 * @author jose
 */
public class MarketChapin {

    public static void main(String[] args) {

        Frame primero = new Frame("Market-Chapin");
        Login panel = new Login(primero);
        CambioFrame cambio = new CambioFrame(primero, panel);
        // Obtener el top 10 de productos más vendidos

    }
}
