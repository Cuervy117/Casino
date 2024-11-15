package tiposDeCambio;

public class PagoEnEuros implements Pago {
    private final double tasaDeCambioEuroAPeso = 21.58;

    @Override
    public void pagar(double cantidad) {
        System.out.println("Pagando " + cantidad/tasaDeCambioEuroAPeso + " euros");
    }
}
