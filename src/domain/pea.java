package domain;

import javax.swing.*;
import java.awt.*;

public class pea {
    private JLabel label;
    private ImageIcon image;
    private int attack;
    private JLayeredPane layeredPane;
    private Rectangle hitbox;
    private int distance;
    private boolean outOfBonds;

    public pea(int attack, JLayeredPane layeredPane, JButton button) {
        this.attack = attack;
        this.layeredPane = layeredPane;
        this.outOfBonds = false;
        // Obtener la posición absoluta del botón
        Point buttonLocationOnScreen = button.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();

        // Calcular la posición relativa del botón dentro del JLayeredPane
        int relativeX = buttonLocationOnScreen.x - layeredPaneLocationOnScreen.x;
        int relativeY = buttonLocationOnScreen.y - layeredPaneLocationOnScreen.y;
        distance = layeredPane.getWidth()/200;

        // Tamaño del botón
        Dimension buttonSize = button.getSize();

        // Configurar la imagen y el JLabel
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/ProjectilePea.png"));
        int labelWidth = (int) (buttonSize.getWidth() / 5);
        int labelHeight = (int) (buttonSize.getHeight() / 5);
        image = new ImageIcon(gifIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_DEFAULT));
        label = new JLabel(image);

        int labelX = relativeX + buttonSize.width;
        int labelY = relativeY + (buttonSize.height / 2) - (labelHeight / 2);

        // Configurar las dimensiones y posición del JLabel
        label.setBounds(labelX, labelY, labelWidth, labelHeight);

        // Agregar el JLabel al JLayeredPane en la capa MODAL_LAYER
        layeredPane.add(label, JLayeredPane.DRAG_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void forward() {
        int currentX = label.getX();
        int currentY = label.getY();
        // Calcular la nueva posición (mover hacia la derecha)
        int newX = currentX + distance;
        // Actualizar la posición del JLabel
        label.setBounds(newX, currentY, label.getWidth(), label.getHeight());
        layeredPane.repaint();
        if (getX() > layeredPane.getWidth()){
            outOfBonds = true;
        }
    }

    public boolean outOfBonds(){
        return outOfBonds;
    }

    public void remove() {
        SwingUtilities.invokeLater(() -> {
            layeredPane.remove(label);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    public int getX() {
        return label.getX();
    }
}

