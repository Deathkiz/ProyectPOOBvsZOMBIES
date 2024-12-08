package domain;

import javax.swing.*;
import java.awt.*;

public class sun {
    private JButton button;
    private ImageIcon image;
    private JLayeredPane layeredPane;

    public sun(JLayeredPane layeredPane, JButton button) {
        this.layeredPane = layeredPane;
        Point buttonLocationOnScreen = button.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();

        int relativeX = buttonLocationOnScreen.x - layeredPaneLocationOnScreen.x;
        int relativeY = buttonLocationOnScreen.y - layeredPaneLocationOnScreen.y;

        // Tamaño del botón
        Dimension buttonSize = button.getSize();

        // Configurar la imagen y el JLabel
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/Sun.gif"));
        int labelWidth = (int) (buttonSize.getWidth()*0.5);
        int labelHeight = (int) (buttonSize.getHeight()*0.5);
        image = new ImageIcon(gifIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_DEFAULT));
        this.button = new JButton(image);
        this.button.setContentAreaFilled(false);
        this.button.setBorderPainted(false);

        // Configurar las dimensiones y posición del JLabel
        this.button.setBounds(relativeX, relativeY, labelWidth, labelHeight);

        // Agregar el JLabel al JLayeredPane en la capa DRAG_LAYER
        layeredPane.add(this.button, JLayeredPane.DRAG_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();

        this.button.addActionListener(e -> moveToPosition(10,10));
    }

    private void moveToPosition(int targetX, int targetY) {
        // Usar un arreglo para hacer que las variables sean "final"
        int[] position = {button.getX(), button.getY()}; // [currentX, currentY]

        // Calcular la dirección (incrementos por paso)
        int stepX = targetX > position[0] ? 1 : -1; // Dirección en X
        int stepY = targetY > position[1] ? 1 : -1; // Dirección en Y

        // Temporizador para manejar el movimiento
        Timer timer = new Timer(1, null);

        timer.addActionListener(e -> {
            // Verificar si el objetivo ha sido alcanzado
            if (position[0] == targetX && position[1] == targetY) {
                ((Timer) e.getSource()).stop(); // Detener el temporizador
                button.setVisible(false);
                return;
            }

            // Actualizar la posición
            if (position[0] != targetX) {
                position[0] += stepX;
            }
            if (position[1] != targetY) {
                position[1] += stepY;
            }

            // Mover el botón y repintar el panel
            button.setBounds(position[0], position[1], button.getWidth(), button.getHeight());
            layeredPane.repaint();
        });

        timer.start(); // Iniciar el temporizador
    }
}
