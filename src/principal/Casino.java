package principal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import blackjack.BlackJack;
import blackjack.BlackjackGUI;
import ruleta.RuletaCasino;

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

    public Casino() {
        inicializar();
        blackJackButton.addActionListener(e -> {
            BlackjackGUI bj = new BlackjackGUI();
            bj.setVisible(true);
        });

        ruletaButton.addActionListener(e -> {
            RuletaCasino ruletaCasino = new RuletaCasino();
            ruletaCasino.setVisible(true);
        } );
    }

    public void inicializar() {
        setContentPane(casino);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
