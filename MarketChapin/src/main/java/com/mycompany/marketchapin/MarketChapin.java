/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.marketchapin;

import com.mycompany.marketchapin.backEnd.Administrador;
import com.mycompany.marketchapin.backEnd.Bodega;
import com.mycompany.marketchapin.backEnd.Login;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author jose
 */
public class MarketChapin {

    public static void main(String[] args) {
        JFrame ventana = new JFrame();
        Bodega panel = new Bodega();
        ventana.setTitle("Market-Chapin");
        Dimension panelSize = panel.getPreferredSize();
        System.out.println("asd" + panelSize.width);
        ventana.setSize(panelSize);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        ventana.setLayout(new FlowLayout());
        ventana.add(panel);
        ventana.setVisible(true);
    }
}
