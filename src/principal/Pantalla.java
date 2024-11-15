package principal;

import javax.swing.*;

public class Pantalla extends JFrame {
    private JTextField usuario;
    private JPasswordField contraseña;
    private JButton entrar;
    private JPanel Principal;
    private JPanel Titulo;
    private JPanel Entrada;
    private JButton crearCuentaButton;

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
            Casino casino = new Casino();
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
