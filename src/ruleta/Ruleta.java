package ruleta;
import java.awt.*;
import javax.swing.*;


public class Ruleta extends JDialog {

    private JPanel ruleta;
    private JTextField montoApuesta;
    private JComboBox tipoApuesta;
    private JButton girar;
    private JTextArea mensajes;
    private JScrollPane historialApuestas;

    // Aquí encapsulo toda la lógica de los botones
    public Ruleta(JFrame parent) {
        super(parent);
        inicializarPantalla();
    }
    public static void main(String[] args) {
        Ruleta r = new Ruleta(null);
    }
    //Lógica de diseño del cuadro que aparece en pantalla
    private void inicializarPantalla() {
        setTitle("Ruleta");
        setContentPane(ruleta);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(getParent());
        setVisible(true);
    }
}

