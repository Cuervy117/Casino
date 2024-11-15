package usuario;

import java.util.ArrayList;
import java.util.List;

public class Cartera {
    private double saldo;  
    private List<Tarjeta> tarjetas;  
    private List<CuentaBancaria> cuentasBancarias;  

    public Cartera(double saldoInicial) {
        this.saldo = saldoInicial;
        this.tarjetas = new ArrayList<>();
        this.cuentasBancarias = new ArrayList<>();
    }

    public double getSaldo() {
        return saldo;
    }

    public void agregarSaldoCasino(double cantidad) {
        saldo += cantidad;
    }

    public boolean retirarSaldoCasino(double cantidad) {
        if (cantidad <= saldo) {
            saldo -= cantidad;
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
}
