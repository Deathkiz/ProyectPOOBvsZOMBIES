package domain;

import java.awt.*;

public abstract class Projectile {
    protected Rectangle hitbox;
    protected int attack;
    protected int distance;
    protected boolean outOfBonds;
    protected int maxWidth;

    public abstract void forward(long currentTime);

    public abstract void remove();

    public boolean isOutOfBonds(){return outOfBonds;}
}
