package domain;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public abstract class Plant implements Serializable {
    protected int hp;
    protected JLayeredPane layeredPane;
    protected JButton button;
    protected Rectangle hitbox;
    protected int cost;

    public void damage(int damage){hp -= damage;}

    public int getHp(){return hp;}

    public void setHp(int hp){this.hp = hp;}

    public void createHitbox(){
        Point buttonLocationOnScreen = button.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();

        // Calcular la posición relativa del botón dentro del JLayeredPane
        int relativeX = buttonLocationOnScreen.x - layeredPaneLocationOnScreen.x;
        int relativeY = buttonLocationOnScreen.y - layeredPaneLocationOnScreen.y;

        // Tamaño del botón
        Dimension buttonSize = button.getSize();
        Point p = new Point(relativeX, relativeY);

        hitbox = new Rectangle(p,buttonSize);
    }
    public Rectangle getHitbox(){
        return hitbox;
    }

    public abstract void update(long currentTime,int actualSuns);

    public void die(){
        button.setIcon(null);
    }

    public int getCost(){
        return cost;
    }
}