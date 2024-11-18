package principal;

import blackjack.BlackjackGUI;
import javax.swing.*;
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
        //Agregando funcionalidad de ruleta
        ruletaButton.addActionListener(e ->{ 
            RuletaCasino rc = new RuletaCasino(null);
        });
    }

    public void inicializar() {
        setContentPane(casino);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
