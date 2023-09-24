/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marketchapin.backEnd;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author jose
 */
public class Frame extends JFrame {
    
    private String title;

    public Frame(String title) {
        this.title  = title;
        this.setUndecorated(true);
    }
    
    public void configuracionesGenerales(Dimension panelSize){
        this.getContentPane().removeAll();
        this.setTitle(title);
        this.setSize(panelSize);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
}
