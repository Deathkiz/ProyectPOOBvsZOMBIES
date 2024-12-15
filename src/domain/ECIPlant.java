package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ECIPlant extends Plant{
    private long lastActionTime;
    private static final long ACTION_INTERVAL = 25000;
    private int ACTION_USES = 3;
    private ArrayList<Collectable> suns;

    public ECIPlant(JButton button, JLayeredPane layeredPane, ArrayList<Collectable> suns) {
        super.hp = 150;
        super.button = button;
        super.layeredPane = layeredPane;
        this.suns = suns;
        super.cost = 75;

        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/Sunflower.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);
        createHitbox();
        this.lastActionTime = System.currentTimeMillis();
        createSun();
    }


    @Override
    public void update(long currentTime,int actualSuns) {
        if (actualSuns == 0 && ACTION_USES>0 && currentTime-lastActionTime >= ACTION_INTERVAL){
            createSun();
            lastActionTime = currentTime;
        }
    }

    private void createSun(){
        ACTION_USES--;
        suns.add(new Sun(50,2,layeredPane,button));
    }
}
