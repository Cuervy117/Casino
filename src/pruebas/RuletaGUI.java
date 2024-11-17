package pruebas;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RuletaGUI extends JFrame {
    private UsuarioRuleta usuario;
    private Ruleta ruleta;
    private JLabel mensaje;
    private JLabel saldoLabel;
    private JPanel ruletaPanel;
    private Timer animacionTimer;
    private int angulo;

    public RuletaGUI(UsuarioRuleta usuario) {
        this.usuario = usuario;
        this.ruleta = new Ruleta();
        configurarInterfaz();
    }

    private void configurarInterfaz() {
        setTitle("Ruleta de Casino");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: Mensaje y saldo
        JPanel panelSuperior = new JPanel(new BorderLayout());
        mensaje = new JLabel("Bienvenido, " + usuario.getNombre() + "!");
        saldoLabel = new JLabel("Saldo: $" + usuario.getSaldo());
        panelSuperior.add(mensaje, BorderLayout.NORTH);
        panelSuperior.add(saldoLabel, BorderLayout.SOUTH);

        // Panel central: Animación de la ruleta
        ruletaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;

                // Dibujar ruleta
                g2d.setColor(Color.BLACK);
                g2d.fillOval(centerX - 100, centerY - 100, 200, 200);

                // Dividir la ruleta en segmentos
                g2d.setColor(Color.RED);
                for (int i = 0; i < 360; i += 30) {
                    g2d.fillArc(centerX - 100, centerY - 100, 200, 200, angulo + i, 15);
                }
            }
        };

        // Botón para girar la ruleta
        JButton girarButton = new JButton("Girar Ruleta");
        girarButton.addActionListener(e -> iniciarGiro());

        add(panelSuperior, BorderLayout.NORTH);
        add(ruletaPanel, BorderLayout.CENTER);
        add(girarButton, BorderLayout.SOUTH);
    }

    private void iniciarGiro() {
        if (animacionTimer != null && animacionTimer.isRunning()) {
            return; // Prevenir múltiples giros simultáneos
        }

        angulo = 0;
        animacionTimer = new Timer(50, new ActionListener() {
            int tiempo = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                angulo += 15; // Incrementar ángulo
                ruletaPanel.repaint();
                tiempo += 50;

                if (tiempo >= 2000) { // Animación dura 2 segundos
                    animacionTimer.stop();
                    ruleta.girar();
                    mostrarResultado();
                }
            }
        });
        animacionTimer.start();
    }

    private void mostrarResultado() {
        int numero = ruleta.getNumero();
        String color = ruleta.getColor();
        mensaje.setText("Número: " + numero + ", Color: " + color);

        // Actualizar saldo (simulación de ganancia)
        double ganancia = 100; // Ejemplo
        usuario.actualizarSaldo(ganancia);
        saldoLabel.setText("Saldo: $" + usuario.getSaldo());
    }

    public static void main(String[] args) {
        UsuarioRuleta usuario = new UsuarioRuleta("Jugador1", 1000);
        SwingUtilities.invokeLater(() -> new RuletaGUI(usuario).setVisible(true));
    }
}













