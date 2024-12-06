package domain;

import javax.swing.*;

public abstract class Plant {
    protected int hp;
    protected int cost;
    protected JLayeredPane layeredPane;
    protected JButton button;

    public void damage(int damage){hp -= damage;}

    public int getHp(){return hp;}

    public int getCost(){return cost;}

}
