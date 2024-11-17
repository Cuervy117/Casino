package ruleta;

import javax.swing.*;

public class Ruleta extends JFrame {

    private JPanel ruleta;
    // Aquí encapsulo toda la lógica de los botones
    public Ruleta() {
        inicializarPantalla();
    }
    public static void main(String[] args) {
        Ruleta r = new Ruleta();
        r.setVisible(true); //Sin esto, no aparece
    }
    //Lógica de diseño del cuadro que aparece en pantalla
    private void inicializarPantalla() {
        setContentPane(ruleta);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}

