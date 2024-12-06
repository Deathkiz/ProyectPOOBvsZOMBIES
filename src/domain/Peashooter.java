package domain;

import javax.swing.*;
import java.awt.*;

public class Peashooter extends Plant{

    public Peashooter(){
        super.hp = 300;
        super.cost = 100;

    }

    public void attack(){

    }

    public void dead(){
        if (super.hp <= 0){

        }
    }
}
