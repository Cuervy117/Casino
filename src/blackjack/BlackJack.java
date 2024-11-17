package blackjack;

import java.util.ArrayList;
import java.util.Scanner;

//Ignorar esta clase, base para hacer la interfaz gráfica

public class BlackJack {

    public static void jugar(){
        Scanner scanner = new Scanner(System.in);
        int opcion;
        Baraja baraja = new Baraja();
        double seguro = 0;
        ArrayList<Carta> manoJugador = new ArrayList<>();
        ArrayList<Carta> manoCrupier = new ArrayList<>();

        System.out.println("Ingrese la cantidad que desea apostar: ");
        double apuesta = scanner.nextDouble();

        System.out.println("Carta en su mano: " + baraja.getFirst().toString());
        manoJugador.add(baraja.removeFirst());
        System.out.println("Carta del crupier: " + baraja.getFirst().toString());
        manoCrupier.add(baraja.removeFirst());
        System.out.println("Segunda carta en su mano: " + baraja.getFirst().toString());
        manoJugador.add(baraja.removeFirst());
        if(calcularValorDeMano(manoJugador) == 21){
            manejarApuesta(1, apuesta);
        }

        do {
            mostrarOpciones();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1 -> {
                    verMano(manoJugador);
                }
                case 2 -> {
                    verMano(manoCrupier);
                }
                case 3 -> {
                    if(manoCrupier.getFirst().getNumero().equals("As")){
                        seguro = apuesta / 2;
                        System.out.println("Se ha pagado $" + seguro + " como seguro");
                    }else{
                        System.out.println("Solo se puede pagar seguro si la primera carta del crupier fue As");
                    }
                }
                case 4 -> {
                    pedirCarta(baraja, manoJugador, apuesta);
                    if(calcularValorDeMano(manoJugador) > 21){
                        manejarApuesta(3, apuesta);
                        return;
                    }
                }
                case 5 -> {
                    plantarse(baraja, manoJugador, manoCrupier, apuesta, seguro);
                    return;
                }
                default -> {
                    System.out.println("Opción inválida. Ingrese nuevamente");
                }
            }
        } while (true);
    }

    private static void mostrarOpciones(){
        System.out.println("Seleccione número de acción que desea realizar: ");
        System.out.println("1) Ver mi mano");
        System.out.println("2) Ver mano del crupier");
        System.out.println("3) Pagar seguro (Mitad de la apuesta)");
        System.out.println("4) Pedir");
        System.out.println("5) Plantarse");
    }

    private static void verMano(ArrayList<Carta> mano){
        System.out.println("Cartas en la mano: ");
        for(Carta c : mano){
            System.out.println(c.toString());
        }
        System.out.println("Valor de mano: " + calcularValorDeMano(mano));
    }

    public static int calcularValorDeMano(ArrayList<Carta> mano) {
        int total = 0;
        int ases = 0;

        for(Carta c : mano){
            String numero = c.getNumero();
            try{
                total += Integer.parseInt(numero);
            }catch(NumberFormatException e){
                if(numero.equals("As")){
                    ases++;
                    total += 11;
                }else if(numero.equals("J") || numero.equals("Q") || numero.equals("K")){
                    total += 10;
                }
            }
        }
        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }
        return total;
    }

    private static void pedirCarta(Baraja baraja, ArrayList<Carta> manoJugador, double apuesta){
        if(calcularValorDeMano(manoJugador) != 21){
            System.out.println("Carta obtenida: " + baraja.getFirst().toString());
            manoJugador.add(baraja.removeFirst());
            verMano(manoJugador);
        }else{
            System.out.println("Ya no puedes pedir más cartas, has llegado a 21.");
        }
    }

    private static void plantarse(Baraja baraja, ArrayList<Carta> manoJugador, ArrayList<Carta> manoCrupier, double apuesta, double seguro){
        while(calcularValorDeMano(manoCrupier) <= 17){
            System.out.println("Nueva carta del crupier: " + baraja.getFirst().toString());
            manoCrupier.add(baraja.removeFirst());
            System.out.println("Mano del crupier hasta el momento: ");
            verMano(manoCrupier);
        }

        int b = determinarResultado(manoJugador, manoCrupier);
        if(b == 3 && seguro != 0 && calcularValorDeMano(manoCrupier) == 21 && manoCrupier.size() == 2){
            System.out.println("Gracias al seguro...");
            manejarApuesta(2, apuesta);
        }else{
            manejarApuesta(b, apuesta);
        }
    }

    public static int determinarResultado(ArrayList<Carta> manoJugador, ArrayList<Carta> manoCrupier){
        if(calcularValorDeMano(manoJugador) == calcularValorDeMano(manoCrupier))
            return 2;
        if(calcularValorDeMano(manoCrupier) > 21 || calcularValorDeMano(manoCrupier) < calcularValorDeMano(manoCrupier)){
            return 1;
        }else{
            return 3;
        }
    }

    private static void manejarApuesta(int resultado, double apuesta){
        switch (resultado) {
            case 1 -> {
                System.out.println("Has ganado, ganas la cantidad que apostaste.");
            }
            case 2 -> {
                System.out.println("Has empatado, se regresa su dinero.");
            }
            case 3 -> {
                System.out.println("Has perdido, se le cobra su apuesta.");
            }
        }
    }

}

