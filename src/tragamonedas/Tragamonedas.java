package tragamonedas;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Tragamonedas extends JFrame{
    private JLabel titulo;
    private JButton GIRARButton;
    private JLabel primerEspacio;
    private JLabel segundoEspacio;
    private JLabel tercerEspacio;
    private JPanel tragaperras;
    private Timer animationTimer; // Timer para la animación
    private final Random rng = new Random();
    private int animationCounter = 0; // Contador para la animación
    private static final String[] simbolos = {"icons/uvas.png", "icons/manzana.png", "icons/sandia.png"};

    public Tragamonedas(){
        inicializar();

        primerEspacio.setIcon(escalarImagen(simbolos[rng.nextInt(simbolos.length)]));
        segundoEspacio.setIcon(escalarImagen(simbolos[rng.nextInt(simbolos.length)]));
        tercerEspacio.setIcon(escalarImagen(simbolos[rng.nextInt(simbolos.length)]));

        GIRARButton.addActionListener(e -> {
            iniciarAnimacion();
            if(girar()){
                System.out.println("ganó");
            }
        });
    }

    public void inicializar(){
        setContentPane(tragaperras);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    public boolean girar(){
        int symbol1 = rng.nextInt(simbolos.length);
        int symbol2 = rng.nextInt(simbolos.length);
        int symbol3 = rng.nextInt(simbolos.length);

        // Actualizar los rodillos
        primerEspacio.setIcon(escalarImagen(simbolos[symbol1]));
        segundoEspacio.setIcon(escalarImagen(simbolos[symbol2]));
        tercerEspacio.setIcon(escalarImagen(simbolos[symbol3]));

        vibrarPantalla();

        // Determinar el resultado
        if (symbol1 == symbol2 && symbol2 == symbol3) {
            titulo.setText("¡Ganaste!");
            return true;
        } else {
            titulo.setText("Sigue intentando...");
            return false;
        }
    }

    private ImageIcon escalarImagen(String path) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void iniciarAnimacion() {
        // Desactivar el botón mientras se ejecuta la animación
        GIRARButton.setEnabled(false);
        titulo.setText("Girando...");

        // Reiniciar el contador de animación
        animationCounter = 0;

        // Configurar el Timer para la animación
        animationTimer = new Timer(100, e -> {
            // Actualizar los rodillos con símbolos aleatorios
            primerEspacio.setIcon(escalarImagen(simbolos[rng.nextInt(simbolos.length)]));
            segundoEspacio.setIcon(escalarImagen(simbolos[rng.nextInt(simbolos.length)]));
            tercerEspacio.setIcon(escalarImagen(simbolos[rng.nextInt(simbolos.length)]));

            animationCounter++;

            // Detener la animación después de un número definido de actualizaciones
            if (animationCounter > 10) { // La animación durará 20 pasos
                animationTimer.stop();

            }
        });

        // Iniciar el Timer
        animationTimer.start();

        GIRARButton.setEnabled(true);
    }

    private void resaltarVentana() {
        setAlwaysOnTop(true);  // Configurar la ventana como "siempre encima"
        toFront();             // Llevar la ventana al frente
        requestFocus();        // Solicitar el foco
        setAlwaysOnTop(false); // Desactivar "siempre encima" para restaurar el comportamiento normal
    }


    private void vibrarPantalla() {
        int originalWidth = getWidth();
        int originalHeight = getHeight();
        int[] counter = {0}; // Contador para los ciclos de vibración

        Timer timer = new Timer(50, e -> {
            if (counter[0] % 2 == 0) {
                setSize(originalWidth + 10, originalHeight + 10); // Aumentar el tamaño
            } else {
                setSize(originalWidth, originalHeight); // Restaurar el tamaño original
            }
            counter[0]++;

            // Detener la vibración después de 6 ciclos (3 vibraciones)
            if (counter[0] > 6) {
                setSize(originalWidth, originalHeight); // Restaurar el tamaño final
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
        resaltarVentana();
    }

}
