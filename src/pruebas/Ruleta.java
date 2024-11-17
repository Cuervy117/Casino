package pruebas;
import java.util.Random;

public class Ruleta {
    private static final String[] COLORES = {"Rojo", "Negro"};
    private static final Random random = new Random();

    public int girar() {
        return random.nextInt(37); // 0 a 36
    }

    public String obtenerColor(int numero) {
        if (numero == 0) return "Verde"; // Color especial para 0
        return (numero % 2 == 0) ? COLORES[1] : COLORES[0];
    }

    public double calcularGanancia(String tipoApuesta, int numeroApuesta, int numeroGanador, String colorGanador, double montoApuesta) {
        if (tipoApuesta.equals("Número") && numeroApuesta == numeroGanador) {
            return montoApuesta * 35; // Paga 35 a 1
        } else if (tipoApuesta.equals("Par") && numeroGanador != 0 && numeroGanador % 2 == 0) {
            return montoApuesta * 2;
        } else if (tipoApuesta.equals("Impar") && numeroGanador % 2 != 0) {
            return montoApuesta * 2;
        } else if (tipoApuesta.equals(colorGanador)) {
            return montoApuesta * 2;
        }
        return -montoApuesta; // Pierde la apuesta
    }
}



