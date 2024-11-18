package principal;

import java.util.Scanner;

/**
 *
 * @author David
 */
public class Principal {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //GestorUsuarios gestorUsuarios = new GestorUsuarios("src/datos/usuarios.dat");
        Pantalla login = new Pantalla();
        login.setVisible(true);

    }
}
