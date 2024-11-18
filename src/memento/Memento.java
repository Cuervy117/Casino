package memento;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Memento implements Serializable {
    private final double saldo;
    private final ArrayList<String> historial;

    public Memento(double saldo, ArrayList<String> historial) {
        this.saldo = saldo;
        this.historial = historial;
    }

    public double getSaldo() {
        return saldo;
    }

    public ArrayList<String> getHistorial() {
        return historial;
    }
}

