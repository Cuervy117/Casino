package ruleta;

import usuario.Usuario;

import java.util.Random;

public class JuegoRuleta implements Juego {
    private Usuario usuario;
    private int numero;
    private String color;
    public JuegoRuleta(Usuario usuario) {
        this.usuario = usuario;
    }
    @Override
    public void jugar() {
        Random rand = new Random();
        numero = rand.nextInt(37); //Limite hasta 37

    }

    @Override
    public void manejarApuesta() {

    }
}
