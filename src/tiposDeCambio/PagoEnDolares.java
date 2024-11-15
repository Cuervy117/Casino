/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiposDeCambio;

/**
 *
 * @author David
 */
public class PagoEnDolares implements Pago {
    private final double tasaDeCambioDolarAPeso = 20.47;

    @Override
    public void pagar(double cantidad){
        System.out.println("pagando " + cantidad/tasaDeCambioDolarAPeso + " dolares");
    }
}
