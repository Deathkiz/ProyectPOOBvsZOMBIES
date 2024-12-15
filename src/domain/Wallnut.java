package domain;

import javax.swing.*;
import java.awt.*;

public class Wallnut extends Plant{
    private ImageIcon normal;
    private ImageIcon cracked;
    private ImageIcon superCracked;
    private String icon;

    public Wallnut(JButton button, JLayeredPane layeredPane){
        super.hp = 4000;
        super.button = button;
        super.layeredPane = layeredPane;
        super.cost = 50;
        icon = "normal";
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/WallNut.gif"));
        normal = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        ImageIcon gifIcon1 = new ImageIcon(getClass().getResource("/resources/Wallnut_cracked1.gif"));
        cracked = new ImageIcon(gifIcon1.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        ImageIcon gifIcon2 = new ImageIcon(getClass().getResource("/resources/Wallnut_cracked2.gif"));
        superCracked = new ImageIcon(gifIcon2.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(normal);
        createHitbox();
    }

    @Override
    public void update(long currentTime,int actualSuns) {
        if (hp <= 2500 && icon.equals("normal")){
            button.setIcon(cracked);
            icon = "cracked";
        } else if (hp <= 1000 && icon.equals("cracked")){
            button.setIcon(superCracked);
            icon = "superCracked";
        }
    }
}
