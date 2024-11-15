package principal;

import javax.swing.*;

import sistemaLogin.BaseDeDatos;
import usuario.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearCuenta extends JFrame {
    private JPanel crearCuenta;
    private JTextField usuario;
    private JTextField correo;
    private JPasswordField contraseña;
    private JButton crearButton;

    public CrearCuenta() {
        inicializarPantalla();
        crearButton.addActionListener(e -> {
            BaseDeDatos.agregarUsuario(new Usuario(null, usuario.getText(), correo.getText(),
                    null, new String(contraseña.getPassword())));

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
        JOptionPane.showMessageDialog(this,BaseDeDatos.getBaseDeDatos()
                .getUsuarios().get(usuario.getText()).toString());
        dispose();
    }
    private void inicializarPantalla() {
        setContentPane(crearCuenta);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
