package ruleta;
import javax.swing.*;
import java.awt.*;

public class RuletaPanel extends JPanel {
    private static final int DIAMETRO = 300;
    private static final int CENTRO_X = 200;
    private static final int CENTRO_Y = 200;

    private int anguloActual; // Ángulo actual de la ruleta

    public RuletaPanel() {
        this.anguloActual = 0; // Inicializar el ángulo
    }

    public void setAnguloActual(int anguloActual) {
        this.anguloActual = anguloActual; // Actualizar el ángulo
        repaint(); // Redibujar la ruleta
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int segmentos = 37; // 36 números + 0
        int anguloSegmento = 360 / segmentos;

        for (int i = 0; i < segmentos; i++) {
            if (i == 0) {
                g2d.setColor(Color.GREEN); // 0
            } else if (esNumeroRojo(i)) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.BLACK);
            }

            g2d.fillArc(CENTRO_X - DIAMETRO / 2, CENTRO_Y - DIAMETRO / 2, DIAMETRO, DIAMETRO,
                    anguloActual + (i * anguloSegmento), anguloSegmento);
        }

        g2d.setColor(Color.BLACK);
        g2d.drawOval(CENTRO_X - DIAMETRO / 2, CENTRO_Y - DIAMETRO / 2, DIAMETRO, DIAMETRO);

        // Dibujar indicador
        g2d.setColor(Color.BLUE);
        g2d.fillPolygon(
                new int[]{CENTRO_X, CENTRO_X - 10, CENTRO_X + 10},
                new int[]{CENTRO_Y - DIAMETRO / 2, CENTRO_Y - DIAMETRO / 2 - 20, CENTRO_Y - DIAMETRO / 2 - 20},
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


