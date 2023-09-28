/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.marketchapin.frontEnd;

import com.mycompany.marketchapin.controladores.CambioFrame;

/**
 *
 * @author jose
 */
public class opcionCajero extends javax.swing.JPanel {

    private Frame principal;
    private String usuario;
    private String sucursal;

    /**
     * Creates new form opcionCajero
     *
     * @param principal
     * @param usuario
     * @param sucursal
     */
    public opcionCajero(Frame principal, String usuario, String sucursal) {
        this.principal = principal;
        this.sucursal = sucursal;
        this.usuario = usuario;
        initComponents();
        jLabel1.setText("Usuario: " + usuario + "          Sucursal: " + sucursal);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();

        jToolBar1.setRollover(true);

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("C059", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Modificar datos de clientes");
        jButton1.setOpaque(false);
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, -1, -1));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("C059", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Generar venta");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, -1, -1));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("C059", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setText("Cerrar sesion");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 710, -1, -1));

        jLabel1.setFont(new java.awt.Font("C059", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cajero:          Sucursal:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 330, -1));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/marketchapin/frontEnd/imagenes/atm-machine-isolated-on-white-background-free-vector.jpg"))); // NOI18N
        add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 842));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        CambioFrame nuevo = new CambioFrame(principal, new Login(principal));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CambioFrame nuevoFrame = new CambioFrame(principal, new Venta(principal, usuario, sucursal, this));
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
