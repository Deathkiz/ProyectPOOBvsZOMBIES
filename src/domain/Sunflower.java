package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Sunflower extends Plant{
    private long lastActionTime;
    private static final long ACTION_INTERVAL = 25000;
    private ArrayList<Sun> suns;

    public Sunflower(JButton button, JLayeredPane layeredPane, ArrayList<Sun> suns) {
        super.hp = 300;
        super.cost = 50;
        super.button = button;
        super.layeredPane = layeredPane;
        this.suns = suns;

        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/Sunflower.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);
        createHitbox();
        this.lastActionTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastActionTime >= ACTION_INTERVAL) {
            lastActionTime = currentTime;
            createSun();
        }

    }

    private void createSun(){
        suns.add(new Sun(25,layeredPane,button));
    }

}
