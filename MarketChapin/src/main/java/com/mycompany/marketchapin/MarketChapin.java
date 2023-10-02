/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.marketchapin;

import com.mycompany.marketchapin.frontEnd.Frame;
import com.mycompany.marketchapin.frontEnd.Login;
import com.mycompany.marketchapin.controladores.CambioFrame;

/**
 *
 * @author jose
 * CLase principal y metodo main
 * primero el frma  principal 
 * @author jose
 */
public class MarketChapin {

    public static void main(String[] args) {
        Frame primero = new Frame("Market-Chapin");
        Login panel = new Login(primero);
        CambioFrame cambio = new CambioFrame(primero, panel);
    }
}
