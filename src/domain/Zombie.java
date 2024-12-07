package domain;

import javax.swing.*;
import java.awt.*;

public abstract class Zombie {
    protected int hp;
    protected int cost;
    protected JLayeredPane layeredPane;
    protected Rectangle[][] hitboxs;


    public void damage(int damage){hp -= damage;}

    public int getHp(){return hp;}

    public int getCost(){return cost;}


}
