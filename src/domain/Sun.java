package domain;

import javax.swing.*;
import java.awt.*;

public class Sun {
    private JButton button;
    private ImageIcon image;
    private JLayeredPane layeredPane;
    private boolean inPosition;
    private boolean active;
    private int suns;

    public Sun(int suns, JLayeredPane layeredPane, JButton button) {
        this.layeredPane = layeredPane;
        inPosition = false;
        active = false;
        this.suns = suns;
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


        this.button.addActionListener(e -> {
            active = true;
        });

    }

    public boolean getActive(){return active;}

    public void moveToPosition(int targetX, int targetY) {
        int currentX = button.getX();
        int currentY = button.getY();

        if (currentX != targetX || currentY != targetY) { // Verificar si no está en la posición objetivo
            // Calcular las diferencias entre las posiciones objetivo y actuales
            int deltaX = targetX - currentX;
            int deltaY = targetY - currentY;

            // Calcular la distancia total entre las posiciones
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            // Normalizar los pasos (dirección) y escalar para mover 5 píxeles por paso
            double stepX = (deltaX / distance) * 5; // Escalar a 5 píxeles
            double stepY = (deltaY / distance) * 5; // Escalar a 5 píxeles

            // Redondear los pasos y mover la posición actual
            currentX += Math.round(stepX);
            currentY += Math.round(stepY);

            // Ajustar si supera el objetivo
            if (Math.abs(currentX - targetX) < 5) currentX = targetX;
            if (Math.abs(currentY - targetY) < 5) currentY = targetY;

            // Mover el botón a la nueva posición
            button.setBounds(currentX, currentY, button.getWidth(), button.getHeight());

            // Repintar el panel para actualizar la interfaz gráfica
            layeredPane.repaint();
        } else {
            inPosition = true; // Marcar que ya está en posición
        }
    }

    public void remove() {
        SwingUtilities.invokeLater(() -> {
            layeredPane.remove(button);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    public int getSuns() {
        return suns;
    }

    public boolean getInPosition(){return inPosition;}
}
