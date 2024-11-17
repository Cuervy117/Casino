package memento;
import java.io.Serializable;

public class Memento implements Serializable {
    private final double saldo;
    private final String historial;

    public Memento(double saldo, String historial) {
        this.saldo = saldo;
        this.historial = historial;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getHistorial() {
        return historial;
    }
}

