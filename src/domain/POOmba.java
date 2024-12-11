package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class POOmba extends Projectile{
    private JLabel label;
    private ImageIcon image;
    private JLayeredPane layeredPane;
    private ArrayList<Plant> plants;


    public POOmba(int attack, JLayeredPane layeredPane, JLabel label, ArrayList<Plant> plants) {
        super.attack = attack;
        this.layeredPane = layeredPane;
        super.outOfBonds = false;
        this.plants = plants;
        this.hitbox = new Rectangle();
        maxWidth = 0;
        // Obtener la posición absoluta del botón
        Point buttonLocationOnScreen = label.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();

        // Calcular la posición relativa del botón dentro del JLayeredPane
        int relativeX = buttonLocationOnScreen.x - layeredPaneLocationOnScreen.x;
        int relativeY = buttonLocationOnScreen.y - layeredPaneLocationOnScreen.y;
        distance = layeredPane.getWidth()/200;

        // Tamaño del botón
        Dimension buttonSize = label.getSize();

        // Configurar la imagen y el JLabel
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/POOmba.png"));
        int labelWidth = (int) (buttonSize.getWidth() / 5);
        int labelHeight = (int) (buttonSize.getHeight() / 5);
        image = new ImageIcon(gifIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_DEFAULT));
        label = new JLabel(image);

        int labelX = relativeX + buttonSize.width;
        int labelY = relativeY + (buttonSize.height / 2) - (labelHeight / 2);

        // Configurar las dimensiones y posición del JLabel
        label.setBounds(labelX, labelY, labelWidth, labelHeight);
        hitbox.setBounds(labelX, labelY, labelWidth, labelHeight);
        // Agregar el JLabel al JLayeredPane en la capa MODAL_LAYER
        layeredPane.add(label, JLayeredPane.DRAG_LAYER);
    }

    @Override
    public void forward(long currentTime) {
        int currentX = label.getX();
        int currentY = label.getY();
        // Calcular la nueva posición (mover hacia la derecha)
        int newX = currentX - distance;
        // Actualizar la posición del JLabel
        label.setBounds(newX, currentY, label.getWidth(), label.getHeight());
        hitbox.setBounds(newX,hitbox.y,hitbox.width,hitbox.height);
        if (getX() < maxWidth){
            outOfBonds = true;
        }
        attack(currentTime);
    }

    public void remove() {
        SwingUtilities.invokeLater(() -> {
            layeredPane.remove(label);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    public void attack(long currentTime){
        Plant plant = null;
        for (int i = 0; i < plants.size(); i++) {
            Rectangle hitbox = plants.get(i).getHitbox();
            if (this.hitbox.intersects(hitbox)) {
                plant = plants.get(i);
                plant.damage(attack);
                remove();
                outOfBonds = true;
                break;
            }
        }
    }

    public int getX() {
        return label.getX();
    }
}
