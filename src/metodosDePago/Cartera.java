package metodosDePago;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import tiposDeCambio.Pago;
// Implementé la interfaz Serializable para poder guardar esto en el archivo de objetos. 
//Se deberá hacer también para todas las clases con las que desees hacer lo mismo pibe
public class Cartera implements Serializable{
    private double saldo;  
    private List<Tarjeta> tarjetas;
    private List<CuentaBancaria> cuentasBancarias;
    private Pago tipoDeCambio;
    //Nuevas adiciones: Historial de movimientos:
    private ArrayList<String> historial;

    public Cartera(double saldoInicial, Pago tipoDeCambio) {
        this.saldo = saldoInicial;
        this.tarjetas = new ArrayList<>();
        this.cuentasBancarias = new ArrayList<>();
        this.tipoDeCambio = tipoDeCambio;
        this.historial = new ArrayList<>();
    }

    public void realizarPago(double cantidad) throws IllegalArgumentException{
        if(cantidad > saldo){
            throw new IllegalArgumentException("Saldo insuficiente. Estado de saldo: " + saldo + " Te faltan: " + (cantidad - saldo));
        }
        // Elegir método de pago por medio de la interfaz
        tipoDeCambio.pagar(cantidad);
        saldo -= cantidad;
        //Lógica de Historial de pago
        historial.add("Pagó: $" + cantidad);
    }

    public double getSaldo() {
        return saldo;
    }
    //Tengo que añadirlo por los mementos
    public ArrayList<String> getHistorial() {
        return historial;
    }
    public void setSaldo(double saldo){
        this.saldo = saldo;
    }

    public void setHistorial(ArrayList<String> historial) {
        this.historial = historial;
    }

    // Esto supongo que es para juntar las ganancias del casino, por ende agregué lógica de historial
    public void agregarSaldoCasino(double cantidad) {
        saldo += cantidad;
        //Lógica de Historial (Borra en caso de no considerarlo lógico)
        historial.add("Se ingresó un monto de: $" + cantidad);
         
    }

    //Intentalo Trabajar por excepciones o como veas, agrega la lógica del historial
    public boolean retirarSaldoCasino(double cantidad) {
        if (cantidad <= saldo) {
            saldo -= cantidad;
            // Mensaje y anotación en el historial
            historial.add("Se hizo un retiro por: $" + cantidad);
            System.out.println("Saldo restante: " + saldo);
            return true;
        } else {
            System.out.println("Saldo insuficiente en el casino.");
            return false;
        }
    }

    public void agregarTarjeta(Tarjeta tarjeta) {
        tarjetas.add(tarjeta);
    }

    public void eliminarTarjeta(Tarjeta tarjeta) {
        tarjetas.remove(tarjeta);
    }

    public List<Tarjeta> obtenerTarjetas() {
        return tarjetas;
    }

    public void agregarCuentaBancaria(CuentaBancaria cuenta) {
        cuentasBancarias.add(cuenta);
    }

    public void eliminarCuentaBancaria(CuentaBancaria cuenta) {
        cuentasBancarias.remove(cuenta);
    }

    public List<CuentaBancaria> obtenerCuentasBancarias() {
        return cuentasBancarias;
    }

    public void mostrarInformacion() {
        System.out.println("Saldo en casino: $" + saldo);

        System.out.println("\nTarjetas Bancarias:");
        for (Tarjeta tarjeta : tarjetas) {
            System.out.println(tarjeta);
        }

        System.out.println("\nCuentas Bancarias:");
        for (CuentaBancaria cuenta : cuentasBancarias) {
            System.out.println(cuenta);
        }
    }

    // Retornar el objeto
    @Override
    public String toString() {
        return "Usuario{" +
                "Saldo='" + saldo + '\'' +
                ", TipoDeCambio='" + tipoDeCambio.getClass().getSimpleName() + '\'';
    }


}
