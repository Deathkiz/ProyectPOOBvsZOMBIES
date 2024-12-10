package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Brainstein extends Zombie{
    private JButton button;
    private ArrayList<Collectable> brains;
    private long lastActionTime;
    private long ACTION_INTERVAL = 20000;


    public Brainstein(JButton button, JLayeredPane layeredPane, ArrayList<Collectable> brains) {
        super.hp = 300;
        this.button = button;
        super.layeredPane = layeredPane;
        this.brains = brains;

        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/Sunflower.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);
        createHitbox();
        this.lastActionTime = System.currentTimeMillis();
    }

    public void createHitbox(){
        Point buttonLocationOnScreen = button.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();

        // Calcular la posición relativa del botón dentro del JLayeredPane
        int relativeX = buttonLocationOnScreen.x - layeredPaneLocationOnScreen.x;
        int relativeY = buttonLocationOnScreen.y - layeredPaneLocationOnScreen.y;

        // Tamaño del botón
        Dimension buttonSize = button.getSize();
        Point p = new Point(relativeX, relativeY);

        hitbox = new Rectangle(p,buttonSize);
    }

    @Override
    public void update(long currentTime) {
        if (currentTime - lastActionTime >= ACTION_INTERVAL) {
            lastActionTime = currentTime;
            createBrain();
        }
    }

    private void createBrain(){
        brains.add(new Brain(25,layeredPane,button));
    }

    @Override
    public void die() {

    }

    @Override
    public void remove() {

    }
}
