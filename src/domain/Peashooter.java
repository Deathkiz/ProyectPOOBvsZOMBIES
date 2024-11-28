package domain;

public class Peashooter extends Plant{

    public Peashooter(){
        super.hp = 300;
        super.cost = 100;

    }

    public void attack(){

    }

    public void damage(int recive){
        super.hp -= recive;
    }

    public void dead(){
        if (super.hp <= 0){

        }
    }
}
