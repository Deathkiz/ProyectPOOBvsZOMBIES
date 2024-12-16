package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Brainstein extends Zombie{
    private JButton button;
    private ArrayList<Collectable> brains;
    private long lastActionTime;
    private long ACTION_INTERVAL = 20000;
    private ImageIcon kaboomIcon;
    private boolean machineMode;


    public Brainstein(JButton button, JLayeredPane layeredPane, ArrayList<Collectable> brains,boolean machineMode) {
        super.hp = 300;
        this.button = button;
        icon = "existe";
        super.layeredPane = layeredPane;
        this.brains = brains;
        super.cost = 50;
        this.machineMode = machineMode;

        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/Sunflower.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        ImageIcon ashIcon = new ImageIcon(getClass().getResource("/resources/BoomDie.gif"));
        this.kaboomIcon = new ImageIcon(ashIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
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
        if (hp<=0) {
            icon = "dead";
        }
    }

    private void createBrain(){
        brains.add(new Brain(25,layeredPane,button,machineMode));
    }

    @Override
    public void die(long currentTime) {
        hitbox.setBounds(0,0,0,0);
    }

    @Override
    public void kaboom(long currentTime) {
        button.setIcon(kaboomIcon);
    }

    @Override
    public void remove() {
        button.setIcon(null);
    }

    public boolean endGame(){
        return false;
    }
}
