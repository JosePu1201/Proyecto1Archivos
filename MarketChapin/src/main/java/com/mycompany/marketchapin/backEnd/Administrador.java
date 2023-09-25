/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.marketchapin.backEnd;

import com.mycompany.marketchapin.controladores.CambioFrame;

/**
 *
 * @author jose
 */
public class Administrador extends javax.swing.JPanel {
    private Frame principal;
    /**
     * Creates new form Administrador
     */
    public Administrador(Frame principal) {
       this.principal = principal;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sucursal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(630, 560));
        setPreferredSize(new java.awt.Dimension(641, 579));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Chilanka", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 51, 0));
        jLabel2.setText("Market-Chapin");

        jLabel3.setFont(new java.awt.Font("Chandas", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 0));
        jLabel3.setText("Area administrativa ");

        sucursal.setFont(new java.awt.Font("Chandas", 1, 18)); // NOI18N
        sucursal.setForeground(new java.awt.Color(255, 51, 0));
        sucursal.setText("Sucursal:");

        jLabel1.setFont(new java.awt.Font("Chandas", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 0));
        jLabel1.setText("Elije lo que deceas hacear");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Chandas", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 51, 0));
        jButton1.setText("Agregar empleado");
        jButton1.setOpaque(false);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Chandas", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 51, 0));
        jButton2.setText("Ver reportes");
        jButton2.setOpaque(false);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Chandas", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 51, 0));
        jButton3.setText("Actualizar clientes");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Chandas", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 51, 0));
        jButton4.setText("Administrar tarjetas");
        jButton4.setOpaque(false);

        jButton5.setBackground(new java.awt.Color(153, 255, 153));
        jButton5.setFont(new java.awt.Font("Chandas", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 102, 0));
        jButton5.setText("Cerrar sesion");
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(sucursal))
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(sucursal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 470, -1));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/marketchapin/backEnd/imagenes/photo-1570284613060-766c33850e00.jpeg"))); // NOI18N
        fondo.setInheritsPopupMenu(false);
        fondo.setOpaque(true);
        add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 580));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        CambioFrame nuevo = new  CambioFrame(principal, new Login(principal));
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel sucursal;
    // End of variables declaration//GEN-END:variables
}
