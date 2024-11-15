package usuario;

public class CuentaBancaria {
    private String numeroCuenta;
    private String nombreBanco;
    private String titularCuenta;

    public CuentaBancaria(String numeroCuenta, String nombreBanco, String titularCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.nombreBanco = nombreBanco;
        this.titularCuenta = titularCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public String getTitularCuenta() {
        return titularCuenta;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", nombreBanco='" + nombreBanco + '\'' +
                ", titularCuenta='" + titularCuenta + '\'' +
                '}';
    }
}

