package domain;

public abstract class Collectable {
    protected boolean inPosition;
    protected boolean active;
    protected int value;


    public abstract void moveToPosition();

    public abstract void remove();

    public int getValue(){return value;}

    public boolean isInPosition(){return inPosition;}

    public boolean isActive(){return active;}
}

