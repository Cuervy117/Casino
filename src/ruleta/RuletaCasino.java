package ruleta;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import metodosDePago.Cartera;
import tiposDeCambio.Pago;
import tiposDeCambio.PagoEnPesos;
import usuario.*;

public class RuletaCasino extends JFrame {
    private int anguloActual = 0; // Ángulo inicial
    private int resultadoFinal = -1; // Posición final de la ruleta
    private Timer timer;
    private Usuario usuario;

    // Opciones de apuesta
    private JComboBox<String> opcionesApuesta;
    private JLabel saldoLabel;
    private double saldo; // Saldo inicial
    private JTextField cantidadApuestaField; // Campo para ingresar cantidad de apuesta
    private JTextField numeroEspecificoField; // Campo para ingresar el número específico

    public RuletaCasino(Usuario usuario) {
        this.usuario = usuario;
        this.saldo = usuario.getCartera().getSaldo();
        setTitle("Ruleta de Casino");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para la ruleta
        RuletaPanel panelRuleta = new RuletaPanel();
        add(panelRuleta, BorderLayout.CENTER);

        // Panel inferior para opciones de apuesta
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(5, 2, 10, 10));

        // Opciones de apuesta
        panelOpciones.add(new JLabel("Seleccione tipo de apuesta:"));
        opcionesApuesta = new JComboBox<>(new String[]{
                "Par", "Impar", "Rojo", "Negro", "Número Específico", "1-18", "19-36"
        });
        panelOpciones.add(opcionesApuesta);

        // Campo para ingresar la cantidad de apuesta
        panelOpciones.add(new JLabel("Cantidad de apuesta:"));
        cantidadApuestaField = new JTextField();
        panelOpciones.add(cantidadApuestaField);

        // Campo para el número específico
        panelOpciones.add(new JLabel("Número específico (0-36):"));
        numeroEspecificoField = new JTextField();
        numeroEspecificoField.setEnabled(false); // Deshabilitar inicialmente
        panelOpciones.add(numeroEspecificoField);

        // Saldo y botón para girar
        saldoLabel = new JLabel("Saldo: $" + saldo);
        panelOpciones.add(saldoLabel);
        JButton botonGirar = new JButton("Girar la Ruleta");
        panelOpciones.add(botonGirar);

        add(panelOpciones, BorderLayout.SOUTH);

        // ActionListener para habilitar/deshabilitar el campo del número específico
        opcionesApuesta.addActionListener(e -> {
            String seleccion = (String) opcionesApuesta.getSelectedItem();
            if ("Número Específico".equals(seleccion)) {
                numeroEspecificoField.setEnabled(true); // Habilitar
            } else {
                numeroEspecificoField.setEnabled(false); // Deshabilitar
            }
        });

        botonGirar.addActionListener(e -> girarRuleta(panelRuleta));
    }

    private void girarRuleta(RuletaPanel panelRuleta) {
        double cantidadApuesta = obtenerCantidadApuesta();
        if (cantidadApuesta <= 0) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una apuesta válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            usuario.getCartera().realizarPago(cantidadApuesta);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            case "Número Específico":
                int numeroEspecifico = obtenerNumeroEspecifico();
                if (resultadoFinal == numeroEspecifico) ganancia = 35; // Paga 35x
                break;
            case "1-18":
                if (resultadoFinal >= 1 && resultadoFinal <= 18) ganancia = 2; // Paga 2x
                break;
            case "19-36":
                if (resultadoFinal >= 19 && resultadoFinal <= 36) ganancia = 2; // Paga 2x
                break;
        }

        if (ganancia > 0) {
            saldo = usuario.getCartera().getSaldo(); // Ganancia basada en la cantidad apostada
            usuario.getCartera().agregarSaldoCasino(cantidadApuesta * ganancia);
            JOptionPane.showMessageDialog(this, "¡Ganaste! Resultado: " + resultadoFinal +
                    " Ganaste $" + (cantidadApuesta * ganancia), "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Pérdida de la apuesta
            saldo = usuario.getCartera().getSaldo();
            JOptionPane.showMessageDialog(this, "Perdiste. Resultado: " + resultadoFinal,
                    "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }

        saldoLabel.setText("Saldo: $" + saldo);

        if (saldo <= 0) {
            JOptionPane.showMessageDialog(this, "Te has quedado sin saldo.", "Saldo Insuficiente",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private int obtenerNumeroEspecifico() {
        try {
            return Integer.parseInt(numeroEspecificoField.getText());
        } catch (NumberFormatException e) {
            return -1; // Si no se ingresa un número válido, devolver un valor negativo
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
            Pago pesosMexicanos = new PagoEnPesos();
            Usuario user1 = new Usuario("123", "Samuel", "santi.sam120@gmail.com", new Cartera(500, pesosMexicanos), "123");
            RuletaCasino ruleta = new RuletaCasino(user1);
            ruleta.setVisible(true);
        });
    }
}






