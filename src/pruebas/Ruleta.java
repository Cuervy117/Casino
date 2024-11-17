package pruebas;
import java.util.Random;

public class Ruleta {
    private final Random random = new Random();
    private int numero;
    private String color;

    public void girar() {
        numero = random.nextInt(37); // Números de 0 a 36
        color = (numero == 0) ? "Verde" : (numero % 2 == 0 ? "Rojo" : "Negro");
    }

    public int getNumero() {
        return numero;
    }

    public String getColor() {
        return color;
    }
}




