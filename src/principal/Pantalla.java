package principal;

import javax.swing.*;
import metodosDePago.Cartera;
import tiposDeCambio.PagoEnEuros;
import usuario.Usuario;

public class Pantalla extends JFrame {
    private JTextField usuario;
    private JPasswordField contraseña;
    private JButton entrar;
    private JPanel Principal;
    private JPanel Titulo;
    private JPanel Entrada;
    private JButton crearCuentaButton;
    private PagoEnEuros pago = new PagoEnEuros();
    private Usuario user = new Usuario("","David", "david@david",new Cartera(12000, pago), "asdfasdfa");

    public Pantalla(){
        inicializar();
        crearCuentaButton.addActionListener(e -> {
            CrearCuenta crearCuenta = new CrearCuenta();
            crearCuenta.setVisible(true);
        });

        usuario.addActionListener(e -> contraseña.requestFocusInWindow());
        contraseña.addActionListener(e -> {
            entrar.requestFocusInWindow();
            entrar.doClick();
        });

        entrar.addActionListener(e -> {

            JOptionPane.showMessageDialog(this, "Bienvenido al sistema");
            dispose();
            Casino casino = new Casino(user);
            casino.setVisible(true);
        });
    }

    private void inicializar(){
        setContentPane(Principal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

    }

}
