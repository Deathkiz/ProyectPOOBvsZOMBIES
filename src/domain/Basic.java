package domain;

import javax.swing.*;
import java.awt.*;

public class Basic extends Zombie{
    private JLabel label;
    private boolean attack;
    private String icon;
    private boolean isAttack;
    private Plant[] plants;

    public Basic(JButton button, JLayeredPane layeredPane, Rectangle[] hitboxs,Plant[] plants){
        super.hp = 100;
        super.cost = 100;
        super.layeredPane = layeredPane;
        super.hitboxs = hitboxs;
        this.plants = plants;
        final long LONG = 1;
        attack = false;
        icon = "caminando";
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/zombie.gif"));
        final ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        ImageIcon attackIcon = new ImageIcon(getClass().getResource("/resources/zombieAttack.gif"));
        final ImageIcon labelIcon = new ImageIcon(attackIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        label = new JLabel(buttonIcon);
        Point buttonLocationOnScreen = button.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();
        int relativeY = buttonLocationOnScreen.y - layeredPaneLocationOnScreen.y;
        int width = buttonIcon.getIconWidth();
        int height = buttonIcon.getIconHeight();
        int y = (int) (relativeY - height/3);
        label.setBounds(layeredPane.getWidth(), y, width, height);
        super.hitbox = new Rectangle((int) (layeredPane.getWidth()+width*0.7),relativeY,(int) width/2,button.getHeight());
        layeredPane.add(label, JLayeredPane.DRAG_LAYER);
        layeredPane.repaint();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int position = -1;
                    int i = 0;
                    for (Rectangle plant: hitboxs){
                        if (plant != null){
                            if (hitbox.intersects(plant)){
                                attack = true;
                                position = i;
                                break;
                            }
                        }
                        else {
                            i++;
                        }
                    }

                    if (attack && icon.equals("caminando")){
                        label.setIcon(labelIcon);
                        icon = "atacando";
                    }
                    else if ((!attack) && icon.equals("atacando")){
                        label.setIcon(buttonIcon);
                        icon = "caminando";
                    }

                    if (position >= 0){
                        attackZombie(position);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        movement(LONG);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    attack = false;
                }
            }
        });


        thread.start();

    }
    public void movement(long LONG){
        hitbox.setLocation((int) (hitbox.x-LONG),hitbox.y);
        label.setBounds((int) (label.getX()-LONG),label.getY(), (int) label.getSize().getWidth(), (int) label.getSize().getHeight());
        layeredPane.repaint();
    }
    public int getX(){
        return label.getX();
    }
    public void attackZombie(int position){
        plants[position].damage(100);
        if (plants[position].getHp() <= 0){
            plants[position] = null;
            hitboxs[position] = null;
        }
    }
}
