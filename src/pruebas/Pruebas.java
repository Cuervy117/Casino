package pruebas;
import memento.*;
import metodosDePago.Cartera;
import tiposDeCambio.*;
import usuario.*;
public class Pruebas {
    public static void main(String[] args) {
        Pago pesosMexicanos = new PagoEnPesos();
        GestorUsuarios gestorUsuarios = new GestorUsuarios("src/datos/usuarios.dat");
        /*Usuario administrador = new Admin("4321", "David", "Cuervy@gmail.com", new Cartera(1000, pesosMexicanos),"contraseña" );
        */
        Usuario usuario = new Usuario("123", "Samuel", "santi.sam120@gmail.com", new Cartera(1000, pesosMexicanos), "123");
        try {
            gestorUsuarios.registrarUsuario(usuario);
            System.out.println("Usuario registrado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Primer usuario " + usuario.toString());
        Juego apuesta = new Juego(usuario);
        apuesta.jugar();
        System.out.println("Saldo después de jugar " + usuario.getCartera().getSaldo());
        System.out.println("Historial crediticio: " + usuario.getCartera().getHistorial());

        try {
            Usuario papu = gestorUsuarios.autenticarUsuario("Samuel", "123");
            System.out.println("Saldo inicial: $" + papu.getCartera().getSaldo());

            papu.getCartera().realizarPago(200);
            papu.getCartera().agregarSaldoCasino(500);
            System.out.println("Saldo después de apostar y ganar: $" + papu.getCartera().getSaldo());

            gestorUsuarios.guardarEstadoUsuario(papu.getNombre());
            System.out.println("Estado guardado.");

            papu.getCartera().realizarPago(300);
            System.out.println("Saldo después de otra apuesta: $" + papu.getCartera().getSaldo());

            gestorUsuarios.restaurarEstadoUsuario(papu.getNombre());
            System.out.println("Estado restaurado. Saldo: $" + papu.getCartera().getSaldo());

            System.out.println("Apuesta excesiva uwu");
            papu.getCartera().realizarPago(1400);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    private static class Juego{
        private Usuario jugador;
        private Juego(Usuario jugador){
            this.jugador = jugador;
        }

        
        void jugar(){
            try {
                double cantidadApuesta = 800;
                System.out.println("La cantidad a apostar será de " +cantidadApuesta );
                //El jugador paga esta cantidad
                jugador.pagar(cantidadApuesta);
                System.out.println("Felicidades ganaste! ");
                jugador.getCartera().agregarSaldoCasino(cantidadApuesta/2);
            } catch (Exception e) {
                System.out.println("Quizás el saldo no es suficiente");
            }
            
        }

    }
}
