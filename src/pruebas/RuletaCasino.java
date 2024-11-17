package pruebas;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class RuletaCasino extends JFrame {
    private static final int DIAMETRO = 250; // Tamaño reducido de la ruleta
    private static final int CENTRO_X = 200; // Centro en X
    private static final int CENTRO_Y = 200; // Centro en Y

    private int anguloActual = 0; // Ángulo inicial de la ruleta
    private int resultadoFinal = -1; // Resultado de la ruleta
    private int saldo = 1000; // Saldo inicial del jugador
    private Timer timer;
    
    private String apuesta = ""; // Apuesta seleccionada
    private int cantidadApuesta = 0; // Cantidad apostada

    public RuletaCasino() {
        setTitle("Ruleta de Casino");
        setSize(450, 500); // Tamaño reducido de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de la ruleta
        RuletaPanel panelRuleta = new RuletaPanel();
        add(panelRuleta, BorderLayout.CENTER);

        // Panel de opciones
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new FlowLayout());
        
        // Botones de apuestas
        JButton botonIniciarGiro = new JButton("Iniciar Giro");
        JButton apuestaPar = new JButton("Apostar Par");
        JButton apuestaImpar = new JButton("Apostar Impar");
        JButton apuestaRojo = new JButton("Apostar Rojo");
        JButton apuestaNegro = new JButton("Apostar Negro");
        JButton apuestaNumero = new JButton("Apostar Número");

        // Campo de texto para número específico
        JTextField campoNumero = new JTextField(5);
        
        // Mostrar saldo
        JLabel labelSaldo = new JLabel("Saldo: $" + saldo);
        panelOpciones.add(labelSaldo);

        // Añadir botones al panel
        panelOpciones.add(apuestaPar);
        panelOpciones.add(apuestaImpar);
        panelOpciones.add(apuestaRojo);
        panelOpciones.add(apuestaNegro);
        panelOpciones.add(apuestaNumero);
        panelOpciones.add(campoNumero);
        panelOpciones.add(botonIniciarGiro);

        add(panelOpciones, BorderLayout.SOUTH);

        // Acción al hacer clic en el botón de iniciar giro
        botonIniciarGiro.addActionListener(e -> girarRuleta(panelRuleta, labelSaldo));

        // Acciones para apostar
        apuestaPar.addActionListener(e -> seleccionarApuesta("Par", campoNumero));
        apuestaImpar.addActionListener(e -> seleccionarApuesta("Impar", campoNumero));
        apuestaRojo.addActionListener(e -> seleccionarApuesta("Rojo", campoNumero));
        apuestaNegro.addActionListener(e -> seleccionarApuesta("Negro", campoNumero));
        apuestaNumero.addActionListener(e -> {
            if (!campoNumero.getText().isEmpty()) {
                seleccionarApuesta("Número", campoNumero);
            }
        });

        // Mostrar bienvenida
        JOptionPane.showMessageDialog(this, "Bienvenido a la Ruleta de Casino\nTu saldo inicial es: $" + saldo);
    }

    private void seleccionarApuesta(String tipoApuesta, JTextField campoNumero) {
        if (saldo <= 0) {
            JOptionPane.showMessageDialog(this, "No tienes saldo suficiente para apostar.");
            return;
        }

        String input = JOptionPane.showInputDialog(this, "¿Cuánto deseas apostar?");

        try {
            cantidadApuesta = Integer.parseInt(input);

            if (cantidadApuesta > saldo) {
                JOptionPane.showMessageDialog(this, "No tienes suficiente saldo para esa apuesta.");
                return;
            }

            if (tipoApuesta.equals("Número") && campoNumero.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes ingresar un número.");
                return;
            }

            if (tipoApuesta.equals("Número") && (Integer.parseInt(campoNumero.getText()) < 0 || Integer.parseInt(campoNumero.getText()) > 36)) {
                JOptionPane.showMessageDialog(this, "El número debe estar entre 0 y 36.");
                return;
            }

            apuesta = tipoApuesta;
            if (apuesta.equals("Número")) {
                apuesta += " " + campoNumero.getText();
            }
            JOptionPane.showMessageDialog(this, "Apuesta seleccionada: " + apuesta + "\nMonto: $" + cantidadApuesta);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido.");
        }
    }

    private void girarRuleta(RuletaPanel panelRuleta, JLabel labelSaldo) {
        // Verificar si hay apuesta seleccionada
        if (apuesta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes hacer una apuesta antes de girar.");
            return;
        }

        // Generar resultado aleatorio (0-36)
        resultadoFinal = new Random().nextInt(37);
        int segmentos = 37; // Total de segmentos en la ruleta
        int anguloSegmento = 360 / segmentos;

        // Calcular el ángulo de destino
        int anguloDestino = (360 - (resultadoFinal * anguloSegmento)) % 360;

        // Reiniciar el ángulo inicial
        anguloActual = 0;

        // Animación con un Timer
        timer = new Timer(10, new ActionListener() {
            private int velocidad = 15; // Velocidad de la animación
            private int girosRestantes = 200; // Número de giros antes de desacelerar
            private boolean desacelerando = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (girosRestantes > 0) {
                    anguloActual = (anguloActual + velocidad) % 360;
                    if (!desacelerando && girosRestantes < 40) {
                        desacelerando = true;
                        velocidad = 3; // Reducir la velocidad
                    }
                    girosRestantes--;
                    panelRuleta.repaint();
                } else if (anguloActual != anguloDestino) {
                    anguloActual = (anguloActual + 1) % 360; // Ajuste fino
                    panelRuleta.repaint();
                } else {
                    timer.stop();
                    mostrarResultado(labelSaldo);
                }
            }
        });

        timer.start();
    }

    private void mostrarResultado(JLabel labelSaldo) {
        String resultadoColor = esNumeroRojo(resultadoFinal) ? "Rojo" : (resultadoFinal == 0 ? "Verde" : "Negro");
        boolean gano = false;

        if (apuesta.contains("Par") && resultadoFinal % 2 == 0) {
            gano = true;
        } else if (apuesta.contains("Impar") && resultadoFinal % 2 != 0) {
            gano = true;
        } else if (apuesta.contains("Rojo") && resultadoColor.equals("Rojo")) {
            gano = true;
        } else if (apuesta.contains("Negro") && resultadoColor.equals("Negro")) {
            gano = true;
        } else if (apuesta.contains("Número") && apuesta.equals("Número " + resultadoFinal)) {
            gano = true;
        }

        if (gano) {
            int ganancia = (apuesta.contains("Número") ? 36 : 2) * cantidadApuesta;
            saldo += ganancia;
            JOptionPane.showMessageDialog(this, "¡Ganaste!\nNúmero: " + resultadoFinal + " Color: " + resultadoColor + "\nGanaste: $" + ganancia);
        } else {
            saldo -= cantidadApuesta;
            JOptionPane.showMessageDialog(this, "Perdiste.\nNúmero: " + resultadoFinal + " Color: " + resultadoColor);
        }

        labelSaldo.setText("Saldo: $" + saldo);
        apuesta = ""; // Restablecer apuesta
    }

    private class RuletaPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int segmentos = 37; // Total de números en la ruleta
            int anguloSegmento = 360 / segmentos;

            // Dibujar los segmentos de la ruleta
            for (int i = 0; i < segmentos; i++) {
                g2d.setColor(i % 2 == 0 ? Color.RED : Color.BLACK); // Rojo y negro alternados
                g2d.fillArc(CENTRO_X - DIAMETRO / 2, CENTRO_Y - DIAMETRO / 2, DIAMETRO, DIAMETRO, anguloSegmento * i, anguloSegmento);
            }

            // Dibujar la flecha
            g2d.setColor(Color.BLACK);
            g2d.fillPolygon(new int[]{CENTRO_X, CENTRO_X - 10, CENTRO_X + 10}, new int[]{CENTRO_Y - DIAMETRO / 2, CENTRO_Y - DIAMETRO / 4, CENTRO_Y - DIAMETRO / 4}, 3);

            // Dibujar el número de la ruleta
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString(String.valueOf(resultadoFinal), CENTRO_X - 10, CENTRO_Y);
        }
    }

    // Determinar si un número está en un segmento rojo
    private boolean esNumeroRojo(int numero) {
        int[] rojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int num : rojos) {
            if (num == numero) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        RuletaCasino ventana = new RuletaCasino();
        ventana.setVisible(true);
    }
}












