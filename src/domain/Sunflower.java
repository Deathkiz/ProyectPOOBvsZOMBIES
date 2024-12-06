package domain;

import javax.swing.*;
import java.awt.*;

public class Sunflower extends Plant{

    public Sunflower(JButton button){
        super.hp = 100;
        super.cost = 50;
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/Sunflower.gif"));
        ImageIcon buttonIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 0.7), (int) (button.getSize().getHeight() * 0.7), Image.SCALE_DEFAULT));
        button.setIcon(buttonIcon);

    }
}
