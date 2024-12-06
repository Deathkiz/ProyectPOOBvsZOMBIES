package domain;

public abstract class Plant {
    protected int hp;
    protected int cost;

    public void damage(int damage){hp -= damage;}

    public int getHp(){return hp;}

    public int getCost(){return cost;}

}
