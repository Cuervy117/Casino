package principal;

import tiposDeCambio.PagoEnEuros;
import metodosDePago.Cartera;
import usuario.Usuario;

/**
 *
 * @author David
 */
public class Principal {
    public static void main(String[] args) {
        Usuario usuario = new Usuario("123323", "David",
                "david@gmail.com", new Cartera(123, new PagoEnEuros()));
        System.out.println(usuario.getCartera().getSaldo());
        usuario.pagar(120);
        System.out.println(usuario.getCartera().getSaldo());

    }
}
