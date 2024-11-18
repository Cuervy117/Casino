package principal;

import blackjack.BlackjackGUI;
import java.util.ArrayList;
import javax.swing.*;
import memento.Memento;
import ruleta.*;
import tragamonedas.Tragamonedas;
import usuario.Usuario;

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
        //Agregando funcionalidad de ruleta
        ruletaButton.addActionListener(e ->{ 
            RuletaCasino rc = new RuletaCasino(usuario);
        });

        ruletaButton.addActionListener(e -> {
            RuletaCasino ruletaCasino = new RuletaCasino(usuario);
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

        ruletaButton.addActionListener(e -> {
            RuletaCasino ruletaCasino = new RuletaCasino(usuario);
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
    }

    public void inicializar() {
        setContentPane(casino);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
