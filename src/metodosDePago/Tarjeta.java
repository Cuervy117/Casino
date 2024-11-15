package metodosDePago;
import tiposDeCambio.Pago;
public class Tarjeta {
    private String numero;
    private String titular;
    private String fechaVencimiento;
    private String tipo; // Ejemplo: "Débito" o "Crédito"
    private Pago tipoDeCambio;

    public Tarjeta(String numero, String titular, String fechaVencimiento, String tipo, Pago tipoDeCambio) {
        this.numero = numero;
        this.titular = titular;
        this.fechaVencimiento = fechaVencimiento;
        this.tipo = tipo;
        this.tipoDeCambio = tipoDeCambio;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Tarjeta{" +
                "numero='" + numero + '\'' +
                ", titular='" + titular + '\'' +
                ", fechaVencimiento='" + fechaVencimiento + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
