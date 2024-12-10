package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Peashooter extends Plant{
    private static final long speed = 10;
    private static final long ACTION_INTERVAL = 1500;
    private ArrayList<Projectile> Peas;
    private long lastActionTime;
    private ArrayList<Zombie> zombies;

    public Peashooter(JButton button, JLayeredPane layeredPane, ArrayList<Projectile> rowPeas, ArrayList<Zombie> zombies) {
        super.hp = 300;
        super.layeredPane = layeredPane;
        super.button = button;
        this.Peas = rowPeas;
        this.zombies = zombies;
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/PeaShooter.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);
        createHitbox();
        this.lastActionTime = System.currentTimeMillis();
    }

    @Override
    public void update(long currentTime,int actualSuns) {
        if (currentTime - lastActionTime >= ACTION_INTERVAL) {
            attack();
            lastActionTime = currentTime;
        }
    }

    private void attack(){
        if (!zombies.isEmpty()) {
            Peas.add(new Pea(20, layeredPane, button, zombies));
        }
    }
}
