package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class POOmba extends Projectile{
    private JLabel label;
    private ImageIcon image;
    private JLayeredPane layeredPane;
    private Plant[] plants;


    public POOmba(int attack, JLayeredPane layeredPane, JLabel label, Plant[] plants) {
        super.attack = attack;
        this.layeredPane = layeredPane;
        super.outOfBonds = false;
        this.plants = plants;
        this.hitbox = new Rectangle();
        maxWidth = 0;
        // Obtener la posición absoluta del botón
        Point labelLocationOnScreen = label.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();

        // Calcular la posición relativa del botón dentro del JLayeredPane
        int relativeX = labelLocationOnScreen.x - layeredPaneLocationOnScreen.x;
        int relativeY = labelLocationOnScreen.y - layeredPaneLocationOnScreen.y;
        distance = layeredPane.getWidth()/200;

        // Tamaño del botón
        Dimension buttonSize = label.getSize();

        // Configurar la imagen y el JLabel
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/POOmba.png"));
        int labelWidth = (int) (buttonSize.getWidth() / 3);
        int labelHeight = (int) (buttonSize.getHeight() / 3);
        image = new ImageIcon(gifIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_DEFAULT));
        this.label = new JLabel(image);

        int labelX = relativeX + labelWidth;
        int labelY = (int) (relativeY + (buttonSize.height / 1.5) - (labelHeight / 2));

        // Configurar las dimensiones y posición del JLabel
        this.label.setBounds(labelX, labelY, labelWidth, labelHeight);
        hitbox.setBounds(labelX, labelY, labelWidth, labelHeight);
        // Agregar el JLabel al JLayeredPane en la capa MODAL_LAYER
        layeredPane.add(this.label, JLayeredPane.DRAG_LAYER);
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
        if (getX() < maxWidth-label.getWidth()){
            outOfBonds = true;
        }
        attack(currentTime);
    }

    public void remove() {
        layeredPane.remove(label);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void attack(long currentTime){
        Plant plant = null;
        for (int i = 0; i < plants.length; i++) {
            if (plants[i] != null) {
                Rectangle hitbox = plants[i].getHitbox();
                if (this.hitbox.intersects(hitbox)) {
                    plant = plants[i];
                    plant.damage(attack);
                    remove();
                    outOfBonds = true;
                    break;
                }
            }
        }
    }

    public int getX() {
        return label.getX();
    }
}