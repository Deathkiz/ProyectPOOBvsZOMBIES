package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Peashooter extends Plant{
    private long lastActionTime = 0;
    private long lastPeaAction = 0;
    private static final long speed = 1000000;
    private static final long ACTION_INTERVAL = 1500000000;
    private ArrayList<pea> peas;

    public Peashooter(JButton button, JLayeredPane layeredPane){
        super.hp = 300;
        super.cost = 100;
        super.layeredPane = layeredPane;
        super.button = button;
        peas = new ArrayList<>();
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/PeaShooter.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long currentTime = System.nanoTime(); // Obtener el tiempo actual en nanosegundos
                    if (currentTime - lastActionTime >= ACTION_INTERVAL) {
                        attack();
                        lastActionTime = currentTime;
                    }
                    long peaTimer = System.nanoTime();
                    pea outOfBounds = null;
                    if (peaTimer - lastPeaAction >= speed){
                        for (pea p:peas){
                            p.forward();
                            if (p.getX() > layeredPane.getWidth()){
                                outOfBounds = p;
                            }
                            lastPeaAction = peaTimer;
                        }
                        if (outOfBounds != null){
                            peas.remove(outOfBounds);
                        }
                    }
                }
            }
        });

        // Iniciar el hilo
        thread.start();
    }

    private void attack(){
        peas.add(new pea(20,layeredPane,button));
    }

    public void dead(){

    }
}
