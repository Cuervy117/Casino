package principal;

import javax.swing.*;
import memento.*;
import metodosDePago.Cartera;
import tiposDeCambio.PagoEnPesos;
import usuario.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearCuenta extends JFrame {
    private JPanel crearCuenta;
    private JTextField usuario;
    private JTextField correo;
    private JPasswordField contraseña;
    private JButton crearButton;
    //Atributos de clase
    private GestorUsuarios gestorUsuarios;
    public CrearCuenta(GestorUsuarios admin) {
        this.gestorUsuarios = admin;
        inicializarPantalla();
        crearButton.addActionListener(e -> {
            try {
                gestorUsuarios.registrarUsuario(new Usuario(null, usuario.getText(), correo.getText(),
                        new Cartera(0, new PagoEnPesos()), new String(contraseña.getPassword())));
                JOptionPane.showMessageDialog(this, "Cuenta creada con exito", "Felcidades", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            mostrarUsuario();
        });
        usuario.addActionListener(e -> correo.requestFocusInWindow());
        correo.addActionListener(e -> contraseña.requestFocusInWindow());
        contraseña.addActionListener(e -> {
            crearButton.requestFocusInWindow();
            crearButton.doClick();
        });
    }

    public void mostrarUsuario() {
        //JOptionPane.showMessageDialog(this.BaseDeDatos.getBaseDeDatos()
                //.getUsuarios().get(usuario.getText()).toString());
        dispose();
    }
    private void inicializarPantalla() {
        setContentPane(crearCuenta);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
