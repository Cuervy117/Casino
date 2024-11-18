package principal;

import javax.swing.*;

import memento.GestorUsuarios;
import metodosDePago.Cartera;
import tiposDeCambio.PagoEnEuros;
import usuario.Usuario;
import memento.*;


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
    private GestorUsuarios gestorUsuarios;
    public Pantalla(GestorUsuarios admin){
        this.gestorUsuarios = admin;
        inicializar();
        crearCuentaButton.addActionListener(e -> {
            CrearCuenta crearCuenta = new CrearCuenta(gestorUsuarios);
            crearCuenta.setVisible(true);
        });

        usuario.addActionListener(e -> contraseña.requestFocusInWindow());
        contraseña.addActionListener(e -> {
            entrar.requestFocusInWindow();
            entrar.doClick();
        });

        entrar.addActionListener(e -> {
            if (usuario.getText().isEmpty() || contraseña.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un nombre de usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                user = GestorUsuarios.getGestor().autenticarUsuario(usuario.getText(), new String(contraseña.getPassword()));
                JOptionPane.showMessageDialog(this, "Bienvenido al sistema");
                Casino casino = new Casino(user);
                casino.setVisible(true);
            } catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void inicializar(){
        setContentPane(Principal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

    }

}
