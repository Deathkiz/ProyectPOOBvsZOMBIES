package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Pea extends Projectile{
    private JLabel label;
    private ImageIcon image;
    private JLayeredPane layeredPane;
    private int distance;
    private Rectangle hitbox;
    private ArrayList<Zombie> zombies;


    public Pea(int attack, JLayeredPane layeredPane, JButton button, ArrayList<Zombie> zombies) {
        super.attack = attack;
        this.layeredPane = layeredPane;
        super.outOfBonds = false;
        this.zombies = zombies;
        this.hitbox = new Rectangle();
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
        hitbox.setBounds(labelX, labelY, labelWidth, labelHeight);
        // Agregar el JLabel al JLayeredPane en la capa MODAL_LAYER
        layeredPane.add(label, JLayeredPane.DRAG_LAYER);
    }

    @Override
    public void forward() {
        int currentX = label.getX();
        int currentY = label.getY();
        // Calcular la nueva posición (mover hacia la derecha)
        int newX = currentX + distance;
        // Actualizar la posición del JLabel
        label.setBounds(newX, currentY, label.getWidth(), label.getHeight());
        hitbox.setBounds(newX,hitbox.y,hitbox.width,hitbox.height);
        layeredPane.repaint();
        if (getX() > layeredPane.getWidth()){
            outOfBonds = true;
        }
        attack();
    }

    public void remove() {
        SwingUtilities.invokeLater(() -> {
            layeredPane.remove(label);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    public void attack(){
        Zombie zombie1 = null;
        for (int i = 0; i < zombies.size(); i++) {
            Rectangle hitbox = zombies.get(i).getHitbox();
            if (this.hitbox.intersects(hitbox)) {
                zombie1 = zombies.get(i);
                zombie1.damage(attack);
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

