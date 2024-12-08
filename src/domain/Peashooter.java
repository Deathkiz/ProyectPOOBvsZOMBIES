package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Peashooter extends Plant{
    private static final long speed = 10;
    private static final long ACTION_INTERVAL = 1500;
    private ArrayList<Pea> Peas;
    private long lastActionTime;
    private long lastPeaMove;
    private ArrayList<Zombie> zombies;

    public Peashooter(JButton button, JLayeredPane layeredPane, ArrayList<Pea> rowPeas, ArrayList<Zombie> zombies) {
        super.hp = 300;
        super.cost = 100;
        super.layeredPane = layeredPane;
        super.button = button;
        this.Peas = rowPeas;
        this.zombies = zombies;
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/PeaShooter.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);
        createHitbox();
        this.lastActionTime = System.currentTimeMillis();
        this.lastPeaMove = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();// Obtener el tiempo actual en nanosegundos
        if (currentTime - lastActionTime >= ACTION_INTERVAL) {
            attack();
            lastActionTime = currentTime;
        }
    }

    private void attack(){
        Peas.add(new Pea(20,layeredPane,button,zombies));
    }
}
