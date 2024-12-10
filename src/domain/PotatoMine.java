package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PotatoMine extends Plant{
    private long startTime;
    private static final long activation = 14000;
    private ArrayList<Zombie> zombies;
    private boolean active;
    private boolean dead;
    private String icon;
    private ImageIcon activateIcon;
    private ImageIcon explosiveIcon;

    public PotatoMine(JButton button, JLayeredPane layeredPane, ArrayList<Zombie> zombies) {
        super.hp = 300;
        super.button = button;
        super.layeredPane = layeredPane;
        this.active = false;
        this.dead = false;
        this.icon = "desactivada";
        this.zombies = zombies;
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/PotatoMineNotReady.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        ImageIcon readyIcon = new ImageIcon(getClass().getResource("/resources/PotatoMine.gif"));
        activateIcon = new ImageIcon(readyIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        ImageIcon explosioooon = new ImageIcon(getClass().getResource("/resources/PotatoMine_mashed.gif"));
        explosiveIcon = new ImageIcon(explosioooon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1), (int) (button.getSize().getHeight() * 1), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);
        hitbox = new Rectangle(0,0,0,0);
        this.startTime = System.currentTimeMillis();
    }


    @Override
    public void update(long currentTime,int actualSuns) {
        if (currentTime-startTime >= activation && icon.equals("desactivada")){
            activate();
        }
        else if (active){
            attack(currentTime);
        }
        else if (dead){
            if (currentTime-startTime >= 1000){
                hp = 0;
            }
        }
    }

    private void activate(){
        createHitbox();
        active = true;
        icon = "acivado";
        button.setIcon(activateIcon);
    }

    private void attack(long currentTime){
        for (Zombie zombie:zombies){
            if (hitbox.intersects(zombie.getHitbox())){
                active=false;
                kaboom(currentTime);
                zombie.setHp(0);
                zombie.kaboom(currentTime);
                break;
            }
        }
    }

    private void kaboom(long currentTime){
        dead = true;
        startTime = currentTime;
        button.setIcon(explosiveIcon);

    }
}
