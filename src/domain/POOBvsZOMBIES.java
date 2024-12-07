package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class POOBvsZOMBIES {
    private Plant[][] plants;
    private ArrayList<Zombie>[] zombies;
    private int suns;
    private int brains;
    private Rectangle[][] hitboxs;

    public POOBvsZOMBIES(int suns, int brains){
        plants = new Plant[5][8];
        zombies = (ArrayList<Zombie>[]) new ArrayList[5];
        for (int i = 0; i < 5; i++) {
            zombies[i] = new ArrayList<>();
        }
        this.suns = suns;
        this.brains = brains;
        hitboxs = new Rectangle[5][8];
    }

    public void plantDamage(int row, int column, int damage){plants[row][column].damage(damage);}

    public void zombieDamage(int damage){}

    public void createPlant(String type, int row, int column,JButton button,JLayeredPane layeredPane){
        if (type.equals("peashooter")){
            plants[row][column] = new Peashooter(button,layeredPane);
            hitboxs[row][column] = plants[row][column].getHitbox();
        }
        else if (type.equals("sunflower")){
            plants[row][column] = new Sunflower(button, layeredPane);
            hitboxs[row][column] = plants[row][column].getHitbox();
        }


    }

    public void deletePlant(int row, int column){
        plants[row][column] = null;
    }

    public void createZombie(String type, int row, JButton button,JLayeredPane layeredPane){
        if (type.equals("basic")){
            zombies[row].add(new Basic(button,layeredPane, hitboxs));
        }
        else if (type.equals("coneHead")){
            //zombies[row].add(new ConeHead(button,layeredPane));
        }
        else if (type.equals("bucketHead")){
            //zombies[row].add(new BucketHead(button,layeredPane));
        }
        else if (type.equals("brainstein")){
            //zombies[row].add(new Brainstein(button,layeredPane));
        }
        else if (type.equals("ECIZombie")){
            //zombies[row].add(new ECIZombie(button,layeredPane));
        }

    }
}
