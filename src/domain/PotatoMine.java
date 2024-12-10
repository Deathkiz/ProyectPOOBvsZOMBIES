package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PotatoMine extends Plant{
    private long startTime;
    private static final long activation = 14000;
    private ArrayList<Zombie> zombies;
    private boolean active;
    private String icon;
    private ImageIcon activateIcon;


    public PotatoMine(JButton button, JLayeredPane layeredPane, ArrayList<Zombie> zombies) {
        super.hp = 300;
        super.button = button;
        super.layeredPane = layeredPane;
        this.active = false;
        this.icon = "desactivada";
        this.zombies = zombies;
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/PotatoMineNotReady.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);
        createHitbox();
        this.startTime = System.currentTimeMillis();
    }


    @Override
    public void update(long currentTime,int actualSuns) {
        if (currentTime-startTime >= activation && icon.equals("desactivada")){
            activate();
        }
        if (active){
            attack();
        }
    }

    private void activate(){
        icon = "acivado";
        button.setIcon(activateIcon);
    }

    private void attack(){
        ArrayList<Zombie> attackedZombies = new ArrayList<>();
        for (Zombie zombie:zombies){
            if (hitbox.intersects(zombie.getHitbox())){
                attackedZombies.add(zombie);
            }
        }

        if (!attackedZombies.isEmpty()){
            for (Zombie zombie: attackedZombies){
                zombie.setHp(0);
            }
            hp = 0;
        }
    }
}
