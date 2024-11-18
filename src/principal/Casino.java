package principal;

import javax.swing.*;

import blackjack.BlackjackGUI;
import memento.Memento;
import ruleta.RuletaCasino;
import tragamonedas.Tragamonedas;
import usuario.Usuario;

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
    private final Usuario usuario;
    
    private ArrayList<Memento> historial = new ArrayList<>();

    public Casino(Usuario usuario) {
        inicializar();
        this.usuario = usuario;

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
    }

    public void inicializar() {
        setContentPane(casino);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
