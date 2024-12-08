package domain;

import javax.swing.*;
import java.awt.*;

public abstract class Zombie {
    protected int hp;
    protected int cost;
    protected JLayeredPane layeredPane;
    protected Rectangle[] hitboxs;
    protected Plant[] plants;
    protected Rectangle hitbox;
    protected String icon;
    protected long deadTime;


    public void damage(int damage){hp -= damage;}

    public int getHp(){return hp;}

    public int getCost(){return cost;}

    public Rectangle getHitbox() {return hitbox;}

    public abstract void update();

    public abstract void die();

    public long getDeadTime(){return deadTime;}

    public abstract void remove();

    public String getIcon(){return icon;};
}
