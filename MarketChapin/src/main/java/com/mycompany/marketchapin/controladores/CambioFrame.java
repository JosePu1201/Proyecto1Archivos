/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marketchapin.controladores;

import com.mycompany.marketchapin.frontEnd.Frame;
import javax.swing.JPanel;

/**
 *
 * @author jose
 */
public class CambioFrame {

    private Frame frame;
    private JPanel panel;

    public CambioFrame(Frame frame, JPanel panel) {
        this.frame = frame;
        this.panel = panel;
        cambios();
    }

    public void cambios() {
        frame.configuracionesGenerales(panel.getPreferredSize());
        frame.add(panel);
    }
}
