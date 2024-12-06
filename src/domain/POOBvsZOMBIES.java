package domain;

import javax.swing.*;
import java.util.ArrayList;

public class POOBvsZOMBIES {
    private Plant[][] plants;
    private ArrayList<Zombie> zombies;
    private int suns;
    private int brains;

    public POOBvsZOMBIES(int suns, int brains){
        plants = new Plant[5][8];
        zombies = new ArrayList<>();
        this.suns = suns;
        this.brains = brains;
    }

    public void plantDamage(int row, int column, int damage){plants[row][column].damage(damage);}

    public void zombieDamage(int damage){}

    public void createPlant(String type, int row, int column){
        if (type.equals("peashooter")){
            plants[row][column] = new Peashooter();
        }
        else if (type.equals("sunflower")){
            plants[row][column] = new Sunflower();
        }


    }

    public void deletePlant(int row, int column){
        plants[row][column] = null;
    }
}
