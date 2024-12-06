package domain;

import javax.swing.*;
import java.awt.*;

public class Peashooter extends Plant{
    private long lastActionTime;
    private static final long ACTION_INTERVAL = 1500000000;

    public Peashooter(JButton button){
        super.hp = 300;
        super.cost = 100;
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/PeaShooter.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Aquí va la lógica que debe ejecutarse en el hilo
                while (true) {
                    long currentTime = System.nanoTime(); // Obtener el tiempo actual en nanosegundos
                    if (currentTime - lastActionTime >= ACTION_INTERVAL) {
                        System.out.println("El Peashooter está disparando...");

                        lastActionTime = currentTime;
                    }
                }
            }
        });

        // Iniciar el hilo
        thread.start();
    }

    private void run(){

    }

    public void dead(){

    }
}
