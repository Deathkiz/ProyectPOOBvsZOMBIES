package domain;

import javax.swing.*;

public class ECIZombie extends Zombie{
    private JLabel label;
    private JLabel head;
    private boolean attack;
    private int distance;
    private ImageIcon walkingIcon;
    private ImageIcon attackIcon;
    private ImageIcon bodyDieIcon;
    private ImageIcon headDieIcon;
    private ImageIcon kaboomIcon;
    private long lastMovement;
    private long lastAttack;

    @Override
    public void update(long currentTime) {

    }

    @Override
    public void die(long currentTime) {

    }

    @Override
    public void kaboom(long currentTime) {

    }

    @Override
    public void remove() {

    }
}
