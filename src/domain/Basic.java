package domain;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class Basic extends Zombie{
    private JLabel label;

    public Basic(JButton button, JLayeredPane layeredPane, Rectangle[][] hitboxs){
        super.hp = 100;
        super.cost = 100;
        super.layeredPane = layeredPane;
        super.hitboxs = hitboxs;
        final long LONG = 1;
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/zombie.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        label = new JLabel(buttonIcon);
        Point buttonLocationOnScreen = button.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();
        int relativeY = buttonLocationOnScreen.y - layeredPaneLocationOnScreen.y;
        int width = buttonIcon.getIconWidth();
        int height = buttonIcon.getIconHeight();
        int y = (int) (relativeY - height/3);
        label.setBounds(layeredPane.getWidth(), y, width, height);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Color negro, grosor 2 píxeles
        label.setBorder(border);
        layeredPane.add(label, JLayeredPane.DRAG_LAYER);
        layeredPane.repaint();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long lastActionTime = System.nanoTime();
                while (true) {
                    long currentTime = System.nanoTime();// Obtener el tiempo actual en nanosegundos
                    movement(LONG);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        thread.start();

    }
    public void movement(long LONG){
        label.setBounds((int) (label.getX()-LONG),label.getY(), (int) label.getSize().getWidth(), (int) label.getSize().getHeight());
        layeredPane.repaint();
    }
    public int getX(){
        return label.getX();
    }

    public void createHitbox(){

    }


}
