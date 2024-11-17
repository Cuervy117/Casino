package pruebas;

public class UsuarioRuleta {
    private String nombre;
    private double saldo;

    public UsuarioRuleta(String nombre, double saldoInicial) {
        this.nombre = nombre;
        this.saldo = saldoInicial;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void actualizarSaldo(double monto) {
        this.saldo += monto;
    }

    public boolean puedeApostar(double monto) {
        return saldo >= monto;
    }
}
