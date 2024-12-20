package principal;

import blackjack.BlackjackGUI;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;
import memento.Memento;
import ruleta.RuletaCasino;
import tragamonedas.Tragamonedas;
import usuario.Usuario;

public class Casino extends JFrame {
    private JButton blackJackButton;
    private JButton ruletaButton;
    private JPanel juegos;
    private JPanel cuenta;
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
            bj.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e) {
                    saldo.setText("Saldo : " + usuario.getCartera().getSaldo()); // Actualizar saldo
                }
            });
            bj.setVisible(true);
        });

        ruletaButton.addActionListener(e -> {
            RuletaCasino ruletaCasino = new RuletaCasino(usuario);
            historial.add(usuario.crearMemento());
            ruletaCasino.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e) {
                    saldo.setText("Saldo : " + usuario.getCartera().getSaldo()); // Actualizar saldo
                }
            });
            ruletaCasino.setVisible(true);
        } );

        tragamonedasButton.addActionListener(e -> {
            Tragamonedas tragamonedas = new Tragamonedas(usuario);
            historial.add(usuario.crearMemento());
            tragamonedas.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e) {
                    saldo.setText("Saldo : " + usuario.getCartera().getSaldo()); // Actualizar saldo
                }
            });
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
            }
        });
        cambiarDeCuentaButton.addActionListener(e -> {
            dispose();
        });
        ingresarDineroButton.addActionListener(e -> {
            String ingresar = JOptionPane.showInputDialog(this, "Ingresa la cantidad que deseas ingresar",
                    "Ingreso de dinero", JOptionPane.PLAIN_MESSAGE);
            try{
                usuario.getCartera().agregarSaldoCasino(Double.parseDouble(ingresar));
                saldo.setText("Saldo : " + usuario.getCartera().getSaldo());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,"Entrada invalida.");
            }
        });
        historialButton.addActionListener(e -> {
            //historial.add(new Memento(120, (new ArrayList<>()).add("hola")));
            mostrarHistorial();
        });
    }

    public void inicializar() {
        setContentPane(casino);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private void mostrarHistorial() {
        if (historial.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El historial está vacío.", "Historial", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Memento ultimo = historial.get(historial.size() - 1);
            StringBuilder builder = new StringBuilder();
            for (String cambios : ultimo.getHistorial()) {
                builder.append(cambios).append("\n");
            }
            JOptionPane.showMessageDialog(null, builder.toString(), "Historial", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
