package principal;

import javax.swing.*;

import blackjack.BlackjackGUI;
import memento.Memento;
import ruleta.RuletaCasino;
import tragamonedas.Tragamonedas;
import usuario.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Casino extends JFrame {
    private JButton blackJackButton;
    private JButton ruletaButton;
    private JPanel juegos;
    private JPanel cuenta;
    private JPanel apuestas;
    private JButton tragamonedasButton;
    private JButton ingresarDineroButton;
    private JButton cambiarDeCuentaButton;
    private JButton retirarDineroButton;
    private JButton desactivarCuentaButton;
    private JButton historialButton;
    private JPanel casino;
    private JLabel saldo;
    private JLabel nombre;
    private final Usuario usuario;

    private ArrayList<Memento> historial = new ArrayList<>();

    public Casino(Usuario usuario) {
        inicializar();
        this.usuario = usuario;
        saldo.setText("Saldo : " + usuario.getCartera().getSaldo());
        nombre.setText("Nombre : " + usuario.getNombre());
        blackJackButton.addActionListener(e -> {
            BlackjackGUI bj = new BlackjackGUI(usuario);
            historial.add(usuario.crearMemento());
            bj.setVisible(true);
        });

        ruletaButton.addActionListener(e -> {
            RuletaCasino ruletaCasino = new RuletaCasino();
            historial.add(usuario.crearMemento());
            ruletaCasino.setVisible(true);
        } );

        tragamonedasButton.addActionListener(e -> {
            Tragamonedas tragamonedas = new Tragamonedas(usuario);
            historial.add(usuario.crearMemento());
            tragamonedas.setVisible(true);
        });
        retirarDineroButton.addActionListener(e -> {
            String retirar = JOptionPane.showInputDialog(this, "Ingresa la cantidad que deseas retirar",
                    "Retiro de dinero", JOptionPane.PLAIN_MESSAGE);
            try{
                usuario.getCartera().retirarSaldoCasino(Double.parseDouble(retirar));
                saldo.setText("Saldo : " + usuario.getCartera().getSaldo());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,"Entrada invalida.");
            }
        });
        desactivarCuentaButton.addActionListener(e -> {

            if(JOptionPane.showConfirmDialog(this, "¿Seguro que deseas desactivar tu cuenta?") == 1){
                usuario.desactivarCuenta();
                JFrame.EXIT_ON_CLOSE;
            }
        });
    }

    public void inicializar() {
        setContentPane(casino);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
