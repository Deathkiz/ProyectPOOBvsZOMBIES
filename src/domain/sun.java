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
        Thread movementThread = new Thread(() -> {
            try {
                // Obtener la posición actual del botón
                int currentX = button.getX();
                int currentY = button.getY();

                // Calcular la dirección (incrementos por paso)
                int stepX = targetX > currentX ? 1 : -1; // Dirección en X
                int stepY = targetY > currentY ? 1 : -1; // Dirección en Y

                // Movimiento hasta alcanzar el objetivo
                while (currentX != targetX || currentY != targetY) {
                    if (currentX != targetX) {
                        currentX += stepX; // Mover en X
                    }
                    if (currentY != targetY) {
                        currentY += stepY; // Mover en Y
                    }

                    // Actualizar la posición del botón
                    button.setBounds(currentX, currentY, button.getWidth(), button.getHeight());

                    // Repintar el JLayeredPane
                    layeredPane.repaint();

                    // Esperar un breve intervalo (~16 ms para 60 FPS)
                    Thread.sleep(2);
                }
                button.setVisible(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Iniciar el hilo
        movementThread.start();
    }

}
