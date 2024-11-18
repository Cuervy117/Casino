package ruleta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.random.*;
public class Ruleta extends JDialog implements Juego {
    // Atributos del form para la interfaz
    private JPanel ruleta;
    private JTextField montoApuesta;
    private JComboBox<String> tipoApuesta;
    private JButton girar;
    private JTextArea mensajes;
    private JScrollPane historialApuestas;
    private JPanel animacion; // Panel donde se dibuja la ruleta

    // Atributos propios del juego
    private int anguloActual = 0; // Ángulo inicial
    private int resultadoFinal = -1; // Posición final de la ruleta
    private Timer timer;
    private double saldo = 1000; // Saldo inicial

    public Ruleta(JFrame parent) {
        super(parent);
        inicializarPantalla();
        RuletaPanel ruletaPanel = new RuletaPanel();
        girar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarApuesta(); // Aquí manejamos la apuesta
                girarRuleta(ruletaPanel); // Luego giramos la ruleta
            }
        });
    }

    public static void main(String[] args) {
        Ruleta r = new Ruleta(null);
    }

    // Lógica de diseño del cuadro que aparece en pantalla
    private void inicializarPantalla() {
        setTitle("Ruleta");
        setContentPane(ruleta);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(getParent());

        // Inicializar la interfaz
        setVisible(true);

        // Inicializar el JTextArea y JScrollPane
        mensajes = new JTextArea();
        mensajes.setEditable(false);
        historialApuestas = new JScrollPane(mensajes);
        historialApuestas.setPreferredSize(new Dimension(400, 100));

        // Añadir al panel de la interfaz
        ruleta.add(historialApuestas, BorderLayout.SOUTH);
    }

    // Lógica para manejar la apuesta
    @Override
    public void manejarApuesta() {
        try {
            // Leer la apuesta del jugador
            double apuesta = Double.parseDouble(montoApuesta.getText());

            if (apuesta <= 0) {
                JOptionPane.showMessageDialog(this, "La apuesta debe ser mayor a cero.");
                return;
            }

            if (apuesta > saldo) {
                JOptionPane.showMessageDialog(this, "No tienes suficiente saldo.");
                return;
            }

            // Decrementar el saldo del jugador
            saldo -= apuesta;

            // Actualizar el historial de apuestas
            actualizarHistorial("Apuesta de $" + apuesta + " realizada.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un monto válido.");
        }
    }

    // Lógica para determinar el resultado del juego
    private String obtenerResultadoMensaje() {
        // Lógica simple para verificar el resultado basado en el número que salió
        if (resultadoFinal == 0) {
            return "Perdiste, es el 0 (verde)";
        } else if (resultadoFinal % 2 == 0) {
            return "Perdiste, el número es par.";
        } else {
            return "Ganaste, el número es impar!";
        }
    }

    // Método para girar la ruleta y animar
    private void girarRuleta(RuletaPanel panelRuleta) {
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
                    calcularResultado();
                }
            }
        });

        timer.start();
    }

    // Método para calcular el resultado final
    private void calcularResultado() {
        String seleccion = (String) tipoApuesta.getSelectedItem();
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
            saldo += 10 * ganancia; // Ganancia basada en apuesta fija de $10
            JOptionPane.showMessageDialog(this, "¡Ganaste! Resultado: " + resultadoFinal +
                    " Ganaste $" + (10 * ganancia), "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            saldo -= 10; // Pérdida de $10
            JOptionPane.showMessageDialog(this, "Perdiste. Resultado: " + resultadoFinal,
                    "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }

        // Actualizar el saldo
        actualizarHistorial("Resultado: " + obtenerResultadoMensaje());

        // Comprobar si el saldo es insuficiente
        if (saldo <= 0) {
            JOptionPane.showMessageDialog(this, "Te has quedado sin saldo.", "Saldo Insuficiente",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para actualizar el historial
    private void actualizarHistorial(String mensaje) {
        String nuevoHistorial = mensaje + "\nSaldo: $" + saldo + "\n" + mensajes.getText(); // Concatenar el mensaje con el saldo
        mensajes.setText(nuevoHistorial); // Actualizar el JTextArea con el nuevo historial
        historialApuestas.setViewportView(mensajes); // Asegurar que el JScrollPane esté mostrando el JTextArea actualizado
    }

    // Método para verificar si el número es rojo
    static boolean esNumeroRojo(int numero) {
        int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int n : numerosRojos) {
            if (n == numero) return true;
        }
        return false;
    }

    @Override
    public void jugar() {
        // Aquí podemos agregar lógica de inicio de juego o reiniciar apuestas si se desea
        JOptionPane.showMessageDialog(this, "Comienza un nuevo juego.");
    }
}





