package principal;

import tiposDeCambio.PagoEnEuros;
import metodosDePago.Cartera;
import usuario.Usuario;
import java.util.Scanner;

/**
 *
 * @author David
 */
public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Pantalla login = new Pantalla();

        login.setVisible(true);

    }
}
