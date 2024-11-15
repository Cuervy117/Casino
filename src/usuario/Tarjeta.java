package usuario;

public class Tarjeta {
    private String numero;
    private String titular;
    private String fechaVencimiento;
    private String tipo; // Ejemplo: "Débito" o "Crédito"

    public Tarjeta(String numero, String titular, String fechaVencimiento, String tipo) {
        this.numero = numero;
        this.titular = titular;
        this.fechaVencimiento = fechaVencimiento;
        this.tipo = tipo;
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
