package domain;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public abstract class Zombie implements Serializable {
    protected int hp;
    protected JLayeredPane layeredPane;
    protected Rectangle[] hitboxs;
    protected Plant[] plants;
    protected LawnMower lawnMower;
    protected Rectangle hitbox;
    protected String icon;
    protected long deadTime;
    protected int cost;


    public void damage(int damage){hp -= damage;}

    public void setHp(int hp){this.hp = hp;}

    public int getHp(){return hp;}

    public Rectangle getHitbox() {return hitbox;}

    public abstract void update(long currentTime);

    public abstract void die(long currentTime);

    public abstract void kaboom(long currentTime);

    public long getDeadTime(){return deadTime;}

    public abstract void remove();

    public String getIcon(){return icon;};

    public abstract boolean endGame();

    public int getCost(){
        return cost;
    }
}