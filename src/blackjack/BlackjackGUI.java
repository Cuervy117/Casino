package blackjack;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BlackjackGUI extends JFrame {
    private JTextArea textArea;
    private JPanel panelCartasJugador;
    private JPanel panelCartasCrupier;
    private Baraja baraja;
    private ArrayList<Carta> manoJugador;
    private ArrayList<Carta> manoCrupier;
    private double apuesta;
    private double seguro;

    public BlackjackGUI() {
        super("BlackJack");
        inicializarJuego();
        inicializarInterfaz();
    }

    private void inicializarJuego() {
        baraja = new Baraja();
        manoJugador = new ArrayList<>();
        manoCrupier = new ArrayList<>();
        apuesta = 0;
        seguro = 0;
    }

    private void inicializarInterfaz() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelCartas = new JPanel();
        panelCartas.setLayout(new GridLayout(2, 1, 10, 10));

        panelCartasJugador = new JPanel();
        panelCartasJugador.setBorder(BorderFactory.createTitledBorder("Tus cartas"));
        panelCartasJugador.setLayout(new FlowLayout());
        panelCartas.add(panelCartasJugador);

        panelCartasCrupier = new JPanel();
        panelCartasCrupier.setBorder(BorderFactory.createTitledBorder("Cartas del crupier"));
        panelCartasCrupier.setLayout(new FlowLayout());
        panelCartas.add(panelCartasCrupier);

        add(panelCartas, BorderLayout.CENTER);

        JPanel panelTexto = new JPanel(new BorderLayout());
        panelTexto.setPreferredSize(new Dimension(250, getHeight()));
        panelTexto.setBorder(BorderFactory.createTitledBorder("Información"));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panelTexto.add(scrollPane, BorderLayout.CENTER);
        add(panelTexto, BorderLayout.EAST);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 5));

        JButton btnVerManoJugador = new JButton("Ver mi mano");
        JButton btnVerManoCrupier = new JButton("Ver mano del crupier");
        JButton btnPagarSeguro = new JButton("Pagar seguro");
        JButton btnPedirCarta = new JButton("Pedir carta");
        JButton btnPlantarse = new JButton("Plantarse");

        panelBotones.add(btnVerManoJugador);
        panelBotones.add(btnVerManoCrupier);
        panelBotones.add(btnPagarSeguro);
        panelBotones.add(btnPedirCarta);
        panelBotones.add(btnPlantarse);

        add(panelBotones, BorderLayout.SOUTH);

        btnVerManoJugador.addActionListener(e -> mostrarMano(manoJugador, "Tu mano", panelCartasJugador));
        btnVerManoCrupier.addActionListener(e -> mostrarMano(manoCrupier, "Mano del crupier", panelCartasCrupier));
        btnPagarSeguro.addActionListener(e -> pagarSeguro());
        btnPedirCarta.addActionListener(e -> pedirCarta());
        btnPlantarse.addActionListener(e -> plantarse());

        mostrarMensaje("¡Bienvenido a BlackJack! Inicia el juego apostando una cantidad.");
        realizarApuestaInicial();
    }

    private void realizarApuestaInicial() {
        String input = JOptionPane.showInputDialog(this, "Ingrese la cantidad que desea apostar:", "Apuesta inicial", JOptionPane.PLAIN_MESSAGE);
        try {
            apuesta = Double.parseDouble(input);
            mostrarMensaje("Has apostado $" + apuesta);

            manoJugador.add(baraja.removeFirst());
            manoCrupier.add(baraja.removeFirst());
            manoJugador.add(baraja.removeFirst());

            mostrarMensaje("Carta en tu mano: " + manoJugador.get(0));
            mostrarMensaje("Carta del crupier: " + manoCrupier.get(0));
            mostrarMensaje("Segunda carta en tu mano: " + manoJugador.get(1));

            actualizarPanelCartas(panelCartasJugador, manoJugador);
            actualizarPanelCartas(panelCartasCrupier, manoCrupier);

            if (calcularValorDeMano(manoJugador) == 21) {
                manejarApuesta(1);
            }
        } catch (NumberFormatException ex) {
            mostrarMensaje("Entrada inválida. Por favor, ingrese un número.");
            realizarApuestaInicial();
        }
    }

    private void mostrarMano(ArrayList<Carta> mano, String titulo, JPanel panel) {
        StringBuilder builder = new StringBuilder(titulo + ":\n");
        for (Carta carta : mano) {
            builder.append(carta).append("\n");
        }
        builder.append("Valor total: ").append(calcularValorDeMano(mano));
        mostrarMensaje(builder.toString());

        actualizarPanelCartas(panel, mano);
    }

    private void pagarSeguro() {
        if (manoCrupier.get(0).getNumero().equals("As")) {
            seguro = apuesta / 2;
            mostrarMensaje("Has pagado $" + seguro + " como seguro.");
        } else {
            mostrarMensaje("Solo puedes pagar seguro si la primera carta del crupier es un As.");
        }
    }

    private void pedirCarta() {
        if (calcularValorDeMano(manoJugador) < 21) {
            Carta nuevaCarta = baraja.removeFirst();
            manoJugador.add(nuevaCarta);
            mostrarMensaje("Has obtenido: " + nuevaCarta);
            actualizarPanelCartas(panelCartasJugador, manoJugador);

            if (calcularValorDeMano(manoJugador) > 21) {
                manejarApuesta(3);
            }
        } else {
            mostrarMensaje("No puedes pedir más cartas, ya tienes 21.");
        }
    }

    private void plantarse() {
        while (calcularValorDeMano(manoCrupier) <= 17) {
            Carta nuevaCarta = baraja.removeFirst();
            manoCrupier.add(nuevaCarta);
            mostrarMensaje("Nueva carta del crupier: " + nuevaCarta);
            actualizarPanelCartas(panelCartasCrupier, manoCrupier);
        }

        int resultado = determinarResultado();
        manejarApuesta(resultado);
    }

    private void manejarApuesta(int resultado) {
        switch (resultado) {
            case 1 -> mostrarMensaje("¡Has ganado! Ganaste $" + apuesta);
            case 2 -> mostrarMensaje("Empate. Recuperas tu apuesta.");
            case 3 -> mostrarMensaje("Has perdido. Pierdes tu apuesta.");
        }
        reiniciarJuego();
    }

    private int determinarResultado() {
        int valorJugador = calcularValorDeMano(manoJugador);
        int valorCrupier = calcularValorDeMano(manoCrupier);

        if (valorJugador > 21) return 3;
        if (valorCrupier > 21 || valorJugador > valorCrupier) return 1;
        if (valorJugador == valorCrupier) return 2;
        return 3;
    }

    private int calcularValorDeMano(ArrayList<Carta> mano) {
        return BlackJack.calcularValorDeMano(mano);
    }

    private void mostrarMensaje(String mensaje) {
        textArea.append(mensaje + "\n");
    }

    private void reiniciarJuego() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Quieres jugar otra ronda?", "Juego terminado", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            inicializarJuego();
            realizarApuestaInicial();
        } else {
            System.exit(0);
        }
    }

    private void actualizarPanelCartas(JPanel panel, ArrayList<Carta> mano) {
        panel.removeAll();
        for (Carta carta : mano) {
            panel.add(crearCartaVisual(carta));
        }
        panel.revalidate();
        panel.repaint();
    }

    private JPanel crearCartaVisual(Carta carta) {
        JPanel panelCarta = new JPanel();
        panelCarta.setPreferredSize(new Dimension(80, 120));
        panelCarta.setBackground(Color.WHITE);
        panelCarta.setBorder(new LineBorder(Color.BLACK, 2));
        panelCarta.setLayout(new BorderLayout());

        JLabel lblNumero = new JLabel(carta.getNumero(), SwingConstants.CENTER);
        JLabel lblPalo = new JLabel(carta.getPalo(), SwingConstants.CENTER);

        panelCarta.add(lblNumero, BorderLayout.NORTH);
        panelCarta.add(lblPalo, BorderLayout.CENTER);

        return panelCarta;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackGUI gui = new BlackjackGUI();
            gui.setVisible(true);
        });
    }
}

