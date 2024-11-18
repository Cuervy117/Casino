package ruleta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RuletaCasino extends JFrame {
    private static final int DIAMETRO = 360; // Tamaño de la ruleta
    private static final int CENTRO_X = 250; // Centro en X
    private static final int CENTRO_Y = 250; // Centro en Y

    private int anguloActual = 0; // Ángulo inicial de la ruleta
    private int resultadoFinal = -1; // Resultado de la ruleta
    private Timer timer;

    public RuletaCasino() {
        setTitle("Ruleta de Casino");
        setSize(550, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de la ruleta
        RuletaPanel panelRuleta = new RuletaPanel();
        add(panelRuleta, BorderLayout.CENTER);

        // Panel de opciones
        JPanel panelOpciones = new JPanel();
        JButton botonGirar = new JButton("Girar la Ruleta");
        panelOpciones.add(botonGirar);
        add(panelOpciones, BorderLayout.SOUTH);

        botonGirar.addActionListener(e -> girarRuleta(panelRuleta));
    }

    private void girarRuleta(RuletaPanel panelRuleta) {
        resultadoFinal = new Random().nextInt(37); // Generar resultado aleatorio (0-36)
        int segmentos = 37; // Total de segmentos en la ruleta
        int anguloSegmento = 360 / segmentos;

        // Calcular el ángulo de destino
        int anguloDestino = (360 - (resultadoFinal * anguloSegmento)) % 360;

        // Reiniciar el ángulo inicial
        anguloActual = 0;

        // Animación con un Timer
        timer = new Timer(10, new ActionListener() {
            private int velocidad = 20;
            private int girosRestantes = 150; // Giros antes de desacelerar
            private boolean desacelerando = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (girosRestantes > 0) {
                    anguloActual = (anguloActual + velocidad) % 360;
                    if (!desacelerando && girosRestantes < 40) {
                        desacelerando = true;
                        velocidad = 5; // Reducir la velocidad
                    }
                    girosRestantes--;
                    panelRuleta.repaint();
                } else if (anguloActual != anguloDestino) {
                    anguloActual = (anguloActual + 1) % 360; // Ajuste fino
                    panelRuleta.repaint();
                } else {
                    timer.stop();
                    JOptionPane.showMessageDialog(RuletaCasino.this,
                            "Número ganador: " + resultadoFinal,
                            "Resultado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        timer.start();
    }

    private class RuletaPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int segmentos = 37; // Total de números en la ruleta
            int anguloSegmento = 360 / segmentos;

            // Dibujar cada segmento
            for (int i = 0; i < segmentos; i++) {
                if (i == 0) {
                    g2d.setColor(Color.GREEN); // Segmento del 0
                } else if (esNumeroRojo(i)) {
                    g2d.setColor(Color.RED);
                } else {
                    g2d.setColor(Color.BLACK);
                }

                g2d.fillArc(CENTRO_X - DIAMETRO / 2, CENTRO_Y - DIAMETRO / 2, DIAMETRO, DIAMETRO,
                        anguloActual + i * anguloSegmento, anguloSegmento);

                // Dibujar números en cada segmento
                g2d.setColor(Color.WHITE);
                double anguloRad = Math.toRadians(anguloActual + (i * anguloSegmento) + anguloSegmento / 2);
                int xNumero = (int) (CENTRO_X + (DIAMETRO / 2.5) * Math.cos(anguloRad));
                int yNumero = (int) (CENTRO_Y - (DIAMETRO / 2.5) * Math.sin(anguloRad));
                g2d.drawString(String.valueOf(i), xNumero - 10, yNumero + 5);
            }

            // Dibujar el borde de la ruleta
            g2d.setColor(Color.BLACK);
            g2d.drawOval(CENTRO_X - DIAMETRO / 2, CENTRO_Y - DIAMETRO / 2, DIAMETRO, DIAMETRO);

            // Dibujar el marcador (flecha)
            g2d.setColor(Color.BLUE);
            g2d.fillPolygon(
                    new int[]{CENTRO_X, CENTRO_X - 15, CENTRO_X + 15},
                    new int[]{CENTRO_Y - DIAMETRO / 2 - 10, CENTRO_Y - DIAMETRO / 2 - 40, CENTRO_Y - DIAMETRO / 2 - 40},
                    3
            );
        }

        private boolean esNumeroRojo(int numero) {
            int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
            for (int n : numerosRojos) {
                if (n == numero) return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RuletaCasino ruleta = new RuletaCasino();
            ruleta.setVisible(true);
        });
    }
}


