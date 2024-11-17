/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiposDeCambio;

import java.io.Serializable;

/**
 *
 * @author David
 */
public class PagoEnPesos implements Pago, Serializable {
    @Override
    public void pagar(double cantidad) {
        System.out.println("Pagando " + cantidad + " en pesos");
    }
}
