package domain;

import javax.swing.*;
import java.awt.*;

public class Sunflower extends Plant{
    private long lastActionTime;
    private static final long ACTION_INTERVAL = 1000000000;

    public Sunflower(JButton button, JLayeredPane layeredPane){
        super.hp = 300;
        super.cost = 50;
        super.button = button;
        super.layeredPane = layeredPane;
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/Sunflower.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);
        createHitbox();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lastActionTime = System.nanoTime();
                while (true) {
                    if (hp <= 0){
                        dead();
                        Thread.interrupted();
                    }
                    long currentTime = System.nanoTime(); // Obtener el tiempo actual en nanosegundos
                    if (currentTime - lastActionTime >= 25*ACTION_INTERVAL) {
                        createSun();
                        lastActionTime = currentTime;
                    }
                }
            }
        });

        // Iniciar el hilo
        thread.start();

    }

    private void createSun(){
        new sun(layeredPane,button);
    }

    private void dead(){
        button.setIcon(null);
        hitbox = null;
    }
}
