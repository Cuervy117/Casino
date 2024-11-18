package principal;

import javax.swing.*;

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

        });
        usuario.addActionListener(e -> correo.requestFocusInWindow());
        correo.addActionListener(e -> contraseña.requestFocusInWindow());
        contraseña.addActionListener(e -> {
            crearButton.requestFocusInWindow();
            crearButton.doClick();
        });
    }

    private void inicializarPantalla() {
        setContentPane(crearCuenta);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
