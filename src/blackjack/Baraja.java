package blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Baraja {
    private static final String[] palos = {"Picas", "Corazones", "Diamantes", "Tréboles"};
    private static final String[] numeros = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private ArrayList<Carta> cartas = new ArrayList<>();

    public Baraja(){
        for(String p : palos){
            for(String n : numeros){
                Carta carta = new Carta(p, n);
                cartas.add(carta);
            }
        }
        Collections.shuffle(cartas);
    }

    public Carta getFirst(){
        return cartas.getFirst();
    }

    public Carta removeFirst(){
        return cartas.removeFirst();
    }

    public int size(){
        return cartas.size();
    }
}
