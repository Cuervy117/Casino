package ruleta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import usuario.*;
import memento.*;

public class RuletaCasino extends JFrame {
    private static final int DIAMETRO = 300;
    private static final int CENTRO_X = 200;
    private static final int CENTRO_Y = 200;

    private int anguloActual = 0; // Ángulo inicial
    private int resultadoFinal = -1; // Posición final de la ruleta
    private Timer timer;
    private Usuario usuario;

    // Opciones de apuesta
    private JComboBox<String> opcionesApuesta;
    private JLabel saldoLabel;
    private double saldo = 1000; // Saldo inicial
    private JTextField cantidadApuestaField; // Campo para ingresar cantidad de apuesta

    public RuletaCasino() {
        setTitle("Ruleta de Casino");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para la ruleta
        RuletaPanel panelRuleta = new RuletaPanel();
        add(panelRuleta, BorderLayout.CENTER);

        // Panel inferior para opciones de apuesta
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(4, 2, 10, 10));

        // Opciones de apuesta
        panelOpciones.add(new JLabel("Seleccione tipo de apuesta:"));
        opcionesApuesta = new JComboBox<>(new String[]{
                "Par", "Impar", "Rojo", "Negro"
        });
        panelOpciones.add(opcionesApuesta);

        // Campo para ingresar la cantidad de apuesta
        panelOpciones.add(new JLabel("Cantidad de apuesta:"));
        cantidadApuestaField = new JTextField();
        panelOpciones.add(cantidadApuestaField);

        // Saldo y botón para girar
        saldoLabel = new JLabel("Saldo: $" + saldo);
        panelOpciones.add(saldoLabel);
        JButton botonGirar = new JButton("Girar la Ruleta");
        panelOpciones.add(botonGirar);

        add(panelOpciones, BorderLayout.SOUTH);

        botonGirar.addActionListener(e -> girarRuleta(panelRuleta));
    }

    private void girarRuleta(RuletaPanel panelRuleta) {
        double cantidadApuesta = obtenerCantidadApuesta();
        if (cantidadApuesta <= 0) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una apuesta válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cantidadApuesta > saldo) {
            JOptionPane.showMessageDialog(this, "No tienes suficiente saldo para hacer esa apuesta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }



        resultadoFinal = new Random().nextInt(37); // Generar resultado aleatorio (0-36)
        anguloActual = 0; // Resetear ángulo

        // Configurar el timer para animar la ruleta
        timer = new Timer(10, new ActionListener() {
            private int velocidad = 20;
            private int girosRestantes = 100;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (girosRestantes > 0) {
                    anguloActual = (anguloActual + velocidad) % 360;
                    velocidad = Math.max(1, velocidad - 1);
                    girosRestantes--;

                    // Actualizar el ángulo en el panel de la ruleta
                    panelRuleta.setAnguloActual(anguloActual);
                } else {
                    timer.stop();
                    calcularResultado(cantidadApuesta);
                }
            }
        });

        timer.start();
    }

    private double obtenerCantidadApuesta() {
        try {
            return Double.parseDouble(cantidadApuestaField.getText());
        } catch (NumberFormatException e) {
            return -1; // Si no se ingresa un número válido, devolver un valor negativo
        }
    }

    private void calcularResultado(double cantidadApuesta) {
        String seleccion = (String) opcionesApuesta.getSelectedItem();
        double ganancia = 0;

        boolean esPar = resultadoFinal != 0 && resultadoFinal % 2 == 0;
        boolean esRojo = esNumeroRojo(resultadoFinal);

        switch (seleccion) {
            case "Par":
                if (esPar) ganancia = 2; // Paga 2x
                break;
            case "Impar":
                if (!esPar && resultadoFinal != 0) ganancia = 2; // Paga 2x
                break;
            case "Rojo":
                if (esRojo) ganancia = 2; // Paga 2x
                break;
            case "Negro":
                if (!esRojo && resultadoFinal != 0) ganancia = 2; // Paga 2x
                break;
        }

        if (ganancia > 0) {
            saldo += cantidadApuesta * ganancia; // Ganancia basada en la cantidad apostada
            JOptionPane.showMessageDialog(this, "¡Ganaste! Resultado: " + resultadoFinal +
                    " Ganaste $" + (cantidadApuesta * ganancia), "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            saldo -= cantidadApuesta; // Pérdida de la apuesta
            JOptionPane.showMessageDialog(this, "Perdiste. Resultado: " + resultadoFinal,
                    "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }

        saldoLabel.setText("Saldo: $" + saldo);

        if (saldo <= 0) {
            JOptionPane.showMessageDialog(this, "Te has quedado sin saldo.", "Saldo Insuficiente",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    static boolean esNumeroRojo(int numero) {
        int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int n : numerosRojos) {
            if (n == numero) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RuletaCasino ruleta = new RuletaCasino();
            ruleta.setVisible(true);
        });
    }
}




